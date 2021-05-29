package com.analysis.wisdomtraffic.ui.filemanage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.been.base.BaseActivity;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author hejunfeng
 * @Date 14:18 2021/3/23 0023
 * @Description com.analysis.wisdomtraffic.ui.filemanage
 **/
public class FileActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_file;
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    protected void initData() {
        String cameraName = getIntent().getStringExtra("name");
        Bundle bundle = new Bundle();
        bundle.putString("name",cameraName);
        ScanPicFragment picFragment = new ScanPicFragment();
        picFragment.setArguments(bundle);

        ScanVideoFragment videoFragment = new ScanVideoFragment();
        videoFragment.setArguments(bundle);
        fragments.add(picFragment);
        fragments.add(videoFragment);
        titles.add("图片");
        titles.add("视频");
        viewPager.setAdapter(new FragmentPagerAdapter(this.getSupportFragmentManager()) {
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
        viewPager.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}
