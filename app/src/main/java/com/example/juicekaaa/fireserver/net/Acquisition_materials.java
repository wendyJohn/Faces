package com.example.juicekaaa.fireserver.net;

import android.content.Context;

import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.utils.MessageEvent;
import com.example.juicekaaa.fireserver.utils.PreferenceUtils;
import com.example.juicekaaa.fireserver.utils.PreferencesUtil;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 物资获取
 */
public class Acquisition_materials {
    //物资获取
    public static void addMaterial(final Context context) {
        final ArrayList<ArchitectureBean> a_list = new ArrayList<>();
        final ArrayList<ArchitectureBean> b_list = new ArrayList<>();
        final ArrayList<ArchitectureBean> c_list = new ArrayList<>();
        final ArrayList<ArchitectureBean> d_list = new ArrayList<>();
        final ArrayList<ArchitectureBean> e_list = new ArrayList<>();
        final ArrayList<ArchitectureBean> f_list = new ArrayList<>();
        final ArrayList<ArchitectureBean> g_list = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("mac", MyApplication.getMac());
        params.put("username", "admin");
        params.put("format",  PreferenceUtils.getString(context,"format"));
        params.put("platformkey", "app_firecontrol_owner");
        RequestUtils.ClientPost(URLs.Materials_URL, params,
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
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("获取成功")) {
                                String data = jsonObject.getString("data");
                                JSONArray array = new JSONArray(data);
                                JSONObject object;
                                for (int i = 0; i < array.length(); i++) {
                                    ArchitectureBean bean = new ArchitectureBean();
                                    object = (JSONObject) array.get(i);
                                    String name = object.getString("name");
                                    String storage_location = object.getString("storage_location");
                                    String total = object.getString("total");
                                    String model = object.getString("model");
                                    String specification = object.getString("specification");
                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("A")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            a_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("A")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            a_list.add(bean);
                                        }
                                    }
                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("B")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            b_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("B")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            b_list.add(bean);
                                        }
                                    }

                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("C")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            c_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("C")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            c_list.add(bean);
                                        }
                                    }

                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("D")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            d_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("D")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            d_list.add(bean);
                                        }
                                    }

                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("E")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            e_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("E")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            e_list.add(bean);
                                        }
                                    }

                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("F")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            f_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("F")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            f_list.add(bean);
                                        }
                                    }

                                    if (object.has("lack")) {
                                        String lack = object.getString("lack");
                                        if (storage_location.equals("G")) {
                                            bean.setName(name);
                                            bean.setNumber(total+specification);
                                            bean.setType(model);
                                            bean.setLack(lack);
                                            g_list.add(bean);
                                        }
                                    } else {
                                        if (storage_location.equals("G")) {
                                            bean.setName(name);
                                            bean.setNumber(total);
                                            bean.setType(model);
                                            bean.setLack("0");
                                            g_list.add(bean);
                                        }
                                    }

                                }
                                MessageEvent messageEvent = new MessageEvent(MyApplication.MESSAGE_MATERIAL);
                                messageEvent.setA_list(a_list);
                                messageEvent.setB_list(b_list);
                                messageEvent.setC_list(c_list);
                                messageEvent.setD_list(d_list);
                                messageEvent.setE_list(e_list);
                                messageEvent.setF_list(f_list);
                                messageEvent.setG_list(g_list);
                                EventBus.getDefault().post(messageEvent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onMyFailure(Throwable arg0) {
                    }
                });
    }

}
