package com.analysis.wisdomtraffic.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.analysis.wisdomtraffic.ui.alarm.AlarmFragment;
import com.analysis.wisdomtraffic.ui.filemanage.ScanFileFragment;
import com.analysis.wisdomtraffic.ui.hydrology.HydrologyFragment;
import com.analysis.wisdomtraffic.ui.monitor.MonitorFragment2;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 15:45 2020/12/7 0007
 * @Description com.analysis.dataanalysis.ui.adapter
 **/
public class MyFragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> mFragment;
    private List<String> mTitleList;
    private SparseArray<Fragment> mRegisteredFragments = new SparseArray<Fragment>();

    public MyFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> mFragment, List<String> mTitleList) {
        super(fragmentActivity);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    public Fragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (mFragment != null){
            return mFragment.get(position);
        }else{
            // 首页
            switch (position) {
                case 0:
//                    return new AlarmFragment();
                    return new HydrologyFragment();
                case 1:
                    return new MonitorFragment2();
                case 2:
                    return new ScanFileFragment();
                default:
                    return new HydrologyFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFragment != null ? mFragment.size() :4;
    }
}
