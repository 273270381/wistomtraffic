package com.analysis.wisdomtraffic.ui.login;

import android.content.Context;

import com.analysis.wisdomtraffic.been.AppOperator;
import com.analysis.wisdomtraffic.been.Auth2User;
import com.analysis.wisdomtraffic.been.User;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.utils.ExampleUtil;
import com.analysis.wisdomtraffic.utils.OSCSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.analysis.wisdomtraffic.been.AlarmContant.AppKey;
import static com.analysis.wisdomtraffic.been.AlarmContant.Secret;

/**
 * Created by hejunfeng on 2020/7/20 0020
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginModelImpl loginModel;

    public LoginPresenter(LoginModelImpl loginModel) {
        this.loginModel = loginModel;
    }

    public void LogIn(Context context, String strUsername, String strPassword, String url){
        if (!isViewAttached()){
            return;
        }

        JSONObject object = new JSONObject();
        try {
            object.put("username",strUsername);
            object.put("password",strPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!ExampleUtil.isConnected(context)){
            getView().showToast("确认网络是否断开");
        }else{
            if (strUsername.trim().equals("")){
                getView().showToast("请您输入用户名");
            }else{
                if (strPassword.trim().equals("")){
                    getView().showToast("请您输入密码");
                }else{
                    getView().showLoading();
                    getdata(object, url);
                }
            }
        }
    }
    private void getdata(JSONObject obj, String url){
        loginModel.login(url, obj, new LoginCallBack() {
            @Override
            public void sendResult(Auth2User user, Integer s) {
                if (s == 200){
                    try {
                        OSCSharedPreference.getInstance().putUserName((String) obj.get("username"));
                        OSCSharedPreference.getInstance().putPassWord((String) obj.get("password"));
                        OSCSharedPreference.getInstance().putToken(user.getToken());
                        OSCSharedPreference.getInstance().putTokenHead(user.getTokenHead());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url = "https://open.ys7.com/api/lapp/token/get";
                    Map<String, String> map = new HashMap<>();
                    map.put("appKey",AppKey);
                    map.put("appSecret",Secret);
                    loginModel.getAccessToken(url, map, new LoginCallBack() {
                        @Override
                        public void sendResult(Auth2User user, Integer s) {

                        }

                        @Override
                        public void sendAccessToken(String s) {
                            getView().setAccessToken(s);
                        }

                        @Override
                        public void onSuccess(Object data) {

                        }

                        @Override
                        public void onFailure(String msg) {
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    getView().hideLoading();
                                    getView().showToast(msg);
                                }
                            });
                        }


                    });
                }else{
                    AppOperator.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().hideLoading();
                            getView().showToast("密码错误");
                        }
                    });
                }
            }

            @Override
            public void sendAccessToken(String s) {

            }

            @Override
            public void onSuccess(Object data) {
                if (isViewAttached()) {
                    getView().hideLoading();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().showToast(msg);
                }
            }
        });
    }
}
