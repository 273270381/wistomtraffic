package com.analysis.wisdomtraffic.ui.hydrology.historydata;

import com.analysis.wisdomtraffic.been.AlarmData;
import com.analysis.wisdomtraffic.been.DeviceAlarmMessage;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.base.BaseView;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:02 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm
 **/
public interface HistoryView extends BaseView {
    void receiveData(Boolean b,List<AlarmData> ls);

    void falied();

    void tableRefrash(Boolean b);

    void tableLoadMore(Boolean b);
}
