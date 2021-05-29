package com.analysis.wisdomtraffic.ui.alarm;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.been.base.BasePresenter;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:44 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.ui.alarm
 **/
public class AlarmPresenter extends BasePresenter<AlarmView> {
    private AlModelImpl model;
    public AlarmPresenter(AlModelImpl model){
        this.model = model;
    }

    public void queryData(int pageSize, PageInfo pageInfo) throws UnsupportedEncodingException {
        if (!isViewAttached()) {
            return;
        }
        model.queryData(pageSize, pageInfo, new AlarmCallBack() {
            @Override
            public void receiveData(List<AlarmMessage> dataList) {
                if (getView() != null){
                    getView().receiveData(dataList);
                }
            }

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
        });
    }
}
