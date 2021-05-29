package com.analysis.wisdomtraffic.ui.monitor;

import android.content.Context;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.List;
import java.util.Map;


/**
 * Created by hejunfeng on 2020/7/25 0025
 */
public class CameraPresenter extends BasePresenter<CameraView> {
    private CameraModelImpl model;

    public CameraPresenter(CameraModelImpl model) {
        this.model = model;
    }


    //BRVAH
    public void getCamersInfoListTask(Context context, int pageSize, PageInfo pageInfo){
        if (!isViewAttached()){
            return;
        }
        //getView().showLoading();
        model.getCamersInfoList(context, pageSize, pageInfo, new CaCallBack() {

            @Override
            public void getCameraListInfo(List<EZDeviceInfo> result) {
                if (getView() != null){
                    getView().getCameraList(result);
                }
            }

            @Override
            public void sendErrorCode(int i) {
                if (getView() != null){
                    getView().onError(i);
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
                    getView().hideLoading();
                    getView().showToast(msg);
                }
            }
        });
    }


    public void refreshPic(List<Map<String,Object>> dataList){
        if (!isViewAttached()){
            return;
        }
        model.refresPic(dataList, new CaCallBack() {

            @Override
            public void refresPic() {
                if (getView() != null){
                    getView().setRefreshPic();
                }
            }

            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

//    public void sendTcp(String et_send, List<String> arrayList, List<LoBody> loBodyList){
//        if (!isViewAttached()){
//            return;
//        }
//        model.sendTcp(et_send, arrayList, loBodyList, new CaCallBack() {
//
//            @Override
//            public void showLoading(TcpClient tcpClient, String no, String text) {
//                if (getView() != null){
//                    getView().showLoading();
//                    getView().sendTcp(tcpClient,no,text);
//                }
//            }
//
//            @Override
//            public void hideLoading() {
//                if (getView() != null){
//                    getView().hideLoading();
//                }
//            }
//
//            @Override
//            public void onSuccess(Object data) {
//
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                if (getView() != null){
//                    getView().showToast(msg);
//                }
//            }
//        });
//    }

    public void getTcpresult(String data){
        if (!isViewAttached()){
            return;
        }
        model.getTcpresult(data, new CaCallBack() {

            @Override
            public void tcpResult(boolean b) {
                if (getView() != null){
                    getView().tcpResult(b);
                }
            }

            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFailure(String msg) {
                if (getView() != null){
                    getView().showToast(msg);
                }
            }

            @Override
            public void hideLoading() {
                if (getView() != null){
                    getView().hideLoading();
                }
            }
        });
    }
}
