package com.analysis.wisdomtraffic.ui.login;

import org.json.JSONObject;

import java.util.Map;

public interface ILoginModel<T> {

    void login(String s, JSONObject m, LoginCallBack<T> callback);

    void getAccessToken(String s, Map m, LoginCallBack<T> callback);
}
