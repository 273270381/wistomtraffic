package com.analysis.wisdomtraffic.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.been.AppOperator;
import com.baidu.mapapi.map.MapView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.videogo.openapi.bean.EZCameraInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @Author hejunfeng
 * @Date 21:01 2021/3/23 0023
 * @Description com.analysis.wisdomtraffic.adapter
 **/
public class DeviceListAdapter extends BaseQuickAdapter<Map<String,Object>, BaseViewHolder> implements LoadMoreModule, OnItemChildClickListener {

    public DeviceListAdapter() {
        super(R.layout.cameralist_small_item);
        addChildClickViewIds(R.id.item_play_btn,R.id.tab_remoteplayback_btn,R.id.tab_setdevice_btn);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, Map<String,Object> map) {
        if (map.get("camera") != null){
            viewHolder.setText(R.id.camera_name_tv,((EZCameraInfo)map.get("camera")).getCameraName());
            viewHolder.setVisible(R.id.item_icon, true);
            if (!TextUtils.isEmpty(((EZCameraInfo)map.get("camera")).getCameraCover())){
                Glide.with(viewHolder.itemView.getContext()).load(((EZCameraInfo)map.get("camera")).getCameraCover()).placeholder(R.drawable.device_other).into((ImageView) viewHolder.getView(R.id.item_icon));
            }
            if (((EZCameraInfo)map.get("camera")).getIsShared() != 0 && ((EZCameraInfo)map.get("camera")).getIsShared() != 1) {
                viewHolder.setVisible(R.id.tab_alarmlist_btn,false);
                viewHolder.setVisible(R.id.tab_setdevice_btn,false);
            }else{
                viewHolder.setVisible(R.id.tab_alarmlist_btn,true);
                viewHolder.setVisible(R.id.tab_setdevice_btn,true);
            }
        }
    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

    }
}
