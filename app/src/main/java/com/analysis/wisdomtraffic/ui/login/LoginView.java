package com.analysis.wisdomtraffic.ui.login;


import com.analysis.wisdomtraffic.been.base.BaseView;

/**
 * Created by hejunfeng on 2020/7/20 0020
 */
public interface LoginView extends BaseView {
    /**
     * 获取登陆结果
     */
    void showData(String data);

    void setAccessToken(String s);

    void setUserType(String strUsername);
}
