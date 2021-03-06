package com.example.juicekaaa.fireserver;

import android.app.Application;
import android.util.Log;

import com.example.juicekaaa.fireserver.utils.CrashUtil;
import com.example.juicekaaa.fireserver.utils.GetMac;
import com.example.juicekaaa.fireserver.utils.PreferenceUtils;
import com.example.juicekaaa.fireserver.utils.PreferencesUtil;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    public static final String BROADCAST_PERMISSION_DISC = "com.permissions.MYF_BROADCAST";
    public static final String BROADCAST_PASS_DISC = "com.permissions.MYP_BROADCAST";
    public static final String BROADCAST_DISMISS_DISC = "com.permissions.MYD_BROADCAST";
    public static final String BROADCAST_VIDEO_DISC = "com.permissions.MYV_BROADCAST";
    public static final String BROADCAST_ACTION_DISC = "com.permissions.myf_broadcast";
    public static final String BROADCAST_ACTION_PASS = "com.permissions.myp_broadcast";
    public static final String BROADCAST_ACTION_DISMISS = "com.permissions.myd_broadcast";
    public static final String BROADCAST_ACTION_VIDEO = "com.permissions.myv_broadcast";

    public static final int MESSAGE = 0x345334;
    public static final int MESSAGE_UPDATE = 0x345333;
    public static final int MESSAGE_OUT = 0x756467;
    public static final int MESSAGE_DISMISS = 0x756468;
    public static final int MESSAGE_BANNER = 0x756499;
    public static final int MESSAGE_MATERIAL = 0x756466;
    public static final int TCP_BACK_DATA = 0x213;
    public static final int MESSAGE_BACK = 0x219911;
    public static final int MESSAGE_DOOR = 0x2199;
    public static final int MESSAGE_HISTORRECORD = 0x219988;
    public static final int MESSAGE_InventoryLocation = 0x2199988;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 0x1231232;
    public static String MAC = "";

    public static boolean isFirst;
    public static MyApplication instance;


    public synchronized MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                instance = new MyApplication();
            }
        }
        return instance;
    }

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        achieveMac();
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        isFirst = true;

        //报错日志监听
//        CrashUtil crashUtil = CrashUtil.getInstance();
//        crashUtil.init(this);

    }

    public static String getMac() {
        return MAC;
    }

    /**
     * 获取本地mac地址
     * 初始化socket
     */
    public void achieveMac() {
        MAC = GetMac.getMacAddress().replaceAll(":", "");
//        PreferencesUtil.putString("MAC",MAC);
        Log.i("MAC", MAC);
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean getIsFirst() {
        return this.isFirst;
    }
}

