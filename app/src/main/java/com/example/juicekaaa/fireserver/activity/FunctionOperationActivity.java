package com.example.juicekaaa.fireserver.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.api.FaceApi;
import com.baidu.aip.db.DBManager;
import com.baidu.aip.entity.Group;
import com.baidu.aip.utils.GlobalSet;
import com.baidu.aip.utils.PreferencesUtil;
import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.R;
import com.example.juicekaaa.fireserver.adapter.FunctionAdapter;
import com.example.juicekaaa.fireserver.adapter.MaterialAdapter;
import com.example.juicekaaa.fireserver.data.DBHelpers;
import com.example.juicekaaa.fireserver.face.activity.LivenessSettingActivity;
import com.example.juicekaaa.fireserver.face.activity.OrbbecProVideoIdentifyActivity;
import com.example.juicekaaa.fireserver.face.activity.RegActivity;
import com.example.juicekaaa.fireserver.face.activity.RgbVideoIdentityActivity;
import com.example.juicekaaa.fireserver.fid.entity.EPC;
import com.example.juicekaaa.fireserver.fid.entity.Lock;
import com.example.juicekaaa.fireserver.fid.serialportapi.ReaderServiceImpl;
import com.example.juicekaaa.fireserver.fid.service.CallBack;
import com.example.juicekaaa.fireserver.fid.service.CallBackStopReadCard;
import com.example.juicekaaa.fireserver.fid.service.CallBacks;
import com.example.juicekaaa.fireserver.fid.service.ReaderService;
import com.example.juicekaaa.fireserver.fid.tool.ReaderUtil;
import com.example.juicekaaa.fireserver.fid.util.DataFilter;
import com.example.juicekaaa.fireserver.net.Acquisition_materials;
import com.example.juicekaaa.fireserver.net.ArchitectureBean;
import com.example.juicekaaa.fireserver.net.NetCallBack;
import com.example.juicekaaa.fireserver.net.RequestUtils;
import com.example.juicekaaa.fireserver.net.URLs;
import com.example.juicekaaa.fireserver.utils.CommonUtil;
import com.example.juicekaaa.fireserver.utils.DoorPasswordView;
import com.example.juicekaaa.fireserver.utils.MessageEvent;
import com.example.juicekaaa.fireserver.utils.PayPasswordView;
import com.example.juicekaaa.fireserver.utils.PreferenceUtils;
import com.example.juicekaaa.fireserver.utils.SVProgressHUD;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能操作界面
 */
public class FunctionOperationActivity extends BaseActivity implements View.OnClickListener, CallBacks {
    @BindViews({R.id.a_checkboxs, R.id.b_checkboxs, R.id.c_checkboxs, R.id.d_checkboxs, R.id.e_checkboxs, R.id.f_checkboxs, R.id.g_checkboxs})
    List<CheckBox> mycheckBoxes; // 多选组
    private TextView back;
    private TextView confirm_opening;
    private TextView One_key_door;
    private String checkedValues;//选中柜门的参数
    private List<String> doorlist;//选中柜门的参数数组
    private BottomSheetDialog bottomSheetDialog;
    private Handler handler = new Handler();
    private Handler handlers = new Handler();
    private DBHelpers mOpenHelper;
    ReaderService readerService = new ReaderServiceImpl();
    private List<EPC> listEPC;
    private List<String> listEpc;
    private List<String> listAnt;
    private TextView passwordopen;
    private TextView faceregistration;
    private int num = 0;
    static int COUNTS = 5;//点击次数
    static long DURATION = 3 * 1000;//规定有效时间
    long[] mHits = new long[COUNTS];
    private GridView a_gridView;
    private GridView b_gridView;
    private GridView c_gridView;
    private GridView d_gridView;
    private GridView e_gridView;
    private GridView f_gridView;
    private GridView g_gridView;
    private FunctionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialopening);
        intiView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_materialopening;
    }

    //初始化
    private void intiView() {
        // 使用人脸1：n时使用
        DBManager.getInstance().init(this);
        livnessTypeTip();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        back = findViewById(R.id.back);
        confirm_opening = findViewById(R.id.confirm_opening);
        One_key_door = findViewById(R.id.One_key_door);
        faceregistration = findViewById(R.id.faceregistration);
        passwordopen = findViewById(R.id.passwordopen);
        back.setOnClickListener(this);
        One_key_door.setOnClickListener(this);
        confirm_opening.setOnClickListener(this);
        faceregistration.setOnClickListener(this);
        passwordopen.setOnClickListener(this);

        List<Group> groupList = FaceApi.getInstance().getGroupList(0, 1000);
        if (groupList.size() <= 0) {
            Toast.makeText(this, "还没有分组，请创建分组并添加用户", Toast.LENGTH_SHORT).show();
            return;
        }
        items = new String[groupList.size()];
        for (int i = 0; i < groupList.size(); i++) {
            Group group = groupList.get(i);
            items[i] = group.getGroupId();
        }
        mOpenHelper = new DBHelpers(this);
        listEPC = new ArrayList<>();
        listEpc = new ArrayList<>();
        listAnt = new ArrayList<>();

        a_gridView = findViewById(R.id.a_gridView);
        b_gridView = findViewById(R.id.b_gridView);
        c_gridView = findViewById(R.id.c_gridView);
        d_gridView = findViewById(R.id.d_gridView);
        e_gridView = findViewById(R.id.e_gridView);
        f_gridView = findViewById(R.id.f_gridView);
        g_gridView = findViewById(R.id.g_gridView);
    }

    @Override
    protected void onResume() {
        hideBottomUIMenu();
        Acquisition_materials.addMaterial(FunctionOperationActivity.this);
        handler.postDelayed(runnable, 5 * 1000);
        handlers.postDelayed(runnables, 5 * 1000);
        super.onResume();
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //加载物资信息
            Acquisition_materials.addMaterial(FunctionOperationActivity.this);
            handler.postDelayed(this, 5 * 1000);//实现循环
        }
    };

    private Runnable runnables = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //实时盘点
            invOnce();
            handlers.postDelayed(this, 5 * 1000);//实现循环
        }
    };

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

                if (a_list.size() > 4) {
                    a_gridView.setNumColumns(3);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, a_list);
                    a_gridView.setAdapter(adapter);
                } else {
                    a_gridView.setNumColumns(2);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, a_list);
                    a_gridView.setAdapter(adapter);
                }

                if (b_list.size() > 4) {
                    b_gridView.setNumColumns(3);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, b_list);
                    b_gridView.setAdapter(adapter);
                } else {
                    b_gridView.setNumColumns(2);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, b_list);
                    b_gridView.setAdapter(adapter);
                }

                if (c_list.size() > 4) {
                    c_gridView.setNumColumns(3);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, c_list);
                    c_gridView.setAdapter(adapter);
                } else {
                    c_gridView.setNumColumns(2);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, c_list);
                    c_gridView.setAdapter(adapter);
                }

                if (d_list.size() > 4) {
                    d_gridView.setNumColumns(3);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, d_list);
                    d_gridView.setAdapter(adapter);
                } else {
                    d_gridView.setNumColumns(2);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, d_list);
                    d_gridView.setAdapter(adapter);
                }

                if (e_list.size() > 4) {
                    e_gridView.setNumColumns(3);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, e_list);
                    e_gridView.setAdapter(adapter);
                } else {
                    e_gridView.setNumColumns(2);
                    adapter = new FunctionAdapter(FunctionOperationActivity.this, e_list);
                    e_gridView.setAdapter(adapter);
                }

                if(f_list.size()>4){
                    f_gridView.setNumColumns(3);
                    adapter=new FunctionAdapter(FunctionOperationActivity.this, f_list);
                    f_gridView.setAdapter(adapter);
                }else{
                    f_gridView.setNumColumns(2);
                    adapter=new FunctionAdapter(FunctionOperationActivity.this, f_list);
                    f_gridView.setAdapter(adapter);
                }

                if(g_list.size()>4){
                    g_gridView.setNumColumns(3);
                    adapter=new FunctionAdapter(FunctionOperationActivity.this, g_list);
                    g_gridView.setAdapter(adapter);
                }else{
                    g_gridView.setNumColumns(2);
                    adapter=new FunctionAdapter(FunctionOperationActivity.this, g_list);
                    g_gridView.setAdapter(adapter);
                }
                break;
            case MyApplication.MESSAGE_BACK://返回
                bottomSheetDialog.dismiss();
                //数值
                doorlist = new ArrayList();
                String[] split = checkedValues.split(",");
                for (int i = 0; i < split.length; i++) {
                    doorlist.add(split[i].trim());
                }
                try {
                    Lock.getInstance().inits(doorlist, FunctionOperationActivity.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case MyApplication.MESSAGE_DISMISS://返回
                bottomSheetDialog.dismiss();
                break;
            case MyApplication.MESSAGE_DOOR:
                handler.removeCallbacks(runnable);
                handlers.removeCallbacks(runnables);
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(this, RegActivity.class);
                startActivity(intent);
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
                handler.removeCallbacks(runnable);
                handlers.removeCallbacks(runnables);
                finish();
                break;
            //人脸开门
            case R.id.confirm_opening:
//                PreferenceUtils.setString(FunctionOperationActivity.this, "FaceUserName", "");
                handler.removeCallbacks(runnable);
                handlers.removeCallbacks(runnables);
                if (checkedValues == null || "".equals(checkedValues)) {
                    SVProgressHUD.showErrorWithStatus(FunctionOperationActivity.this, "请选择相应要开的柜门！");
                } else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager
                            .PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
                        return;
                    }
                    showSingleAlertDialog("OpenTheDoor");
                }
                break;
            //密码开门
            case R.id.passwordopen:
                fiveClick(1);
                break;
//            case R.id.confirm_openings:
//                inventoryLocation();
//                if (checkedValues == null || "".equals(checkedValues)) {
//                    SVProgressHUD.showErrorWithStatus(FunctionOperationActivity.this, "请选择相应要开的柜门！");
//                } else {
//                    openPayPasswordDialog(checkedValues);//临时用
//                }
//                break;
            //一键开门
            case R.id.One_key_door:
                handler.removeCallbacks(runnable);
                handlers.removeCallbacks(runnables);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
                    return;
                }
                showSingleAlertDialog("OneKeyDoor");
                break;
            //人脸注册
            case R.id.faceregistration:
                openPayPasswordDialog();
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
//            PreferenceUtils.setString(FunctionOperationActivity.this, "doorPermission", "Close");
            String mycheckedValues = data.getStringExtra("checkedValues");
            //数值
            doorlist = new ArrayList();
            String[] split = mycheckedValues.split(",");
            for (int i = 0; i < split.length; i++) {
                doorlist.add(split[i].trim());
            }
            try {
//                DoorOrder.getInstance().init(doorlist, FunctionOperationActivity.this);
                Lock.getInstance().inits(doorlist, FunctionOperationActivity.this);
//                getLocksStatus("查询锁是否都关闭");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        handlers.removeCallbacks(runnables);
        super.onDestroy();
    }

    //密码输入验证开门
    private void openPayPasswordDialogs(String checkedValues) {
        PayPasswordView payPasswordView = new PayPasswordView(this, checkedValues);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(payPasswordView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    //密码输入验证注册
    private void openPayPasswordDialog() {
        DoorPasswordView doorPasswordView = new DoorPasswordView(this, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(doorPasswordView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    //    private AlertDialog alertDialog;
    private String[] items;

    public void showSingleAlertDialog(String type) {
        List<Group> groupList = FaceApi.getInstance().getGroupList(0, 1000);
        if (groupList.size() <= 0) {
            Toast.makeText(this, "还没有分组，请创建分组并添加用户", Toast.LENGTH_SHORT).show();
            return;
        }
        items = new String[groupList.size()];
        for (int i = 0; i < groupList.size(); i++) {
            Group group = groupList.get(i);
            items[i] = group.getGroupId();
        }
        choiceIdentityType(items[0], type);

    }

    private void choiceIdentityType(String groupId, String mytype) {
        int type = PreferencesUtil.getInt(LivenessSettingActivity.TYPE_LIVENSS, LivenessSettingActivity
                .TYPE_NO_LIVENSS);
        if (type == LivenessSettingActivity.TYPE_NO_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：无活体", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(FunctionOperationActivity.this, RgbVideoIdentityActivity.class);
            intent.putExtra("group_id", groupId);
            startActivity(intent);
        } else if (type == LivenessSettingActivity.TYPE_RGB_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：单目RGB活体", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(FunctionOperationActivity.this, RgbVideoIdentityActivity.class);
            intent.putExtra("group_id", groupId);
            startActivity(intent);
        } else if (type == LivenessSettingActivity.TYPE_RGB_DEPTH_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：双目RGB+Depth活体", Toast.LENGTH_LONG).show();
            int cameraType = PreferencesUtil.getInt(GlobalSet.TYPE_CAMERA, GlobalSet.ORBBEC);
            Intent intent = null;
            if (cameraType == GlobalSet.ORBBECPRO) {
                intent = new Intent(FunctionOperationActivity.this, OrbbecProVideoIdentifyActivity.class);
            } else if (cameraType == GlobalSet.ORBBECPROS1) {
                intent = new Intent(FunctionOperationActivity.this, OrbbecProVideoIdentifyActivity.class);
            } else if (cameraType == GlobalSet.ORBBECPRODABAI) {
                intent = new Intent(FunctionOperationActivity.this, OrbbecProVideoIdentifyActivity.class);
            } else if (cameraType == GlobalSet.ORBBECPRODEEYEA) {
                intent = new Intent(FunctionOperationActivity.this, OrbbecProVideoIdentifyActivity.class);
            } else if (cameraType == GlobalSet.ORBBECATLAS) {
                intent = new Intent(FunctionOperationActivity.this, OrbbecProVideoIdentifyActivity.class);
            }
            if (intent != null) {
                handler.removeCallbacks(runnable);
                if (mytype.equals("OneKeyDoor")) {
                    intent.putExtra("group_id", groupId);
                    intent.putExtra("checkedValues", "A,B,C,D,E,F,G");
                    startActivityForResult(intent, 0x99);
                } else {
                    intent.putExtra("group_id", groupId);
                    intent.putExtra("checkedValues", checkedValues);
                    startActivityForResult(intent, 0x99);
                }
            }
        }
    }

    private void livnessTypeTip() {
        try {
            int type = PreferencesUtil.getInt(LivenessSettingActivity.TYPE_LIVENSS, LivenessSettingActivity
                    .TYPE_NO_LIVENSS);
            if (type == LivenessSettingActivity.TYPE_NO_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：无活体, 请选用普通USB摄像头", Toast.LENGTH_LONG).show();
            } else if (type == LivenessSettingActivity.TYPE_RGB_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：单目RGB活体, 请选用普通USB摄像头", Toast.LENGTH_LONG).show();
            } else if (type == LivenessSettingActivity.TYPE_RGB_IR_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：双目RGB+IR活体, 请选用RGB+IR摄像头",
//                    Toast.LENGTH_LONG).show();
            } else if (type == LivenessSettingActivity.TYPE_RGB_DEPTH_LIVENSS) {
//            Toast.makeText(this, "当前活体策略：双目RGB+Depth活体，请选用RGB+Depth摄像头", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    //盘点物资出入库（6秒盘点一次）
    private void invOnce() {
        if (null == ReaderUtil.readers) {
            System.out.println("请先连接设备");
            Fid();//重连串口
            return;
        }
        listEPC.removeAll(listEPC);
        listEpc.removeAll(listEpc);
        readerService.invOnceV2(ReaderUtil.readers, new ReadData());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                System.out.println("=======EPC大小========" + listEPC.size());
                if (listEPC.size() <= 0) {
                    readerService.stopInvV2(ReaderUtil.readers, new StopReadData());
                    return;
                }
                // 等待2000毫秒后获取卡号，并比对。
                for (int i = 0; i < listEPC.size(); i++) {
                    String epc = listEPC.get(i).getEpc();
                    String myant = listEPC.get(i).getAnt();
                    listEpc.add(epc);
                }

                JSONArray array = new JSONArray();
                Cursor cursor = mOpenHelper.query("select * from materialtable", null);
                while (cursor.moveToNext()) {
                    String epcs = cursor.getString(cursor.getColumnIndex("Epc"));
                    String staus = cursor.getString(cursor.getColumnIndex("Staus"));
                    String ant = cursor.getString(cursor.getColumnIndex("Ant"));
                    String ids = cursor.getString(cursor.getColumnIndex("Ids"));
                    String StationName = cursor.getString(cursor.getColumnIndex("StationName"));
                    String StationId = cursor.getString(cursor.getColumnIndex("StationId"));
                    String StorageLocation = cursor.getString(cursor.getColumnIndex("StorageLocation"));
                    JSONObject object = new JSONObject();
                    Boolean exists = ((List) listEpc).contains(epcs);
                    if (exists) {
                        System.out.println(epcs + "有卡号信息！");
                        //当有卡时判断状态，如果是出库状态则认为现在为入库。
                        if (staus.equals("retrieval")) {
                            System.out.println(epcs + "物资入库");
                            mOpenHelper.update(epcs, "warehousing");
                            try {
                                object.put("ids", ids);
                                object.put("state", "emergencystation_in");
                                object.put("agentName", PreferenceUtils.getString(FunctionOperationActivity.this, "FaceUserName"));
                                object.put("stationName", StationName);
                                object.put("stationId", StationId);
                                object.put("storageLocation", StorageLocation);
                                array.put(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //当无卡时则认为现在为出库，并修改物资为出库状态。
                    else {
                        if (staus.equals("warehousing")) {
                            System.out.println(epcs + "物资出库");
                            mOpenHelper.update(epcs, "retrieval");
                            try {
                                object.put("ids", ids);
                                object.put("state", "emergencystation_out");
                                object.put("agentName", PreferenceUtils.getString(FunctionOperationActivity.this, "FaceUserName"));
                                object.put("stationName", StationName);
                                object.put("stationId", StationId);
                                object.put("storageLocation", StorageLocation);
                                array.put(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
                cursor.close();
                //提交盘点数据
                System.out.println("========Array大小============" + array.length());
                if (array.length() > 0) {
                    Submission(array);
                }
            }
        }, 2000);


    }


    //盘点位置信息（盘点一次）
    private void inventoryLocation() {
        if (null == ReaderUtil.readers) {
            System.out.println("请先连接设备");
            Fid();//重连串口
            return;
        }
        listEPC.removeAll(listEPC);
        listAnt.removeAll(listAnt);
        readerService.invOnceV2(ReaderUtil.readers, new ReadData());
        System.out.println("=======EPC大小========" + listEPC.size());
        if (listEPC.size() <= 0) {
            disconnect();//断开串口
            return;
        }
        // 获取卡号，并比对。
        for (int i = 0; i < listEPC.size(); i++) {
            String epc = listEPC.get(i).getEpc();
            String myant = listEPC.get(i).getAnt();
            Cursor cursor = mOpenHelper.query("select * from materialtable where Epc=" + "'" + epc + "'", null);
            while (cursor.moveToNext()) {
                String epcs = cursor.getString(cursor.getColumnIndex("Epc"));
                String ant = cursor.getString(cursor.getColumnIndex("Ant"));
                if (epc.equals(epcs) && myant.equals(ant)) {
                    System.out.println(epcs + "位置正确！");
                } else {
                    System.out.println(epcs + "位置错误");
                    Cursor cursors = mOpenHelper.query("select * from anttable where Ant=" + "'" + myant + "'", null);
                    while (cursors.moveToNext()) {
                        String StorageLocation = cursors.getString(cursors.getColumnIndex("StorageLocation"));
                        System.out.println("错误位置" + StorageLocation);
                        SubStorageLocation(epcs, StorageLocation);
                    }
                    cursors.close();
                }
            }
            cursor.close();
        }
    }

    @Override
    public void locksStatus(Boolean result) {
        if (result == false) {
            num = 0;
            System.out.println("===========开始盘点位置==============");
            inventoryLocation();
        }
    }


    class ReadData implements CallBack {
        @Override
        public void readData(String data, String antNo) {
            addToList(listEPC, data, antNo);
        }

        @Override
        public void readData(String data, String rssi, String antNo, String deviceNo, String direction) {
            addToList(listEPC, data, rssi, antNo, deviceNo, direction);
        }
    }

    class StopReadData implements CallBackStopReadCard {
        @Override
        public void stopReadCard(final boolean result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (result) {
                        System.out.println("======停止读卡成功，关闭串口=======");
                        disconnect();
                    } else {
                        System.out.println("======停止读卡失败=======");
                    }
                }
            });
        }
    }

    private void addToList(final List<EPC> listEPC2, final String epc, final String ant) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DataFilter.dataFilter(listEPC2, epc, ant);

            }
        });
    }

    private void addToList(final List<EPC> listEPC2, final String epc, final String rssi, final String ant, final String deviceNo, final String direction) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DataFilter.dataFilter(listEPC2, epc, rssi, ant, deviceNo, direction);
            }
        });
    }

    //断开Fid串口
    private void disconnect() {
        if (ReaderUtil.readers != null) {
            readerService.disconnect(ReaderUtil.readers);
            ReaderUtil.readers = null;
        }
    }

    //连接Fid串口
    private void Fid() {
        if (ReaderUtil.readers == null) {
            ReaderUtil.readers = readerService.serialPortConnect("/dev/ttysWK1", 115200);
            if (ReaderUtil.readers != null) {
                System.out.println("FID串口连接成功");
                readerService.version(ReaderUtil.readers);
            } else {
                System.out.println("FID串口连接失败");
            }
        }
    }

    //提交盘点物资出入库
    public void Submission(JSONArray array) {
        RequestParams params = new RequestParams();
        params.put("list", array.toString());
        params.put("username", "admin");
        params.put("platformkey", "app_firecontrol_owner");
        RequestUtils.ClientPost(URLs.Submission_URL, params,
                new NetCallBack() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null || result.length() == 0) {
                            return;
                        }
                    }

                    @Override
                    public void onMyFailure(Throwable arg0) {
                    }
                });
    }

    //提交盘点物资出入库
    public void SubStorageLocation(String epc, String StorageLocation) {
        RequestParams params = new RequestParams();
        params.put("RFID", epc);
        params.put("storageLocation", StorageLocation);
        params.put("username", "admin");
        params.put("platformkey", "app_firecontrol_owner");
        RequestUtils.ClientPost(URLs.StorageLocation_URL, params,
                new NetCallBack() {
                    @Override
                    public void onStart() {

                        super.onStart();
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null || result.length() == 0) {
                            return;
                        }
                    }

                    @Override
                    public void onMyFailure(Throwable arg0) {
                    }
                });
    }

    public void getLocksStatus(final String question) {
        System.out.println("=======NUM大小=========" + num);
        if (num == 0) {
            num = num + 1;
            //这里用一个线程就是异步，
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Lock.getInstance().setLocksStatus(FunctionOperationActivity.this, question);
                }
            }).start();
        }
    }

    /**
     * 连续点五次
     */
    public void fiveClick(int type) {

        switch (type) {
            case 1://密码开门
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                    if (checkedValues == null || "".equals(checkedValues)) {
                        SVProgressHUD.showErrorWithStatus(FunctionOperationActivity.this, "请选择相应要开的柜门！");
                    } else {
                        openPayPasswordDialogs(checkedValues);//临时用
                    }
                }


                break;
        }

    }


}
