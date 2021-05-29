package com.analysis.wisdomtraffic.ui.alarm;

import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.been.base.BaseView;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:44 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.ui.alarm
 **/
public interface  AlarmView extends BaseView {
    void receiveData(List<AlarmMessage> ls);

    void falied();

    void finishRefresh(Boolean b);

    void finishLoadMore(Boolean b);
}
