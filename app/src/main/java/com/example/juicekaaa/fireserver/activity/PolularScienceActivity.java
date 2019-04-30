package com.example.juicekaaa.fireserver.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.juicekaaa.fireserver.R;

/**
 * 消防科普
 */
public class PolularScienceActivity extends BaseActivity implements View.OnClickListener {
    private TextView back;
    private WebView webview;
    private WebView webviews;
    private TextView backpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polularsience);
        intiView();
    }

    //初始化
    @SuppressLint("ClickableViewAccessibility")
    private void intiView() {
        back = findViewById(R.id.back);
        webview = findViewById(R.id.webview);
        webviews = findViewById(R.id.webviews);
        backpage= findViewById(R.id.backpage);
        webview.loadUrl("https://2437789620.github.io/patrolOne/");//加载url
        webview.setBackgroundColor(2);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        //自适应屏幕
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

        webviews.loadUrl("https://2437789620.github.io/patrolTwo/");//加载url
        webviews.setBackgroundColor(2);
        WebSettings webSetting = webviews.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setUseWideViewPort(true); //将图片调整到适合webview的大小
        //自适应屏幕
        webviews.getSettings().setUseWideViewPort(true);
        webviews.getSettings().setLoadWithOverviewMode(true);


        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                webviews.setVisibility(View.VISIBLE);
                webviews.reload();
                backpage.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);
                return false;
            }
        });

        back.setOnClickListener(this);
        backpage.setOnClickListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_polularsience;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            case R.id.backpage:
                webview.setVisibility(View.VISIBLE);
                webviews.setVisibility(View.GONE);
                backpage.setVisibility(View.GONE);
                break;

        }
    }
}