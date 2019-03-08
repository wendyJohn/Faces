package com.example.juicekaaa.fireserver.entity;

import android.content.Context;
import android.widget.Toast;

import com.example.juicekaaa.fireserver.utils.SerialUtils;

import java.util.List;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/7 0007 09:52
 * Description:开门指令
 */
public class DoorOrder {
    private volatile static DoorOrder instance;
    private SerialUtils serial = null;
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
        serial = new SerialUtils();
        try {
            serial.openSerialPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String order : orderList) {
            switch (order) {
                case "A":
                    sendA();
                    break;
                case "B":
                    sendB();
                    break;
                case "C":
                    sendC();
                    break;
                case "D":
                    sendD();
                    break;
                case "E":
                    sendE();
                    break;
                case "F":
                    sendF();
                    break;
                case "G":
                    sendG();
                    break;
                default:
                    sendAll();
                    break;
            }
        }


    }

    private void sendAll() throws InterruptedException {
        if (serial.isOpen()) {
            serial.sendHex(OPENA);
            Thread.sleep(500);
            serial.sendHex(OPENB);
            Thread.sleep(500);
            serial.sendHex(OPENC);
            Thread.sleep(500);
            serial.sendHex(OPEND);
            Thread.sleep(500);
            serial.sendHex(OPENE);
            Thread.sleep(500);
            serial.sendHex(OPENF);
            Thread.sleep(500);
            serial.sendHex(OPENG);
            Toast.makeText(context, "全部门已打开", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendA() {
        if (serial.isOpen()) {
            Toast.makeText(context, "A门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPENA);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendB() {
        if (serial.isOpen()) {
            Toast.makeText(context, "B门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPENB);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendC() {
        if (serial.isOpen()) {
            Toast.makeText(context, "C门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPENC);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendD() {
        if (serial.isOpen()) {
            Toast.makeText(context, "D门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPEND);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendE() {
        if (serial.isOpen()) {
            Toast.makeText(context, "E门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPENE);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendF() {
        if (serial.isOpen()) {
            Toast.makeText(context, "F门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPENF);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendG() {
        if (serial.isOpen()) {
            Toast.makeText(context, "G门已打开", Toast.LENGTH_SHORT).show();
            serial.sendHex(OPENG);
        } else {
            Toast.makeText(context, "串口没打开", Toast.LENGTH_SHORT).show();
        }
    }
}
