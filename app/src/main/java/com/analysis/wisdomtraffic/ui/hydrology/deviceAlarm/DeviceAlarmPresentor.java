package com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.DeviceAlarmMessage;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.alarm.AlModelImpl;
import com.analysis.wisdomtraffic.ui.alarm.AlarmCallBack;
import com.analysis.wisdomtraffic.ui.alarm.AlarmModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author hejunfeng
 * @Date 15:58 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm
 **/
public class DeviceAlarmPresentor extends BasePresenter<DeviceAlarmView> {
    private AlModelImpl model;

    public DeviceAlarmPresentor(AlModelImpl model) {
        this.model = model;
    }

    public void queryData(Boolean b,int pageSize, PageInfo pageInfo) throws UnsupportedEncodingException {
        if (!isViewAttached()) {
            return;
        }
        model.queryDeviceAlarmData(b,pageSize, pageInfo, new AlarmCallBack() {

            @Override
            public void onSuccess(Object data) {
                if (getView() != null){
                    getView().hideLoading();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (getView() != null){
                    getView().falied();
                    getView().hideLoading();
                    getView().showToast(msg);
                }
            }

            @Override
            public void receiveDeviceAlarmData(Boolean b,List<DeviceAlarmMessage> dataList) {
                if (getView() != null){
                    getView().receiveData(b,dataList);
                }
            }

            @Override
            public void finishRefresh(Boolean b) {
                getView().tableRefrash(b);
            }

            @Override
            public void finishLoadMore(Boolean b) {
                getView().tableLoadMore(b);
            }
        });
    }
}
