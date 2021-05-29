package com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm;

import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.been.DeviceAlarmMessage;
import com.analysis.wisdomtraffic.been.base.BaseView;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:02 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm
 **/
public interface DeviceAlarmView extends BaseView {
    void receiveData(Boolean b,List<DeviceAlarmMessage> ls);

    void falied();

    void tableRefrash(Boolean b);

    void tableLoadMore(Boolean b);
}
