package com.example.juicekaaa.fireserver.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.juicekaaa.fireserver.MainActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public CountDownTimer countDownTimer;
    private long advertisingTime = 300 * 1000;//无操作时跳转首页时间
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏虚拟按键
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        context = this;
        hideBottomUIMenu();
        setContentView(getLayoutRes());
    }

    protected abstract int getLayoutRes();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: //有按下动作时取消定时
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    System.out.println("=================="+"取消定时");
                }
                break;
            case MotionEvent.ACTION_UP: //抬起时启动定时
                System.out.println("=================="+"开始定时");
                startTime();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 无操作时跳转首页
     */
    public void startTime() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(advertisingTime, 1000l) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (!BaseActivity.this.isFinishing()) {
                        int remainTime = (int) (millisUntilFinished / 1000L);
                        System.out.println("=========倒计时========="+remainTime+"秒");
                    }
                }

                @Override
                public void onFinish() { //定时完成后的操作
                    //跳转到页面
                    startActivity(new Intent(context, MainActivity.class));
                }
            };
            countDownTimer.start();
        } else {
            countDownTimer.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示是启动定时
        startTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当activity不在前台是停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
            System.out.println("=================="+"取消定时");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁时停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
            System.out.println("=================="+"取消定时");
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

//    private Timer mTimer; // 计时器，每1秒执行一次任务
//    private MyTimerTask mTimerTask; // 计时任务，判断是否未操作时间到达ns
//    private long mLastActionTime; // 上一次操作时间
//
//    @Override
//    protected void onCreate(Bundle arg0) {
//        super.onCreate(arg0);
//    }
//
//    @Override
//    protected void onResume() {
//        startTimer();
//        super.onResume();
//    }
//    //时间计时器，长时间未操作返回首页。
//    private void startTimer() {
//        mTimer = new Timer();
//        mTimerTask = new MyTimerTask();
//        // 初始化上次操作时间为打开页面的时间
//        mLastActionTime = System.currentTimeMillis();
//        // 每过10s检查一次
//        mTimer.schedule(mTimerTask, 0, 10000);
//        //Log.e(TAG, "打开页面开始计时");
//    }
//
//    // 每当用户接触了屏幕，都会执行此方法
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        mLastActionTime = System.currentTimeMillis();
//        //Log.e(TAG, "正在点击屏幕");
//        return super.dispatchTouchEvent(ev);
//    }
//
//    private class MyTimerTask extends TimerTask {
//        @Override
//        public void run() {
//            System.out.println("计时中……");
//            // 5分钟未操作停止计时并返回首页
//            if (System.currentTimeMillis() - mLastActionTime > 1000 * 60 * 5) {
//                stopTimer();// 停止计时任务
//                resetSprfMain();//返回首页
//            }
//        }
//    }
//
//    //长时间未操作返回首页
//    public void resetSprfMain() {
//        Intent i = new Intent(BaseActivity.this, MainActivity.class);
//        startActivity(i);
//        finish();
//    }
//
//    // 停止计时任务
//    public void stopTimer() {
//        mTimer.cancel();
//        //Log.e("", "取消计时");
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        stopTimer();
//        super.onDestroy();
//    }
}
