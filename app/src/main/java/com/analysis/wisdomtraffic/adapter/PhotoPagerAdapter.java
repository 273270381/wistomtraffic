package com.analysis.wisdomtraffic.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.analysis.wisdomtraffic.ui.filemanage.PhotoFragment;

import java.util.ArrayList;


/**
 * Created by zheng on 2017/11/27.
 */

public class PhotoPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<String> urlList;

    public PhotoPagerAdapter(FragmentManager fm, ArrayList<String> urlList) {
        super(fm);
        this.urlList=urlList;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(urlList.get(position));
    }

    @Override
    public int getCount() {
        return urlList.size();
    }
}
