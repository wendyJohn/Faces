package com.example.juicekaaa.fireserver.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DoorPasswordView extends LinearLayout implements View.OnClickListener, PasswordEditText.PasswordFullListener {
    private LinearLayout mKeyBoardView;
    private PasswordEditText mPasswordEditText;
    private static final String BROADCAST_PASS_DISC = "com.permissions.MYP_BROADCAST";
    private static final String BROADCAST_ACTION_PASS = "com.permissions.myp_broadcast";
    private static final String BROADCAST_DISMISS_DISC = "com.permissions.MYD_BROADCAST";
    private static final String BROADCAST_ACTION_DISMISS = "com.permissions.myd_broadcast";
    private Context context;
    String checkedValues;
    private List<String> doorlist;//选中柜门的参数数组

    public DoorPasswordView(Context context, String checkedValues) {
        this(context, checkedValues,null);
        this.context = context;
        this.checkedValues = checkedValues;
    }

    public DoorPasswordView(Context context, String checkedValues, @Nullable AttributeSet attrs) {
        this(context,checkedValues, attrs, 0);
    }

    public DoorPasswordView(Context context, String checkedValues, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs, defStyleAttr);
        inflate(context, R.layout.pay_password_layout, this);
        mKeyBoardView = findViewById(R.id.keyboard);
        mPasswordEditText = findViewById(R.id.passwordEdt);
        mPasswordEditText.setPasswordFullListener(this);
        setItemClickListener(mKeyBoardView);
    }

    /**
     * 给每一个自定义数字键盘上的View 设置点击事件
     *
     * @param view
     */
    private void setItemClickListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                //不断的给里面所有的View设置setOnClickListener
                View childView = ((ViewGroup) view).getChildAt(i);
                setItemClickListener(childView);
            }
        } else {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            String number = ((TextView) v).getText().toString().trim();
            mPasswordEditText.addPassword(number);
        }
        if (v instanceof ImageView) {
            mPasswordEditText.deletePassword();
        }
        if (v instanceof Button) {
            Intent myintent = new Intent(BROADCAST_ACTION_DISMISS);
            context.sendBroadcast(myintent, BROADCAST_DISMISS_DISC);
        }
    }

    @Override
    public void passwordFull(String password) {
        if (password.equals("123456")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageEvent messageEvent = new MessageEvent(MyApplication.MESSAGE_DOOR);
                    EventBus.getDefault().post(messageEvent);
                }
            }).start();

        } else {
            Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
        }
    }


}
