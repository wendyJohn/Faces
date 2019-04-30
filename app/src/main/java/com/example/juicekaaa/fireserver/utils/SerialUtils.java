//package com.example.juicekaaa.fireserver.utils;
//
//import android.util.Log;
//
//import com.nativec.tools.SerialPort;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//
//
///**
// * Author: create by ZhongMing
// * Time: 2019/2/28 0028 14:45
// * Description:
// */
//public class SerialUtils {
//    private static final String CHUAN = "/dev/ttysWK0";//ttymxc2 ttyS2
//    private static final int BOTE = 9600;
//
//    private final String TAG = "SerialPortUtils";
//    public boolean serialPortStatus = false; //是否打开串口标志
//    public String data_;
//    public boolean threadStatus; //线程状态，为了安全终止线程
////    private SerialPort serialPort = null;
//    private SerialPort mSerialPort = null;
//
//    public InputStream inputStream = null;
//    public OutputStream outputStream = null;
//
//    /**
//     * 打开串口
//     *
//     * @return serialPort串口对象
//     */
//    public SerialPort openSerialPort() {
//        try {
//            mSerialPort = new SerialPort(new File(CHUAN), BOTE, 0);
//
////            serialPort = new SerialPort(new File(CHUAN), BOTE, 0);
//            this.serialPortStatus = true;
//            threadStatus = false; //线程状态
////            //获取打开的串口中的输入输出流，以便于串口数据的收发
//            inputStream = mSerialPort.getInputStream();
//            outputStream = mSerialPort.getOutputStream();
//        } catch (IOException e) {
//            Log.e(TAG, "openSerialPort: 打开串口异常：" + e.toString());
//            return mSerialPort;
//        }
//        Log.d(TAG, "openSerialPort: 打开串口");
//        return mSerialPort;
//    }
//
//
//    /**
//     * 关闭串口
//     */
//    public void closeSerialPort() {
//        try {
//            inputStream.close();
//            outputStream.close();
//
//            this.serialPortStatus = false;
//            this.threadStatus = true; //线程状态
//            mSerialPort.close();
//        } catch (IOException e) {
//            Log.e(TAG, "closeSerialPort: 关闭串口异常：" + e.toString());
//            return;
//        }
//        Log.d(TAG, "closeSerialPort: 关闭串口成功");
//    }
//
//    /**
//     * 发送串口指令（字符串）
//     *
//     * @param
//     */
//    public void sendHex(String sHex) {
//        byte[] bOutArray = HexToByteArr(sHex);
//        this.send(bOutArray);
//    }
//
//    public void send(byte[] bOutArray) {
//        try {
//            this.outputStream.write(bOutArray);
//        } catch (IOException var3) {
//            var3.printStackTrace();
//        }
//
//    }
//
//    public static byte[] HexToByteArr(String inHex) {
//        int hexlen = inHex.length();
//        byte[] result;
//        if (isOdd(hexlen) == 1) {
//            ++hexlen;
//            result = new byte[hexlen / 2];
//            inHex = "0" + inHex;
//        } else {
//            result = new byte[hexlen / 2];
//        }
//
//        int j = 0;
//
//        for(int i = 0; i < hexlen; i += 2) {
//            result[j] = HexToByte(inHex.substring(i, i + 2));
//            ++j;
//        }
//
//        return result;
//    }
//
//    public static int isOdd(int num) {
//        return num & 1;
//    }
//
//    public static byte HexToByte(String inHex) {
//        return (byte)Integer.parseInt(inHex, 16);
//    }
//
//    public boolean isOpen() {
//        return serialPortStatus;
//    }
//
//}
