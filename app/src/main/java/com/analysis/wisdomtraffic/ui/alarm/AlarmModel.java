package com.analysis.wisdomtraffic.ui.alarm;

import com.analysis.wisdomtraffic.adapter.PageInfo;

import java.io.UnsupportedEncodingException;

/**
 * @Author hejunfeng
 * @Date 16:47 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.ui.alarm
 **/
public interface AlarmModel {
    void queryData(int pageSize, PageInfo pageInfo,AlarmCallBack callBack) throws UnsupportedEncodingException;

    void queryDeviceAlarmData(Boolean b,int pageSize, PageInfo pageInfo,AlarmCallBack callBack);
}
