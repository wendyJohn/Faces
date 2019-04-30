package com.example.juicekaaa.fireserver.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.example.juicekaaa.fireserver.MainActivity;
import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.utils.FullVideoView;
import com.example.juicekaaa.fireserver.utils.SosDialog;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.io.File;

/**
 * 功能首页
 */
public class FunctionHomesActivity extends BaseActivity implements View.OnClickListener {

    private FullVideoView videos;
    private TextView online_help;
    private ShimmerTextView myShimmerTextView;
    private Shimmer shimmer;
    private ImageView materialopening;
    private ImageView sos;
    private TextView historicalrecord;
    private TextView polularscience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funtion_homes_activity);
        intiView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.funtion_home_activity;
    }

    //初始化
    private void intiView() {
        myShimmerTextView = findViewById(R.id.shimmer_tv);
        videos = findViewById(R.id.videos);
        online_help = findViewById(R.id.online_help);
        materialopening = findViewById(R.id.materialopening);
        polularscience = findViewById(R.id.polularscience);
        sos = findViewById(R.id.sos);
        historicalrecord = findViewById(R.id.historicalrecord);
        online_help.setOnClickListener(this);
        materialopening.setOnClickListener(this);
        polularscience.setOnClickListener(this);
        sos.setOnClickListener(this);
        historicalrecord.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        hideBottomUIMenu();
        setVideo();
        shimmer = new Shimmer();
        shimmer.start(myShimmerTextView);
        super.onResume();
    }

    /**
     * 设置视频参数
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setVideo() {
        videos = findViewById(R.id.videos);
        MediaController mediaController = new MediaController(FunctionHomesActivity.this);
        mediaController.setVisibility(View.GONE);//隐藏进度条
        videos.setMediaController(mediaController);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + "FireVideo", "1542178640266.mp4");
        videos.setVideoPath(file.getAbsolutePath());
        videos.start();
        videos.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                mediaPlayer.start();
//                mediaPlayer.setLooping(true);

                File file = new File(Environment.getExternalStorageDirectory() + "/" + "FireVideo", "1542178640266.mp4");
                videos.setVideoPath(file.getAbsolutePath());
                videos.start();
            }
        });
        videos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                shimmer.cancel();
                Intent i = new Intent(FunctionHomesActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                finish();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //物资开门
            case R.id.materialopening:
                shimmer.cancel();
                Intent funtion_material = new Intent(FunctionHomesActivity.this, FunctionOperationActivity.class);
                startActivity(funtion_material);
                break;
            //SOS
            case R.id.sos:
                SosDialog sosDialog = new SosDialog(FunctionHomesActivity.this);
                sosDialog.show();
                break;
            //历史记录
            case R.id.historicalrecord:
                shimmer.cancel();
                Intent historicalrecord = new Intent(FunctionHomesActivity.this, Historicalrecord_Activity.class);
                startActivity(historicalrecord);
                break;
            //在线帮助
            case R.id.online_help:
                shimmer.cancel();
                Intent funtion_help = new Intent(FunctionHomesActivity.this, FunctionHelpActivity.class);
                startActivity(funtion_help);
                break;
            //消防科普
            case R.id.polularscience:
                shimmer.cancel();
                Intent PolularScience = new Intent(FunctionHomesActivity.this, PolularScienceActivity.class);
                startActivity(PolularScience);
                break;

        }
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
        shimmer.cancel();
    }


}
