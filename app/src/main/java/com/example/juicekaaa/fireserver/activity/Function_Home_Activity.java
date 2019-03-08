package com.example.juicekaaa.fireserver.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fireserver.MainActivity;
import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.utils.FullVideoView;
import com.example.juicekaaa.fireserver.utils.SosDialog;

import java.io.File;

/**
 * 功能首页
 */
public class Function_Home_Activity extends BaseActivity implements View.OnClickListener {

    private FullVideoView videos;
    private TextView funtion_material;
    private TextView funtion_opendoor;
    private TextView funtion_sos;
    private TextView funtion_propaganda;
    private TextView online_help;

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
        setContentView(R.layout.funtion_home_activity);
        intiView();
    }

    //初始化
    private void intiView() {
        videos = findViewById(R.id.videos);
        funtion_material = findViewById(R.id.funtion_material);
        funtion_opendoor = findViewById(R.id.funtion_opendoor);
        funtion_sos = findViewById(R.id.funtion_sos);
        funtion_propaganda = findViewById(R.id.funtion_propaganda);
        online_help = findViewById(R.id.online_help);

        funtion_material.setOnClickListener(this);
        funtion_opendoor.setOnClickListener(this);
        funtion_sos.setOnClickListener(this);
        funtion_propaganda.setOnClickListener(this);
        online_help.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        hideBottomUIMenu();
        setVideo();
        super.onResume();
    }

    /**
     * 设置视频参数
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setVideo() {
        videos = findViewById(R.id.videos);
        MediaController mediaController = new MediaController(Function_Home_Activity.this);
        mediaController.setVisibility(View.GONE);//隐藏进度条
        videos.setMediaController(mediaController);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + "FireVideo", "1542178640266.mp4");
        videos.setVideoPath(file.getAbsolutePath());
        videos.start();
        videos.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        videos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                stopTimer();
                Intent i = new Intent(Function_Home_Activity.this, MainActivity.class);
                startActivity(i);
                finish();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //物资
            case R.id.funtion_material:
                Intent funtion_material = new Intent(Function_Home_Activity.this, Function_Operation_Activity.class);
                startActivity(funtion_material);
                stopTimer();
                break;
            //开门
            case R.id.funtion_opendoor:
                Intent funtion_opendoor = new Intent(Function_Home_Activity.this, Function_Operation_Activity.class);
                startActivity(funtion_opendoor);
                stopTimer();
                break;
            //SOS
            case R.id.funtion_sos:
                SosDialog sosDialog = new SosDialog(Function_Home_Activity.this);
                sosDialog.show();
                break;
            //宣传
            case R.id.funtion_propaganda:

                break;
            //在线帮助
            case R.id.online_help:
                Intent funtion_help = new Intent(Function_Home_Activity.this, Function_help_Activity.class);
                startActivity(funtion_help);
                stopTimer();
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
        stopTimer();
    }


}
