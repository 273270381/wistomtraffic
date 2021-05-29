package com.analysis.wisdomtraffic.adapter;

import com.analysis.wisdomtraffic.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.videogo.openapi.bean.EZCameraInfo;
import org.jetbrains.annotations.NotNull;

public class ScanPicAdapter extends BaseQuickAdapter<EZCameraInfo, BaseViewHolder> {

    public ScanPicAdapter() {
        super(R.layout.scan_pic_item);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, EZCameraInfo ezCameraInfo) {
        baseViewHolder.setText(R.id.tv,ezCameraInfo.getCameraName());
    }
}
