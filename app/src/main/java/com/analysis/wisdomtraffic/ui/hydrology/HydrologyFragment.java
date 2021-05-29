package com.analysis.wisdomtraffic.ui.hydrology;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.ImageAdapter;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm.DeviceAlarmFragment;
import com.analysis.wisdomtraffic.ui.hydrology.historydata.HistoryDataFragment;
import com.analysis.wisdomtraffic.ui.hydrology.realdata.RealDataFragment;
import com.analysis.wisdomtraffic.utils.DensityUtil;
import com.analysis.wisdomtraffic.view.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Author hejunfeng
 * @Date 15:26 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.ui.hydrology
 **/
public class HydrologyFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    NoScrollViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    private ImageAdapter adapter;
    private List<Map<String ,String>> data = new ArrayList<>();


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hydrology;
    }

    @Override
    protected void initWidget(View mRoot) {
        viewPager = mRoot.findViewById(R.id.viewPager);
        viewPager.setNoScroll(true);
        getUrls();
        adapter = new ImageAdapter(data,getActivity());

        int width = DensityUtil.getDisplayWidth() - DensityUtil.dip2px(getActivity(), 160);
        float height = width / 1.8f;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        Banner banner = mRoot.findViewById(R.id.banner);
        banner.setLayoutParams(lp);
        banner.addBannerLifecycleObserver(this)
                .setAdapter(adapter)
                .setBannerGalleryEffect(60,10)
                .addPageTransformer(new AlphaPageTransformer())
                .setIndicator(new CircleIndicator(getActivity()))
                .setOnBannerListener((data1, position1) -> {
                }).start();
        banner.isAutoLoop(true);

    }

    @Override
    protected void initData() {
        DeviceAlarmFragment deviceAlarmFragment = new DeviceAlarmFragment();
        HistoryDataFragment historyDataFragment = new HistoryDataFragment();
        RealDataFragment realDataFragment = new RealDataFragment();
        fragments.add(deviceAlarmFragment);
        fragments.add(historyDataFragment);
        fragments.add(realDataFragment);
        titles.add("设备报警");
        titles.add("泄洪报警");
        titles.add("实时数据");
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    private void getUrls() {
        try {
            String[] urls = getActivity().getAssets().list("image");
            for (int i = 0 ; i < urls.length ; i++){
                Map<String,String> map = new LinkedHashMap<>();
                String path = "file:///android_asset/image/"+urls[i];
                map.put("url",path);
                if (path.contains("a1")){
                    map.put("title","三台山梨园");
                }else if (path.contains("a2")){
                    map.put("title","项王故里");
                } else if (path.contains("a3")){
                    map.put("title","古黄河公园双塔");
                } else if (path.contains("a4")){
                    map.put("title","洪泽湖湿地");
                } else if (path.contains("a5")){
                    map.put("title","衲田");
                } else if (path.contains("a6")){
                    map.put("title","三台山");
                }
                data.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
