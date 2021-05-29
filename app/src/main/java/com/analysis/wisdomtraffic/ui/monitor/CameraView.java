package com.analysis.wisdomtraffic.ui.monitor;

import com.analysis.wisdomtraffic.been.base.BaseView;
import com.analysis.wisdomtraffic.utils.TcpClient;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.List;


/**
 * Created by hejunfeng on 2020/7/25 0025
 */
public interface CameraView extends BaseView {

    void getCameraList(List<EZDeviceInfo> result);

    void setRefreshPic();

    void onError(int errorCode);

    void sendTcp(TcpClient tcpClient, String no, String text);

    void tcpResult(Boolean b);
}
