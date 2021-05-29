package com.analysis.wisdomtraffic.jpush.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @Author hejunfeng
 * @Date 18:36 2021/3/13 0013
 * @Description com.analysis.wisdomtraffic.jpush.service
 **/
public class SpaceService extends Service {
    private static final int SERVICE_ID = 1; //后台ForegroundService的SERVICE_ID相同

    public SpaceService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("hjf", "创建前台服务替身");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(SERVICE_ID, new Notification());
        stopForeground(true);//移除通知栏消息
        stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("hjf", "销毁前台服务替身");
        super.onDestroy();
    }
}
