package com.analysis.wisdomtraffic.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager2.widget.ViewPager2;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.MyFragmentPagerAdapter;
import com.analysis.wisdomtraffic.been.base.BaseActivity;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.jpush.TagAliasOperatorHelper;
import com.analysis.wisdomtraffic.jpush.service.ForeGroundService;
import com.analysis.wisdomtraffic.utils.OSCSharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    ViewPager2 vpContent;
    @BindView(R.id.bnv_menu)
    BottomNavigationView bnvMenu;
    public static int sequence = 1;
    private Intent intent;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initPresenter() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

        intent = new Intent(this, ForeGroundService.class);
        startService(intent);
        //设置别名
        String userid = OSCSharedPreference.getInstance().getUserId();
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,userid);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(2);
        vpContent.setUserInputEnabled(false);
        vpContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bnvMenu.getMenu().getItem(position).setChecked(true);
            }
        });
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bnv_home:
                        vpContent.setCurrentItem(0);
                        return true;
                    case R.id.bnv_discovery:
                        vpContent.setCurrentItem(1);
                        return true;
                    case R.id.m_file:
                        vpContent.setCurrentItem(2);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        Log.d("hjf","isIgnoringBatteryOptimizations : "+isIgnoringBatteryOptimizations());
        if (!isIgnoringBatteryOptimizations()){
            requestIgnoreBatteryOptimizations();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void showToast(String message) {

    }
}