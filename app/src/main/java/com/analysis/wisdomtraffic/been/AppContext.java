package com.analysis.wisdomtraffic.been;

import android.content.Context;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.been.base.BaseApplication;
import com.analysis.wisdomtraffic.been.db.DBManager;
import com.analysis.wisdomtraffic.utils.DataCleanManager;
import com.analysis.wisdomtraffic.utils.MethodsCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Properties;

import static com.analysis.wisdomtraffic.been.AppConfig.KEY_LOAD_IMAGE;


/**
 * 全局应用程序类
 * 用于保存和调用全局应用配置及访问网络数据
 */
public class AppContext extends BaseApplication {
    public static final int PAGE_SIZE = 40;// 默认分页大小
    private static AppContext instance;
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.bg_color, R.color.common_bg);//全局设置主题颜色
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter footer = new ClassicsFooter(context).setDrawableSize(20);
                footer.setAccentColorId(R.color.common_bg);
                return footer;
            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBManager.init(this);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return AppContext
     */
    public static AppContext getInstance() {
        return instance;
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        //DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }

        /*
        Run.onUiSync(new Action() {
            @Override
            public void call() {
                // Glide 清理内存必须在主线程
                Glide.get(OSCApplication.getInstance()).clearMemory();
            }
        });

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                // Glide 清理磁盘必须在子线程
                Glide.get(OSCApplication.getInstance()).clearDiskCache();
            }
        });
        */

        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
    }

    public static void setLoadImage(boolean flag) {
        set(KEY_LOAD_IMAGE, flag);
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}