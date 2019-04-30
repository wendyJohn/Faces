package com.example.juicekaaa.fireserver.net;

import android.content.Context;
import android.content.Intent;

import com.example.juicekaaa.fireserver.MyApplication;
import com.example.juicekaaa.fireserver.utils.MessageEvent;
import com.example.juicekaaa.fireserver.utils.PreferenceUtils;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * 盘点提交
 */
public class InventorySubmission {
    public static void Submission(final Context context, JSONArray array) {
        JSONObject object = new JSONObject();
        try {
            object.put("data", array);
            RequestParams params = new RequestParams();
            params.put("list", object.toString());
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
