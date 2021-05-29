package com.analysis.wisdomtraffic.jpush.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author hejunfeng
 * @Date 18:35 2021/3/13 0013
 * @Description com.analysis.wisdomtraffic.jpush.service
 **/
public class ForeGroundService extends Service {
    private static final int SERVICE_ID = 1;
    private Timer timer;
    private TimerTask timerTask;
    public static int count;

    public ForeGroundService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("hjf", "创建前台服务");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTask();
        if (Build.VERSION.SDK_INT < 18) {//Android4.3以下版本
            //直接调用startForeground即可,不会在通知栏创建通知
            Log.d("hjf", "sdk<18");
            startForeground(SERVICE_ID, new Notification());

        } else if (Build.VERSION.SDK_INT < 24) {//Android4.3 - 7.0之间
            Log.d("hjf", "sdk<24");
            Intent intent1 = new Intent(this,SpaceService.class);
            startService(intent1);
        } else {//Android 8.0以上
            Log.d("hjf", "sdk>24");
            //经测试，本人的一加6T（Android 10）这样写并不会在通知栏创建通知，其他机型与版本效果仍需考证
            startForeground(SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    /**
     * 开启定时任务，count每秒+1
     */
    private void startTask() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //Log.d("hjf", "服务运行中，count: " + count);
                count++;
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }
    /**
     * 结束定时任务
     */
    private void stopTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        count = 0;
    }

    @Override
    public void onDestroy() {
        stopTask();
        Log.d("hjf", "销毁前台服务");
        super.onDestroy();
    }
}
