package com.example.juicekaaa.fireserver.entity;

import android.content.Context;

import com.example.juicekaaa.fireserver.fid.serialportapi.SerialPortsServiceImpl;
import com.example.juicekaaa.fireserver.fid.service.SerialPortsService;

import java.util.List;

import android_serialport_api.SerialPortDevice;

//import com.example.juicekaaa.fireserver.utils.SerialUtils;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/7 0007 09:52
 * Description:开门指令
 */
public class DoorOrder {
    private volatile static DoorOrder instance;
    SerialPortsService serviceCom = new SerialPortsServiceImpl();
    SerialPortDevice mSerialPort = serviceCom.open("/dev/ttysWK0", 9600);
    private Context context;
    final String OPENA = "68010102ffff16";
    final String OPENB = "68010202ffff16";
    final String OPENC = "68010302ffff16";
    final String OPEND = "68010402ffff16";
    final String OPENE = "68010502ffff16";
    final String OPENF = "68010602ffff16";
    final String OPENG = "68010702ffff16";

    public DoorOrder() {
    }

    public static synchronized DoorOrder getInstance() {
        if (instance == null) {
            synchronized (DoorOrder.class) {
                instance = new DoorOrder();
            }
        }
        return instance;
    }

    public void init(List<String> orderList, Context context) throws InterruptedException {
        this.context = context;
        for (String order : orderList) {
            switch (order) {
                case "A":
                    sendA();
                    Thread.sleep(1000);
                    break;
                case "B":
                    sendB();
                    Thread.sleep(1000);
                    break;
                case "C":
                    sendC();
                    Thread.sleep(1000);
                    break;
                case "D":
                    sendD();
                    Thread.sleep(1000);
                    break;
                case "E":
                    sendE();
                    Thread.sleep(1000);
                    break;
                case "F":
                    sendF();
                    Thread.sleep(1000);
                    break;
                case "G":
                    sendG();
                    Thread.sleep(1000);
                    break;
                default:
                    sendAll();
                    break;

            }
        }
    }

    private void sendAll() throws InterruptedException {
//        if (serial.isOpen()) {
//            serial.sendHex(OPENA);
//            Thread.sleep(1000);
//            serial.sendHex(OPENB);
//            Thread.sleep(1000);
//            serial.sendHex(OPENC);
//            Thread.sleep(1000);
//            serial.sendHex(OPEND);
//            Thread.sleep(1000);
//            serial.sendHex(OPENE);
//            Thread.sleep(1000);
//            serial.sendHex(OPENF);
//            Thread.sleep(1000);
//            serial.sendHex(OPENG);
//            Thread.sleep(1000);
////            Toast.makeText(context, "全部门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========全部门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }
    }


    public void sendA() {
        byte[] sendData = HexToByteArr(OPENA);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========A门已打开================");
//        if (serial.isOpen()) {
//            serial.sendHex(OPENA);
////            Toast.makeText(context, "A门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========A门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }
    }

    public void sendB() {
        byte[] sendData = HexToByteArr(OPENB);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========B门已打开================");
//        if (serial.isOpen()) {
//            serial.sendHex(OPENB);
////            Toast.makeText(context, "B门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========B门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }

    }

    public void sendC() {
        byte[] sendData = HexToByteArr(OPENC);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========C门已打开================");
//        if (ReaderUtil.Doorreaders != null) {
//            reader.sendHex(OPENC);
////            Toast.makeText(context, "C门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========C门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }

    }

    public void sendD() {
        byte[] sendData = HexToByteArr(OPEND);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========D门已打开================");
//        if (serial.isOpen()) {
//            serial.sendHex(OPEND);
////            Toast.makeText(context, "D门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========D门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }

    }

    public void sendE() {
        byte[] sendData = HexToByteArr(OPENE);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========E门已打开================");
//        if (serial.isOpen()) {
//            serial.sendHex(OPENE);
////            Toast.makeText(context, "E门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========E门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }

    }

    public void sendF() {
        byte[] sendData = HexToByteArr(OPENF);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========F门已打开================");
//        if (serial.isOpen()) {
//            serial.sendHex(OPENF);
////            Toast.makeText(context, "F门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========F门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }

    }

    public void sendG() {
        byte[] sendData = HexToByteArr(OPENG);
        serviceCom.send(mSerialPort, sendData);
        System.out.println("===========G门已打开================");
//        if (serial.isOpen()) {
//            serial.sendHex(OPENG);
////            Toast.makeText(context, "G门已打开", Toast.LENGTH_SHORT).show();
//            System.out.println("===========G门已打开================");
//        } else {
//            System.out.println("===========串口没打开================");
//        }
    }

    /**
     * 发送串口指令（字符串）
     * @param
     */
    public void sendHex( byte[] sendData) {
        serviceCom.send(mSerialPort, sendData);
    }


    public static byte[] HexToByteArr(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (isOdd(hexlen) == 1) {
            ++hexlen;
            result = new byte[hexlen / 2];
            inHex = "0" + inHex;
        } else {
            result = new byte[hexlen / 2];
        }
        int j = 0;

        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            ++j;
        }
        return result;
    }

    public static int isOdd(int num) {
        return num & 1;
    }

    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
}
