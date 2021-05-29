package com.analysis.wisdomtraffic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;

import com.analysis.wisdomtraffic.been.AppContext;
import com.analysis.wisdomtraffic.been.base.BaseActivity;
import com.analysis.wisdomtraffic.been.db.DBManager;
import com.analysis.wisdomtraffic.jpush.Reciver.MyReceiver;
import com.analysis.wisdomtraffic.ui.baidumap.LocationService;
import com.analysis.wisdomtraffic.ui.main.MainActivity;
import com.analysis.wisdomtraffic.utils.MD5;
import com.analysis.wisdomtraffic.utils.OSCSharedPreference;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import cn.jpush.android.api.JPushInterface;

import static com.analysis.wisdomtraffic.been.AlarmContant.AppKey;

/**
 * Created by hejunfeng on 2020/7/17 0017
 */
public class OSCApplication extends AppContext {
    private static final String CONFIG_READ_STATE_PRE = "CONFIG_READ_STATE_PRE_";
    public static int user_type;
    public LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化操作
        init();
        initSDK();
    }

    private void initSDK() {
        /**
         * sdk日志开关，正式发布需要去掉
         */
        EZOpenSDK.showSDKLog(true);

        /**
         * 设置是否支持P2P取流,详见api
         */
        EZOpenSDK.enableP2P(true);

        /**
         * APP_KEY请替换成自己申请的
         */
        EZOpenSDK.initLib(this, AppKey);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.videogo.action.ADD_DEVICE_SUCCESS_ACTION");
        intentFilter.addAction("com.action.OAUTH_SUCCESS_ACTION_TRAFFIC");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        EzvizBroadcastReceiver receiver = new EzvizBroadcastReceiver();
        registerReceiver(receiver,intentFilter);

        // 初始化 JPush
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);

//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
//        strategy.setAppVersion(AppUtils.getAppVersionName());
//        strategy.setAppPackageName(AppUtils.getAppPackageName());
//        strategy.setAppReportDelay(20000);

        CrashReport.initCrashReport(getApplicationContext(), "98e3781d6f", true);
    }


    private void init() {
        BaseActivity.IS_ACTIVE = true;
        OSCSharedPreference.init(this, "osc_update_sp");
        DBManager.init(this);
        if (TextUtils.isEmpty(OSCSharedPreference.getInstance().getDeviceUUID())) {
            String androidId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
            if (TextUtils.isEmpty(androidId)) {
                androidId = UUID.randomUUID().toString().replaceAll("-", "");
            }
            OSCSharedPreference.getInstance().putDeviceUUID(MD5.get32MD5Str(androidId));
        }
        // 初始化网络请求
        //ApiHttpClient.init(this);
        //初始化百度地图
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        locationService = new LocationService(getApplicationContext());
        if (OSCSharedPreference.getInstance().hasShowUpdate()) {//如果已经更新过
            //如果版本大于更新过的版本，就设置弹出更新
            if (BuildConfig.VERSION_CODE > OSCSharedPreference.getInstance().getUpdateVersion()) {
                OSCSharedPreference.getInstance().putShowUpdate(true);
            }
        }
    }

    public static void setUser_type(int type){
        user_type = type;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }
    private class EzvizBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.i("TAG","action = "+action);
            //if (action.equals(Constant.OAUTH_SUCCESS_ACTION)){
            if (action.equals("com.action.OAUTH_SUCCESS_ACTION_TRAFFIC")){
                Log.i("TAG", "onReceive: OAUTH_SUCCESS_ACTION_TRAFFIC");
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                /*******   获取登录成功之后的EZAccessToken对象   *****/
                context.startActivity(i);
            }
        }
    }
}
