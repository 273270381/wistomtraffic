package com.analysis.wisdomtraffic.ui.login;


import com.analysis.wisdomtraffic.been.Auth2User;
import com.analysis.wisdomtraffic.been.User;
import com.analysis.wisdomtraffic.been.base.BaseCallback;

/**
 * Created by hejunfeng on 2020/7/20 0020
 */
public interface LoginCallBack<T> extends BaseCallback {

    void sendResult(Auth2User user , Integer s);

    void sendAccessToken(String s);
}
