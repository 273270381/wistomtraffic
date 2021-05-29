package com.analysis.wisdomtraffic.ui.hydrology.realdata;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.RealData;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.alarm.AlModelImpl;
import com.analysis.wisdomtraffic.ui.alarm.AlarmCallBack;
import com.analysis.wisdomtraffic.ui.hydrology.historydata.HistoryView;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:47 2021/5/24 0024
 * @Description com.analysis.wisdomtraffic.ui.hydrology.realdata
 **/
public class RealDataPresentor extends BasePresenter<RealDataView> {
    private AlModelImpl model;

    public RealDataPresentor(AlModelImpl model) {
        this.model = model;
    }

    public void queryData(Boolean b,int pageSize, PageInfo pageInfo) throws UnsupportedEncodingException {
        if (!isViewAttached()) {
            return;
        }
        model.queryRealData(b,pageSize, pageInfo, new AlarmCallBack() {

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
            public void receiveRealData(Boolean b, List<RealData> dataList) {
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
