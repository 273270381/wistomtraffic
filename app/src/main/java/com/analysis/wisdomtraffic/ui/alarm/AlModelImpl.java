package com.analysis.wisdomtraffic.ui.alarm;

import android.app.slice.SliceSpec;
import android.os.Looper;
import android.util.Log;

import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.AlarmContant;
import com.analysis.wisdomtraffic.been.AlarmData;
import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.been.AppOperator;
import com.analysis.wisdomtraffic.been.DeviceAlarmMessage;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.RealData;
import com.analysis.wisdomtraffic.utils.OSCSharedPreference;
import com.analysis.wisdomtraffic.utils.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author hejunfeng
 * @Date 16:46 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.ui.alarm
 **/
public class AlModelImpl implements AlarmModel{
    @Override
    public void queryData(int pageSize, PageInfo pageInfo, AlarmCallBack callBack) throws UnsupportedEncodingException {
        String url = AlarmContant.service_url_wangchan + "highwaymessage/api/getAllMessage";
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("pageSize",String.valueOf(pageSize));
        map.put("pageNum",String.valueOf(pageInfo.getPage()));
        OkHttpUtil.get(url,"", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AppOperator.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("hjf","failure");
                        callBack.onFailure("服务器异常");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                List<AlarmMessage> alarmMessageList = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(responseBody);
                    String result = object.get("code").toString();
                    if (result.equals("200")) {
                        String data = object.get("data").toString();
                        JSONObject objectdata = new JSONObject(data);
                        String total = objectdata.get("total").toString();
                        if (Integer.parseInt(total)>0){
                            Gson gson = new Gson();
                            List<JsonObject> list_objects = gson.fromJson(objectdata.get("list").toString(), new TypeToken<List<JsonObject>>() {
                            }.getType());
                            for (JsonObject object1 : list_objects) {
                                AlarmMessage alarmMessage = gson.fromJson(object1, AlarmMessage.class);
                                alarmMessageList.add(alarmMessage);
                            }
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveData(alarmMessageList);
                                }
                            });
                        }else{
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveData(alarmMessageList);
                                }
                            });
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AppOperator.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onFailure("服务器异常");
                                }
                            });
                        }
                    });
                }
            }
        },map);
    }


    /***
     * @return void
     * @Author hejunfeng
     * @Date 16:24 2021/5/22 0022
     * @Param [pageSize, pageInfo, callBack]
     * @Description 告警信息查询
     **/
    @Override
    public void queryDeviceAlarmData(Boolean b,int pageSize, PageInfo pageInfo, AlarmCallBack callBack) {
        String url = AlarmContant.service_url_1+"api/alarm/list";
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("pageSize",String.valueOf(pageSize));
        map.put("pageNum",String.valueOf(pageInfo.getPage()));
        OkHttpUtil.post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!b){
                    callBack.finishRefresh(false);
                }else{
                    callBack.finishLoadMore(false);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                List<DeviceAlarmMessage> msgList = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(responseBody);
                    String result = object.get("code").toString();
                    if (result.equals("200")) {
                        String data = object.get("data").toString();
                        JSONObject objectdata = new JSONObject(data);
                        String total = objectdata.get("total").toString();
                        if (Integer.parseInt(total)>0){
                            Gson gson = new Gson();
                            List<JsonObject> list_objects = gson.fromJson(objectdata.get("list").toString(), new TypeToken<List<JsonObject>>() {}.getType());
                            for (JsonObject object1 : list_objects) {
                                DeviceAlarmMessage alarmMessage = gson.fromJson(object1, DeviceAlarmMessage.class);
                                msgList.add(alarmMessage);
                            }
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveDeviceAlarmData(b,msgList);
                                }
                            });
                        }else{
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveDeviceAlarmData(b,msgList);
                                }
                            });
                        }
                    }else{
                        callBack.onFailure("服务器异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AppOperator.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onFailure("服务器异常");
                                }
                            });
                        }
                    });
                }
            }
        },map);
    }

    public void queryHistoryData(Boolean b,int pageSize, PageInfo pageInfo, AlarmCallBack callBack){
        String url = AlarmContant.service_url_1+"api/flood/query/alllever-alarm";
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("pageSize",String.valueOf(pageSize));
        map.put("pageNum",String.valueOf(pageInfo.getPage()));
        OkHttpUtil.post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!b){
                    callBack.finishRefresh(false);
                }else{
                    callBack.finishLoadMore(false);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                List<AlarmData> msgList = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(responseBody);
                    String result = object.get("code").toString();
                    if (result.equals("200")) {
                        String data = object.get("data").toString();
                        JSONObject objectdata = new JSONObject(data);
                        String total = objectdata.get("total").toString();
                        if (Integer.parseInt(total)>0){
                            Gson gson = new Gson();
                            List<JsonObject> list_objects = gson.fromJson(objectdata.get("list").toString(), new TypeToken<List<JsonObject>>() {}.getType());
                            for (JsonObject object1 : list_objects) {
                                AlarmData alarmData = gson.fromJson(object1, AlarmData.class);
                                msgList.add(alarmData);
                            }
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveAlarmData(b,msgList);
                                }
                            });
                        }else{
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveAlarmData(b,msgList);
                                }
                            });
                        }
                    }else{
                        callBack.onFailure("服务器异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AppOperator.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onFailure("服务器异常");
                                }
                            });
                        }
                    });
                }
            }
        },map);
    }

    public void queryRealData(Boolean b,int pageSize, PageInfo pageInfo, AlarmCallBack callBack){
        String url = AlarmContant.service_url_1+"api/sensordata/real/dataList";
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("devType","2g");
        try {
            OkHttpUtil.get(url,"", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (!b){
                        callBack.finishRefresh(false);
                    }else{
                        callBack.finishLoadMore(false);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseBody = response.body().string();
                    List<RealData> msgList = new ArrayList<>();
                    try {
                        JSONObject object = new JSONObject(responseBody);
                        String result = object.get("code").toString();
                        if (result.equals("200")) {
                            String data = object.get("data").toString();
                            Gson gson = new Gson();
                            List<JsonObject> list_objects = gson.fromJson(data, new TypeToken<List<JsonObject>>() {}.getType());
                            for (JsonObject object1 : list_objects) {
                                RealData realData = gson.fromJson(object1, RealData.class);
                                msgList.add(realData);
                            }
                            AppOperator.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSuccess(0);
                                    callBack.receiveRealData(b,msgList);
                                }
                            });
                        }else{
                            callBack.onFailure("服务器异常");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AppOperator.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                AppOperator.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onFailure("服务器异常");
                                    }
                                });
                            }
                        });
                    }
                }
            },map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
