package com.analysis.wisdomtraffic.ui.alarm;

import com.analysis.wisdomtraffic.been.AlarmData;
import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.been.DeviceAlarmMessage;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.RealData;
import com.analysis.wisdomtraffic.been.base.BaseCallback;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:48 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.ui.alarm
 **/
public abstract class AlarmCallBack implements BaseCallback {
    public void receiveData(List<AlarmMessage> dataList){};

    public void receiveDeviceAlarmData(Boolean b,List<DeviceAlarmMessage> dataList){};

    public void receiveHistoryData(Boolean b,List<HistoryData> dataList){};

    public void receiveAlarmData(Boolean b, List<AlarmData> datas){};

    public void receiveRealData(Boolean b,List<RealData> dataList){};

    public void finishRefresh(Boolean b){};

    public void finishLoadMore(Boolean b){};
}
