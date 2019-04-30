package com.example.juicekaaa.fireserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.entity.DoorOrder;
import com.example.juicekaaa.fireserver.fid.entity.Lock;
import com.example.juicekaaa.fireserver.fid.serialportapi.SerialPortsServiceImpl;
import com.example.juicekaaa.fireserver.fid.service.CallBacks;
import com.example.juicekaaa.fireserver.fid.service.SerialPortsService;
import com.example.juicekaaa.fireserver.utils.EncodingConversionTools;
import com.example.juicekaaa.fireserver.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import android_serialport_api.SerialPortDevice;

public class BackService extends Service implements CallBacks {
    private static final String TAG = "BackService";
    /**
     * 心跳频率
     */
    private static final long HEART_BEAT_RATE = 60 * 1000;
    /**
     * 服务器ip地址
     */
    public static final String HOST = "101.132.139.37";
    /**
     * 服务器端口号
     */
    public static final int PORT = 23303;
    /**
     * 读线程
     */
    private ReadThread mReadThread;
    private LocalBroadcastManager mLocalBroadcastManager;
    /***/
    private WeakReference<Socket> mSocket;
    // For heart Beat
    private Handler mHandler = new Handler();
    private int num = 0;
    /**
     * 心跳任务，不断重复调用自己
     */
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                boolean isSuccess = sendMsg(EncodingConversionTools.HexString2Bytes(MyApplication.getMac()));//发送MAC, 如果发送失败，就重新初始化一个socket
                if (!isSuccess) {
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    new InitSocketThread().start();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private long sendTime = 0L;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new InitSocketThread().start();

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    public boolean sendMsg(final byte[] msg) {
        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
        final Socket soc = mSocket.get();
        if (!soc.isClosed() && !soc.isOutputShutdown()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OutputStream os = soc.getOutputStream();
                        os.write(msg);
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (e instanceof SocketException) {
                            mReadThread.release();
                            releaseLastSocket(mSocket);
                            new InitSocketThread().start();
                            System.out.println("=========网络异常,发送异常重启Socket=========");
                        }
                    }
                }
            }).start();
            sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
            System.out.println("======发送心跳包，监听Socket状态========");
        } else {
            return false;
        }
        return true;
    }

    private void initSocket() {//初始化Socket
        try {
            Socket so = new Socket(HOST, PORT);
            mSocket = new WeakReference<Socket>(so);
            mReadThread = new ReadThread(so);
            mReadThread.start();
            Socket soc = mSocket.get();
            if (!soc.isClosed() && !soc.isOutputShutdown()) {
                sendMsg(EncodingConversionTools.HexString2Bytes(MyApplication.getMac()));//发送MAC, 如果发送失败，就重新初始化一个socket
            }
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
     *
     * @param mSocket
     */
    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (!sk.isClosed()) {
                    sk.close();
                }
                sk = null;
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void locksStatus(Boolean result) {
        if (result == false) {
            num = 0;
            System.out.println("===========开始盘点位置==============");
            MessageEvent messageEvent = new MessageEvent(MyApplication.MESSAGE_InventoryLocation);
            EventBus.getDefault().post(messageEvent);
        }
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    // Thread to read content from Socket
    class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<Socket>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer, length)).trim();
                            Log.e(TAG, message);
                            String msg = EncodingConversionTools.str2HexStr(message);

//                            SerialPortDevice mSerialPort = serviceCom.open("/dev/ttysWK0", 9600);
//                            serviceCom.send(mSerialPort, buffer);

//                            DoorOrder.getInstance().sendHex(buffer);
//                            new Lock().sends(buffer);
//                            EventBus.getDefault().post(new MessageEvent(MyApplication.TCP_BACK_DATA, buffer));

                            Lock.getInstance().sends(buffer);
//                            getLocksStatus("查询锁是否都关闭");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("数据接收情况");
                }
            }
        }
    }

    public void getLocksStatus(final String question) {
        System.out.println("=======NUM大小=========" + num);
        if (num == 0) {
            num = num + 1;
            //这里用一个线程就是异步，
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Lock.getInstance().setLocksStatus(BackService.this, question);
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(heartBeatRunnable);
        mReadThread.release();
        releaseLastSocket(mSocket);
    }

}