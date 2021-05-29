package com.analysis.wisdomtraffic.ui.decicesetting;

import com.analysis.wisdomtraffic.been.base.BaseView;
import com.videogo.openapi.bean.EZDeviceVersion;


/**
 * Created by hejunfeng on 2020/8/3 0003
 */
public interface DeviceView extends BaseView {
    void getVersion(int versionCode, String versionName, String fileName);

    void getDeviceInfo(EZDeviceVersion mDeviceVersion, Boolean b , int errorCode);
}
