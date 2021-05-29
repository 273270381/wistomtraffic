package cn.jiguang.demo;

import android.app.Application;
import android.util.Log;

import java.io.File;


//jpush_import_start
import cn.jpush.android.api.JPushInterface;
//jpush_import_end



/**
 * For developer startup JIGUANG SDK
 * <p>
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class ExampleApplication extends Application {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onCreate() {
        super.onCreate();

        //jpush_init_start
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        //jpush_init_end



    }
}
