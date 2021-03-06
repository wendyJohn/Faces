package com.example.juicekaaa.fireserver.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.manager.FaceSDKManager;
import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.face.activity.MainsActivity;
import com.example.juicekaaa.fireserver.face.activity.RegActivity;
import com.example.juicekaaa.fireserver.face.activity.UserGroupManagerActivity;
import com.example.juicekaaa.fireserver.utils.DoorPasswordView;
import com.example.juicekaaa.fireserver.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 在线帮助界面
 */
public class FunctionHelpActivity extends BaseActivity implements View.OnClickListener {
    private TextView back;
    private TextView opening_instructions;
    private TextView sos_instructions;
    private TextView common_instructions;
    private TextView smallprogram_instructions;

    private ImageView smallprogramses;
    private ImageView materialaccess;
    private ImageView helpfire;
    private ImageView helpsos;

    static int COUNTS = 5;//点击次数
    static long DURATION = 3 * 1000;//规定有效时间
    long[] mHits = new long[COUNTS];
    private BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        intiView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_help;
    }

    //初始化
    private void intiView() {
        EventBus.getDefault().register(this);
        back = findViewById(R.id.back);
        opening_instructions = findViewById(R.id.opening_instructions);
        sos_instructions = findViewById(R.id.sos_instructions);
        common_instructions = findViewById(R.id.common_instructions);
        smallprogram_instructions = findViewById(R.id.smallprogram_instructions);
        smallprogramses = findViewById(R.id.smallprogramses);
        materialaccess = findViewById(R.id.materialaccess);
        helpfire = findViewById(R.id.helpfire);
        helpsos = findViewById(R.id.helpsos);
        back.setOnClickListener(this);
        opening_instructions.setOnClickListener(this);
        sos_instructions.setOnClickListener(this);
        common_instructions.setOnClickListener(this);
        smallprogram_instructions.setOnClickListener(this);
        smallprogramses.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        hideBottomUIMenu();
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smallprogramses:
//                fiveClick(2);
                break;
            //返回
            case R.id.back:
                finish();
                break;
            //开门存取物资
            case R.id.opening_instructions:
                smallprogramses.setVisibility(View.GONE);
                materialaccess.setVisibility(View.VISIBLE);
                helpfire.setVisibility(View.GONE);
                helpsos.setVisibility(View.GONE);
                break;
            //SOS
            case R.id.sos_instructions:
                smallprogramses.setVisibility(View.GONE);
                materialaccess.setVisibility(View.GONE);
                helpfire.setVisibility(View.GONE);
                helpsos.setVisibility(View.VISIBLE);
                break;
            //常见问题
            case R.id.common_instructions:
                smallprogramses.setVisibility(View.GONE);
                materialaccess.setVisibility(View.GONE);
                helpfire.setVisibility(View.VISIBLE);
                helpsos.setVisibility(View.GONE);
//                openPayPasswordDialog();//临时用
                break;
            //应急小程序
            case R.id.smallprogram_instructions:
                smallprogramses.setVisibility(View.VISIBLE);
                materialaccess.setVisibility(View.GONE);
                helpfire.setVisibility(View.GONE);
                helpsos.setVisibility(View.GONE);
                break;
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
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    private void toast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FunctionHelpActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 连续点五次
     */
    public void fiveClick(int type) {

        switch (type) {
            case 1://设备激活
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                    System.out.println("您已在[" + DURATION + "]ms内连续点击【" + mHits.length + "】次了！！！");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                            .PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                        return;
                    }
                    return;
                }
                break;
            case 2://人员管理
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                    System.out.println("您已在[" + DURATION + "]ms内连续点击【" + mHits.length + "】次了！！！");
                    Intent intent = new Intent(this, MainsActivity.class);
                    startActivity(intent);
                }

                break;
        }

    }

    //密码输入验证注册
    private void openPayPasswordDialog() {
        DoorPasswordView doorPasswordView = new DoorPasswordView(this, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(doorPasswordView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
            case MyApplication.MESSAGE_DISMISS://返回
                bottomSheetDialog.dismiss();
                break;
            case MyApplication.MESSAGE_DOOR:
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(this, RegActivity.class);
                startActivity(intent);
                break;
        }
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
