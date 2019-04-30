package com.example.juicekaaa.fireserver;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.aip.api.FaceApi;
import com.baidu.aip.db.DBManager;
import com.baidu.aip.entity.Group;
import com.baidu.aip.manager.FaceSDKManager;
import com.baidu.aip.utils.GlobalSet;
import com.baidu.aip.utils.PreferencesUtil;
import com.example.juicekaaa.fireserver.activity.FunctionHomesActivity;
import com.example.juicekaaa.fireserver.broadcast.Receiver;
import com.example.juicekaaa.fireserver.data.DBHelpers;
import com.example.juicekaaa.fireserver.fid.entity.EPC;
import com.example.juicekaaa.fireserver.fid.entity.Lock;
import com.example.juicekaaa.fireserver.fid.serialportapi.ReaderServiceImpl;
import com.example.juicekaaa.fireserver.fid.service.CallBack;
import com.example.juicekaaa.fireserver.fid.service.ReaderService;
import com.example.juicekaaa.fireserver.fid.tool.ReaderUtil;
import com.example.juicekaaa.fireserver.fid.util.DataFilter;
import com.example.juicekaaa.fireserver.net.Advertisement;
import com.example.juicekaaa.fireserver.net.NetCallBack;
import com.example.juicekaaa.fireserver.net.RequestUtils;
import com.example.juicekaaa.fireserver.net.URLs;
import com.example.juicekaaa.fireserver.service.BackService;
import com.example.juicekaaa.fireserver.utils.FullVideoView;
import com.example.juicekaaa.fireserver.utils.GlideImageLoader;
import com.example.juicekaaa.fireserver.utils.MessageEvent;
import com.example.juicekaaa.fireserver.utils.PreferenceUtils;
import com.example.juicekaaa.fireserver.utils.SVProgressHUD;
import com.example.juicekaaa.fireserver.utils.VideoUtil;
import com.loopj.android.http.RequestParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

//import com.bjw.bean.ComBean;
//import com.bjw.utils.FuncUtil;
//import com.bjw.utils.SerialHelper;


public class MainActivity extends AppCompatActivity implements OnBannerListener {

    private VideoUtil videoUtil;
    @BindView(R.id.video)
    FullVideoView video;
    @BindView(R.id.banner1)
    Banner bannerone;
    @BindView(R.id.banner2)
    Banner bannertwo;

    private Receiver receivera;
    private Receiver receiverb;
    private Receiver receiverc;
    private Receiver receiverd;

    private RelativeLayout linestatus;
    private BottomSheetDialog bottomSheetDialog;
    //    private SerialUtils serial = null;
    MyApplication myApplication;
    public static final int TYPE_RGB_DEPTH_LIVENSS = 4;
    public static final String TYPE_LIVENSS = "TYPE_LIVENSS";
    private ReaderService readerService = new ReaderServiceImpl();
    private Lock lock = null;
    private DBHelpers mOpenHelper;
    private List<EPC> listEPC;
    private List<String> listEpc;
    private List<String> listAnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏虚拟按键
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        myApplication = new MyApplication();
        if (myApplication.isFirst) {
            myApplication.setIsFirst(false);
            initFaceeSDK();//初始化人脸；
            addGroup();//强制添加分组；
            startService();//开启Service；
            PreferencesUtil.putInt(TYPE_LIVENSS, TYPE_RGB_DEPTH_LIVENSS);//设置摄像头样式；
            PreferencesUtil.putInt(GlobalSet.TYPE_CAMERA, GlobalSet.ORBBECATLAS);//设置摄像头样式；
            Advertisement.format(MainActivity.this);//广告视频；
            PreferenceUtils.setString(MainActivity.this, "doorPermission", "Close");//初始化开门权限。
        }
        initView();//初始化
        RegisteredBroadcasting(); //广播注册
        checkPermission();//7.0以上添加存储与相机的权限
        Fid();
        mOpenHelper = new DBHelpers(this);
        listEPC = new ArrayList<>();
        listEpc = new ArrayList<>();
        listAnt = new ArrayList<>();
    }

    public void startService() {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.juicekaaa.fireserver.service.BackService".equals(service.service.getClassName())) {
                Log.v("qs", "isServiceRunning = true");
                isServiceRunning = true;
            }
        }
        if (!isServiceRunning) {
            int netWork = com.example.juicekaaa.fireserver.utils.Utils.NetWork(getApplicationContext());
            //判断返回值
            if (netWork == -1) {
                System.out.println("======无网络,请检查网络========");
                SVProgressHUD.showInfoWithStatus(MainActivity.this, "当前网络异常，请设置网络！");
            } else if (netWork == 1) {
                System.out.println("======无线网连接,启动服务========");
                Intent startIntent = new Intent(MainActivity.this, BackService.class);
                startService(startIntent);
            } else {
                System.out.println("======手机移动网络连接,,启动服务========");
                Intent startIntent = new Intent(MainActivity.this, BackService.class);
                startService(startIntent);
            }
        } else {
            Log.v("qs", "service alreay start");
        }
    }

    private void initFaceeSDK() {
        FaceSDKManager.getInstance().init(this, new FaceSDKManager.SdkInitListener() {
            @Override
            public void initStart() {

            }

            @Override
            public void initSuccess() {

            }

            @Override
            public void initFail(int errorCode, String msg) {

            }
        });
    }

    /**
     * 播放视频广告
     */
    @Override
    protected void onResume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Advertisement.addImage(); //加载图片
            }
        }).start();
        videoPlayback(); //加载本地視頻
        hideBottomUIMenu();
        super.onResume();
    }

    /**
     * 初始化
     */
    private void initView() {
        linestatus = findViewById(R.id.linestatus);
        videoUtil = new VideoUtil(this, video);

//        serial = new SerialUtils();
        try {
            //打开设备串口（一定要开启）
//            serial.openSerialPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        //触发接收数据接口(不可以删，删掉你啥数据都接收不到了)
//        TCPManager.getInstance().setOnReceiveDataListener(new TCPManager.OnReceiveDataListener() {
//            @Override
//            public void onReceiveData(String str) {
//                System.out.println("accept2:" + str);
//            }
//        });

        System.out.println("========AAAAAAA==========" + MyApplication.getMac());
        //绑定唯一标识
        JPushInterface.setAlias(MainActivity.this, 1, MyApplication.getMac());
    }


    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
            case MyApplication.TCP_BACK_DATA:
                String order = messageEvent.getMessage();
                System.out.println("=======开门数据==========" + order);
                order = order.replaceAll(" ", "");
//                DoorOrder.getInstance().sendHex(order);
//                if (serial.isOpen()) {
//                    System.out.println("===========开门成功================");
//                    serial.sendHex(order);
//                } else {
//                    System.out.println("===========串口没打开================");
//                }
                break;
            case MyApplication.MESSAGE:// 关闭提示框，播放本地视频
                videoUtil.setVideo();
                break;
            case MyApplication.MESSAGE_UPDATE:
                System.out.println("=========AAAA收到了============");

//                Advertisement.downLoad(MainActivity.this);
                //开门权限
//                PreferenceUtils.setString(MainActivity.this,"doorPermission","Open");
                break;
            case MyApplication.MESSAGE_OUT://退出
                finish();
                break;
            case MyApplication.MESSAGE_DISMISS://返回
                bottomSheetDialog.dismiss();
                break;
            case MyApplication.MESSAGE_BANNER://图片广告
                List<String> list_path = messageEvent.getListPath();
                List<String> list_paths = messageEvent.getListPaths();
                List<String> imageTitle = messageEvent.getImageTitle();
                List<String> imageTitles = messageEvent.getImageTitles();
                bannerone.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                bannerone.setImageLoader(new GlideImageLoader());
                bannerone.setBannerAnimation(Transformer.Default);
                bannerone.setDelayTime(3000);
                bannerone.setBannerTitles(imageTitle);
                bannerone.isAutoPlay(true);
                bannerone.setIndicatorGravity(BannerConfig.CENTER);
                bannerone.setImages(list_path).setOnBannerListener(MainActivity.this).start();
                bannertwo.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                bannertwo.setImageLoader(new GlideImageLoader());
                bannertwo.setBannerAnimation(Transformer.Default);
                bannertwo.setDelayTime(3000);
                bannertwo.setBannerTitles(imageTitles);
                bannertwo.isAutoPlay(true);
                bannertwo.setIndicatorGravity(BannerConfig.CENTER);
                bannertwo.setImages(list_paths).setOnBannerListener(MainActivity.this).start();
                break;
            case MyApplication.MESSAGE_InventoryLocation://盘点位置
                inventoryLocation();
                break;

        }
    }


    //播放本地视频，判断本地是否存在视频，没有视频就下载视频
    private void videoPlayback() {
        // 创建文件夹，在存储卡下
        String dirName = "/sdcard/FireVideo/";
        File file = new File(dirName);
        // 文件夹不存在时创建
        if (!file.exists()) {
            file.mkdir();
        }
        // 下载后的文件名
        String fileName = dirName + "1542178640266.mp4";
        File file1 = new File(fileName);
        if (file1.exists()) {
            // 如果已经存在, 就不下载了, 去播放
            setVideo();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //如果不存在, 提示下载 获取视频路径；
                    Advertisement.downLoad(MainActivity.this);
                }
            }).start();
        }
    }

    /**
     * 设置视频参数
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setVideo() {
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);//隐藏进度条
        video.setMediaController(mediaController);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + "FireVideo", "1542178640266.mp4");
        video.setVideoPath(file.getAbsolutePath());
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

//                mediaPlayer.start();
//                mediaPlayer.setLooping(true);

                File file = new File(Environment.getExternalStorageDirectory() + "/" + "FireVideo", "1542178640266.mp4");
                video.setVideoPath(file.getAbsolutePath());
                video.start();
            }
        });
        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(MainActivity.this, FunctionHomesActivity.class);
                startActivity(i);
                return false;
            }
        });
    }


    //广播注册
    private void RegisteredBroadcasting() {
        //视频更新广播注册
        receivera = new Receiver();
        IntentFilter intenta = new IntentFilter();
        intenta.addAction(MyApplication.BROADCAST_ACTION_DISC); // 只有持有相同的action的接受者才能接收此广
        registerReceiver(receivera, intenta, MyApplication.BROADCAST_PERMISSION_DISC, null);
        //退出通知广播注册
        receiverb = new Receiver();
        IntentFilter intentb = new IntentFilter();
        intentb.addAction(MyApplication.BROADCAST_ACTION_PASS); // 只有持有相同的action的接受者才能接收此广
        registerReceiver(receiverb, intentb, MyApplication.BROADCAST_PASS_DISC, null);
        //返回通知广播注册
        receiverc = new Receiver();
        IntentFilter intentc = new IntentFilter();
        intentc.addAction(MyApplication.BROADCAST_ACTION_DISMISS); // 只有持有相同的action的接受者才能接收此广
        registerReceiver(receiverc, intentc, MyApplication.BROADCAST_DISMISS_DISC, null);
        //视频下载完播放通知广播注册
        receiverd = new Receiver();
        IntentFilter intentd = new IntentFilter();
        intentd.addAction(MyApplication.BROADCAST_ACTION_VIDEO); // 只有持有相同的action的接受者才能接收此广
        registerReceiver(receiverd, intentd, MyApplication.BROADCAST_VIDEO_DISC, null);
    }

    /**
     * 轮播监听
     */
    @Override
    public void OnBannerClick(int position) {
        Intent i = new Intent(MainActivity.this, FunctionHomesActivity.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        return;//在按返回键时的操作
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭FID串口
        if (ReaderUtil.readers != null) {
            readerService.disconnect(ReaderUtil.readers);
            ReaderUtil.readers = null;
        }

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        unregisterReceiver(receivera);
        unregisterReceiver(receiverb);
        unregisterReceiver(receiverc);
        unregisterReceiver(receiverd);
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionStrs = new ArrayList<>();
            int hasWriteSdcardPermission = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteSdcardPermission != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            int hasCameraPermission = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(android.Manifest.permission.CAMERA);
            }
            String[] stringArray = permissionStrs.toArray(new String[0]);
            if (permissionStrs.size() > 0) {
                requestPermissions(stringArray, MyApplication.REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }

    //权限设置后的回调函数，判断相应设置，requestPermissions传入的参数为几个权限，则permissions和grantResults为对应权限和设置结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MyApplication.REQUEST_CODE_ASK_PERMISSIONS:
                //可以遍历每个权限设置情况
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里写你需要相关权限的操作
                } else {
                    Toast.makeText(MainActivity.this, "权限没有开启", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //强制添加一个分组
    private void addGroup() {
        // 使用人脸1：n时使用
        DBManager.getInstance().init(this);
        List<Group> groupList = FaceApi.getInstance().getGroupList(0, 1000);
        if (groupList.size() <= 0) {
            String groupId = "UserGroup";
            Group group = new Group();
            group.setGroupId(groupId);
            boolean ret = FaceApi.getInstance().groupAdd(group);
            System.out.println("==============" + "添加" + (ret ? "成功" : "失败"));
            return;
        }
    }

    //断开Fid串口
    private void disconnect() {
        if (ReaderUtil.readers != null) {
            readerService.disconnect(ReaderUtil.readers);
            ReaderUtil.readers = null;

        }
    }

    class ReadData implements CallBack {
        @Override
        public void readData(String data, String antNo) {
            addToList(listEPC, data, antNo);
        }

        @Override
        public void readData(String data, String rssi, String antNo, String deviceNo, String direction) {
            addToList(listEPC, data, rssi, antNo, deviceNo, direction);
        }
    }

    private void addToList(final List<EPC> listEPC2, final String epc, final String ant) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DataFilter.dataFilter(listEPC2, epc, ant);

            }
        });
    }

    private void addToList(final List<EPC> listEPC2, final String epc, final String rssi, final String ant, final String deviceNo, final String direction) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DataFilter.dataFilter(listEPC2, epc, rssi, ant, deviceNo, direction);
            }
        });
    }

    //连接Fid串口
    private void Fid() {
        if (ReaderUtil.readers == null) {
            ReaderUtil.readers = readerService.serialPortConnect("/dev/ttysWK1", 115200);
            if (ReaderUtil.readers != null) {
                System.out.println("FID串口连接成功");
                readerService.version(ReaderUtil.readers);
            } else {
                System.out.println("FID串口连接失败");
            }
        }
    }

    //盘点位置信息（盘点一次）
    private void inventoryLocation() {
        if (null == ReaderUtil.readers) {
            System.out.println("请先连接设备");
            return;
        }
        listEPC.removeAll(listEPC);
        listAnt.removeAll(listAnt);
        readerService.invOnceV2(ReaderUtil.readers, new ReadData());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                System.out.println("=======EPC大小========" + listEPC.size());
                if (listEPC.size() <= 0) {
                    disconnect();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            // 等待1000毫秒后重启串口
                            Fid();
                        }
                    }, 1000);
                    return;
                }
                // 等待2000毫秒后获取卡号，并比对。
                for (int i = 0; i < listEPC.size(); i++) {
                    String epc = listEPC.get(i).getEpc();
                    String myant = listEPC.get(i).getAnt();
                    Cursor cursor = mOpenHelper.query("select * from materialtable where Epc=" + "'" + epc + "'", null);
                    while (cursor.moveToNext()) {
                        String epcs = cursor.getString(cursor.getColumnIndex("Epc"));
                        String ant = cursor.getString(cursor.getColumnIndex("Ant"));
                        if (epc.equals(epcs) && myant.equals(ant)) {
                            System.out.println(epcs + "位置正确！");
                        } else {
                            System.out.println(epcs + "位置错误");
                            Cursor cursors = mOpenHelper.query("select * from anttable where Ant=" + "'" + myant + "'", null);
                            while (cursors.moveToNext()) {
                                String StorageLocation = cursors.getString(cursors.getColumnIndex("StorageLocation"));
                                System.out.println("错误位置" + StorageLocation);
                                SubStorageLocation(epcs, StorageLocation);
                            }
                            cursors.close();
                        }
                    }
                    cursor.close();
                }
            }
        }, 2000);
    }

    //提交盘点物资出入库
    public void SubStorageLocation(String epc, String StorageLocation) {
        RequestParams params = new RequestParams();
        params.put("RFID", epc);
        params.put("storageLocation", StorageLocation);
        params.put("username", "admin");
        params.put("platformkey", "app_firecontrol_owner");
        RequestUtils.ClientPost(URLs.StorageLocation_URL, params,
                new NetCallBack() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                    @Override
                    public void onMySuccess(String result) {
                        if (result == null || result.length() == 0) {
                            return;
                        }
                    }
                    @Override
                    public void onMyFailure(Throwable arg0) {
                    }
                });
    }
}