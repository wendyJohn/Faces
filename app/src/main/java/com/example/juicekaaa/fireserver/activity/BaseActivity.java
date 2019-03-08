package com.example.juicekaaa.fireserver.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.example.juicekaaa.fireserver.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {

    private Timer mTimer; // 计时器，每1秒执行一次任务
    private MyTimerTask mTimerTask; // 计时任务，判断是否未操作时间到达ns
    private long mLastActionTime; // 上一次操作时间

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        startTimer();
        super.onResume();
    }
    //时间计时器，长时间未操作返回首页。
    private void startTimer() {
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        // 初始化上次操作时间为打开页面的时间
        mLastActionTime = System.currentTimeMillis();
        // 每过10s检查一次
        mTimer.schedule(mTimerTask, 0, 10000);
        //Log.e(TAG, "打开页面开始计时");
    }

    // 每当用户接触了屏幕，都会执行此方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mLastActionTime = System.currentTimeMillis();
        //Log.e(TAG, "正在点击屏幕");
        return super.dispatchTouchEvent(ev);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("计时中……");
            // 5分钟未操作停止计时并返回首页
            if (System.currentTimeMillis() - mLastActionTime > 1000 * 60 * 5) {
                stopTimer();// 停止计时任务
                resetSprfMain();//返回首页
            }
        }
    }

    //长时间未操作返回首页
    public void resetSprfMain() {
        Intent i = new Intent(BaseActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // 停止计时任务
    public void stopTimer() {
        mTimer.cancel();
        //Log.e("", "取消计时");
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        stopTimer();
        super.onDestroy();
    }
}
