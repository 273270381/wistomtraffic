package com.analysis.wisdomtraffic.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.utils.DataUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hejunfeng
 * @Date 14:25 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.adapter
 **/
public class AlarmAdapter extends BaseQuickAdapter<AlarmMessage, BaseViewHolder> implements LoadMoreModule{


    public AlarmAdapter() {
        super(R.layout.alarm_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AlarmMessage alarmMessage) {
        baseViewHolder.setText(R.id.tv_tag_name,alarmMessage.getEventtype());
        baseViewHolder.setText(R.id.tv_title,alarmMessage.getMonitorypoint());
        if (alarmMessage.getStarttime()!=null){
            String time = DataUtils.timeStamp2Date(DataUtils.timeStamp2Long(alarmMessage.getStarttime()),"yyyy-MM-dd HH:mm");
            baseViewHolder.setText(R.id.tv_time,time);
        }
        if (alarmMessage.getFinishState() != null && alarmMessage.getFinishState() == 1){
            //baseViewHolder.findView(R.id.im_statue).setVisibility(View.VISIBLE);
            baseViewHolder.findView(R.id.im_statue).setBackgroundResource(R.mipmap.completed);
            baseViewHolder.findView(R.id.iv_new).setVisibility(View.GONE);
        }else{
            //baseViewHolder.findView(R.id.im_statue).setVisibility(View.GONE);
            if (alarmMessage.getHandleState() != null && alarmMessage.getHandleState() == 1){
                baseViewHolder.findView(R.id.im_statue).setBackgroundResource(R.mipmap.yichuzhi);
//                baseViewHolder.setText(R.id.iv_new,"已处置");
//                baseViewHolder.setTextColor(R.id.iv_new,Color.BLUE);
            }else if (alarmMessage.getOrderState() != null && alarmMessage.getOrderState() == 1){
                baseViewHolder.findView(R.id.im_statue).setBackgroundResource(R.mipmap.yijiejing);
//                baseViewHolder.setText(R.id.iv_new,"已接单");
//                baseViewHolder.setTextColor(R.id.iv_new,Color.YELLOW);
            }else{
                baseViewHolder.findView(R.id.im_statue).setBackgroundResource(R.mipmap.yibaojing);
//                baseViewHolder.setTextColor(R.id.iv_new,Color.RED);
//                baseViewHolder.setText(R.id.iv_new,"未接单");
            }
        }
    }

}
