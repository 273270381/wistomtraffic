package com.analysis.wisdomtraffic.ui.login;

import android.util.Log;

import com.analysis.wisdomtraffic.been.AppOperator;
import com.analysis.wisdomtraffic.been.Auth2User;
import com.analysis.wisdomtraffic.been.User;
import com.analysis.wisdomtraffic.utils.OkHttpUtil;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hejunfeng on 2020/7/20 0020
 */
public class LoginModelImpl implements ILoginModel {

    @Override
    public void login(String url, JSONObject m , LoginCallBack callback) {
        OkHttpUtil.post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AppOperator.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("hjf",e.toString());
                        callback.onFailure("网络异常");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseBody);
                    Integer code = (Integer) object.get("code");
                    String data = object.get("data").toString();
                    Gson gson = new Gson();
                    Auth2User user = gson.fromJson(data,Auth2User.class);
                    callback.sendResult(user, code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(0);
            }
        },m);
    }

    @Override
    public void getAccessToken(String url, Map m, LoginCallBack callback) {
        OkHttpUtil.post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseBody);
                    String data = object.get("data").toString();
                    JSONObject obj = new JSONObject(data);
                    String accessToken = obj.get("accessToken").toString();
                    callback.sendAccessToken(accessToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },m);
    }
}
