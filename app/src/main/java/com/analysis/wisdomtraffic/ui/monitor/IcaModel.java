package com.analysis.wisdomtraffic.ui.monitor;

import android.content.Context;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.videogo.openapi.bean.EZCameraInfo;

import java.util.List;
import java.util.Map;


/**
 * Created by hejunfeng on 2020/7/25 0025
 */
public interface IcaModel {
    void getCamersInfoList(Context context, int pagesize, PageInfo pageInfo, CaCallBack caCallBack);

    void refresPic(List<Map<String,Object>> cameraInfoList , CaCallBack caCallBack);

    void getTcpresult(String data, CaCallBack callBack);

}
