package com.example.juicekaaa.fireserver.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.adapter.MaterialAdapter;
import com.example.juicekaaa.fireserver.api.FaceApi;
import com.example.juicekaaa.fireserver.entity.DoorOrder;
import com.example.juicekaaa.fireserver.entity.Group;
import com.example.juicekaaa.fireserver.net.Acquisition_materials;
import com.example.juicekaaa.fireserver.net.ArchitectureBean;
import com.example.juicekaaa.fireserver.ui.OpenDoorActivity;
import com.example.juicekaaa.fireserver.ui.RgbVideoIdentityActivity;
import com.example.juicekaaa.fireserver.utils.CommonUtil;
import com.example.juicekaaa.fireserver.utils.MessageEvent;
import com.example.juicekaaa.fireserver.utils.SVProgressHUD;
import com.example.juicekaaa.fireserver.utils.SosDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能操作界面
 */
public class Function_Operation_Activity extends BaseActivity implements View.OnClickListener {
    @BindViews({R.id.a_checkboxs, R.id.b_checkboxs, R.id.c_checkboxs, R.id.d_checkboxs, R.id.e_checkboxs, R.id.f_checkboxs, R.id.g_checkboxs})
    List<CheckBox> mycheckBoxes; // 多选组
    private TextView back;
    private TextView confirm_opening;
    private TextView One_key_door;
    private TextView material;
    private TextView opendoor;
    private TextView sos;
    private TextView propaganda;
    private String checkedValues;//选中柜门的参数
    private List<String> doorlist;//选中柜门的参数数组
    @BindView(R.id.a_listview)
    ListView a_listview;
    @BindView(R.id.b_listview)
    ListView b_listview;
    @BindView(R.id.c_listview)
    ListView c_listview;
    @BindView(R.id.d_listview)
    ListView d_listview;
    @BindView(R.id.e_listview)
    ListView e_listview;
    @BindView(R.id.f_listview)
    ListView f_listview;
    @BindView(R.id.g_listview)
    ListView g_listview;
    private MaterialAdapter materialAdapter;
    private String[] items;

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
        setContentView(R.layout.activity_materialopening);
        EventBus.getDefault().register(this);
        intiView();
    }

    //初始化
    private void intiView() {
        ButterKnife.bind(this);
        back = findViewById(R.id.back);
        confirm_opening = findViewById(R.id.confirm_opening);
        One_key_door = findViewById(R.id.One_key_door);
        material = findViewById(R.id.material);
        opendoor = findViewById(R.id.opendoor);
        sos = findViewById(R.id.sos);
        propaganda = findViewById(R.id.propaganda);
        back.setOnClickListener(this);
        One_key_door.setOnClickListener(this);
        confirm_opening.setOnClickListener(this);
        material.setOnClickListener(this);
        opendoor.setOnClickListener(this);
        sos.setOnClickListener(this);
        propaganda.setOnClickListener(this);
        List<Group> groupList = FaceApi.getInstance().getGroupList(0, 1000);
        items = new String[groupList.size()];
        for (int i = 0; i < groupList.size(); i++) {
            Group group = groupList.get(i);
            items[i] = group.getGroupId();
        }
    }

    @Override
    protected void onResume() {
        hideBottomUIMenu();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载物资信息
                Acquisition_materials.addMaterial();
            }
        }).start();
        super.onResume();
    }


    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
            case MyApplication.MESSAGE_MATERIAL://物资信息
                List<ArchitectureBean> a_list = messageEvent.getA_list();
                List<ArchitectureBean> b_list = messageEvent.getB_list();
                List<ArchitectureBean> c_list = messageEvent.getC_list();
                List<ArchitectureBean> d_list = messageEvent.getD_list();
                List<ArchitectureBean> e_list = messageEvent.getE_list();
                List<ArchitectureBean> f_list = messageEvent.getF_list();
                List<ArchitectureBean> g_list = messageEvent.getG_list();

                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, a_list);
                a_listview.setAdapter(materialAdapter);
                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, b_list);
                b_listview.setAdapter(materialAdapter);
                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, c_list);
                c_listview.setAdapter(materialAdapter);
                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, d_list);
                d_listview.setAdapter(materialAdapter);
                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, e_list);
                e_listview.setAdapter(materialAdapter);
                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, f_list);
                f_listview.setAdapter(materialAdapter);
                materialAdapter = new MaterialAdapter(Function_Operation_Activity.this, g_list);
                g_listview.setAdapter(materialAdapter);
                break;
        }
    }

    /**
     * 复选项点击事件
     *
     * @param checkBoxs
     */
    @OnClick({R.id.a_checkboxs, R.id.b_checkboxs, R.id.c_checkboxs, R.id.d_checkboxs, R.id.e_checkboxs, R.id.f_checkboxs, R.id.g_checkboxs})
    void changeCheckBoxs(CheckBox checkBoxs) {
        // 显示选中项值
        checkedValues = CommonUtil.getMany(mycheckBoxes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            //确定开门
            case R.id.confirm_opening:
                if (checkedValues == null || "".equals(checkedValues)) {
                    SVProgressHUD.showErrorWithStatus(Function_Operation_Activity.this, "请选择相应要开的柜门！");
                } else {
                    Intent intent_confirm_opening = new Intent(Function_Operation_Activity.this, RgbVideoIdentityActivity.class);
                    intent_confirm_opening.putExtra("checkedValues",checkedValues);
                    intent_confirm_opening.putExtra("group_id", items[0]);
                    startActivityForResult(intent_confirm_opening,0x99);
                    stopTimer();
                }
                break;
            //一键开门
            case R.id.One_key_door:
                Intent intent_One_key_door = new Intent(this, RgbVideoIdentityActivity.class);
                intent_One_key_door.putExtra("checkedValues","A,B,C,D,E,F,G");
                intent_One_key_door.putExtra("group_id", items[0]);
                startActivityForResult(intent_One_key_door,0x99);
                stopTimer();
                break;
            //物资
            case R.id.material:

                break;
            //开门
            case R.id.opendoor:

                break;
            //SOS
            case R.id.sos:
                SosDialog sosDialog = new SosDialog(Function_Operation_Activity.this);
                sosDialog.show();
                break;
            //宣传
            case R.id.propaganda:

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

    // 为了获取结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
        if (resultCode == 0x99) {
            String mycheckedValues = data.getStringExtra("checkedValues");

            //数值
            doorlist = new ArrayList();
            String[] split = mycheckedValues.split(",");
            for (int i = 0; i < split.length; i++) {
                doorlist.add(split[i]);
            }
            try {
                DoorOrder.getInstance().init(doorlist,Function_Operation_Activity.this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
