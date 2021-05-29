package com.analysis.wisdomtraffic.ui.hydrology.historydata;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.AlarmData;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.alarm.AlModelImpl;
import com.analysis.wisdomtraffic.ui.alarm.AlarmCallBack;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:24 2021/5/24 0024
 * @Description com.analysis.wisdomtraffic.ui.hydrology.historydata
 **/
public class HistoryDataPresentor extends BasePresenter<HistoryView> {
    private AlModelImpl model;

    public HistoryDataPresentor(AlModelImpl model) {
        this.model = model;
    }

    public void queryData(Boolean b,int pageSize, PageInfo pageInfo) throws UnsupportedEncodingException {
        if (!isViewAttached()) {
            return;
        }
        model.queryHistoryData(b,pageSize, pageInfo, new AlarmCallBack() {

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
            public void receiveAlarmData(Boolean b, List<AlarmData> datas) {
                if (getView() != null){
                    getView().receiveData(b,datas);
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
