package com.analysis.wisdomtraffic.ui.monitor;

import com.analysis.wisdomtraffic.been.base.BaseCallback;
import com.analysis.wisdomtraffic.utils.TcpClient;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import java.util.List;
/**
 * Created by hejunfeng on 2020/7/25 0025
 */
public abstract class CaCallBack implements BaseCallback {
    public void getCameraListInfo(List<EZDeviceInfo> result){}

    public void sendErrorCode(int i){}

    public void refresPic(){}

    public void showLoading(TcpClient tcpClient, String no, String tx){}

    public void hideLoading(){}

    public void tcpResult(boolean b){}
}
