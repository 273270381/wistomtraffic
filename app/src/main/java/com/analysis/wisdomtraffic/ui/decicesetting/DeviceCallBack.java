package com.analysis.wisdomtraffic.ui.decicesetting;

import com.analysis.wisdomtraffic.been.base.BaseCallback;
import com.videogo.openapi.bean.EZDeviceVersion;


/**
 * Created by hejunfeng on 2020/8/3 0003
 */
public abstract class DeviceCallBack implements BaseCallback {
    public void getVersionInfo(int code, String versionName, String fineName){}

    public void getDeviceInfo(EZDeviceVersion mDeviceVersion, Boolean b , int errorCode){}
}
