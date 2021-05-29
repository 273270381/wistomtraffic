package com.analysis.wisdomtraffic.ui.hydrology.historydata;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.AlarmData;
import com.analysis.wisdomtraffic.been.DeviceAlarmMessage;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.alarm.AlModelImpl;
import com.analysis.wisdomtraffic.ui.hydrology.deviceAlarm.DeviceAlarmPresentor;
import com.analysis.wisdomtraffic.utils.DataUtils;
import com.analysis.wisdomtraffic.utils.DateUtils;
import com.analysis.wisdomtraffic.utils.ExampleUtil;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import static com.analysis.wisdomtraffic.been.AppContext.PAGE_SIZE;

/**
 * @Author hejunfeng
 * @Date 15:37 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.ui.hydrology.historydata
 **/
public class HistoryDataFragment extends BaseFragment implements HistoryView{
    private SmartTable<AlarmData> table;
    private RefreshLayout refreshLayout;
    private HistoryDataPresentor presentor;
    private PageInfo pageInfo = new PageInfo();
    @Override
    public BasePresenter getPresenter() {
        return presentor;
    }

    @Override
    public void initPresenter() {
        presentor = new HistoryDataPresentor(new AlModelImpl());
    }

    @Override
    protected void initWidget(View mRoot) {
        table = (SmartTable<AlarmData>) mRoot.findViewById(R.id.table);
        refreshLayout = (RefreshLayout)mRoot.findViewById(R.id.refreshLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_history;
    }

    @Override
    protected void initData() {
        //设置全局字体大小
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(getActivity(),17));
        FontStyle.setDefaultTextColor(Color.WHITE);

        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {

                return ContextCompat.getColor(getActivity(), R.color.transparent);
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if (cellInfo.column.getColumnName().equals("风速预警")||
                        cellInfo.column.getColumnName().equals("雨量预警")||
                        cellInfo.column.getColumnName().equals("水位预警")||
                        cellInfo.column.getColumnName().equals("水量预警")){
                    if (("橙色预警").equals(cellInfo.value)){
                        return ContextCompat.getColor(getContext(),R.color.a1_orange_color);
                    }else if(("黄色预警").equals(cellInfo.value)){
                        return ContextCompat.getColor(getContext(),R.color.possible_result_points);
                    }else if(("红色预警").equals(cellInfo.value)){
                        return ContextCompat.getColor(getContext(),R.color.upgrade_red);
                    }
                }

                if (cellInfo.column.getColumnName().equals("时间")){
                    cellInfo.column.setFormat(new IFormat() {
                        @Override
                        public String format(Object o) {
                            String s = DateUtils.parseTimeZ((Date)o);
                            return s;
                        }
                    });
                }
                AlarmData data = table.getTableData().getT().get(cellInfo.row);
                if (("橙色预警").equals(data.getWindAlarmLever()) && cellInfo.column.getColumnName().equals("风速(m/s)")){
                    return ContextCompat.getColor(getContext(),R.color.a1_orange_color);
                }
                if (("橙色预警").equals(data.getWaterAlarmLever()) && cellInfo.column.getColumnName().equals("水位(m)")){
                    return ContextCompat.getColor(getContext(),R.color.a1_orange_color);
                }
                if (("橙色预警").equals(data.getRainAlarmLever()) && cellInfo.column.getColumnName().equals("雨量(mm)")){
                    return ContextCompat.getColor(getContext(),R.color.a1_orange_color);
                }
                if (("橙色预警").equals(data.getVolumeAlarmlever()) && cellInfo.column.getColumnName().equals("水量(m3)")){
                    return ContextCompat.getColor(getContext(),R.color.a1_orange_color);
                }


                if (("黄色预警").equals(data.getWindAlarmLever()) && cellInfo.column.getColumnName().equals("风速(m/s)")){
                    return ContextCompat.getColor(getContext(),R.color.possible_result_points);
                }
                if (("黄色预警").equals(data.getWaterAlarmLever()) && cellInfo.column.getColumnName().equals("水位(m)")){
                    return ContextCompat.getColor(getContext(),R.color.possible_result_points);
                }
                if (("黄色预警").equals(data.getRainAlarmLever()) && cellInfo.column.getColumnName().equals("雨量(mm)")){
                    return ContextCompat.getColor(getContext(),R.color.possible_result_points);
                }
                if (("黄色预警").equals(data.getVolumeAlarmlever()) && cellInfo.column.getColumnName().equals("水量(m3)")){
                    return ContextCompat.getColor(getContext(),R.color.possible_result_points);
                }


                if (("红色预警").equals(data.getWindAlarmLever()) && cellInfo.column.getColumnName().equals("风速(m/s)")){
                    return ContextCompat.getColor(getContext(),R.color.upgrade_red);
                }
                if (("红色预警").equals(data.getWaterAlarmLever()) && cellInfo.column.getColumnName().equals("水位(m)")){
                    return ContextCompat.getColor(getContext(),R.color.upgrade_red);
                }
                if (("红色预警").equals(data.getRainAlarmLever()) && cellInfo.column.getColumnName().equals("雨量(mm)")){
                    return ContextCompat.getColor(getContext(),R.color.upgrade_red);
                }
                if (("红色预警").equals(data.getVolumeAlarmlever()) && cellInfo.column.getColumnName().equals("水量(m3)")){
                    return ContextCompat.getColor(getContext(),R.color.upgrade_red);
                }


                return super.getTextColor(cellInfo);
            }
        });

        table.getConfig().setShowTableTitle(false);
        table.getConfig().setVerticalPadding(20);
        table.getConfig().setHorizontalPadding(50);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(true);
        table.getConfig().setFixedYSequence(true);
        initRefreshLayout();
        initLoadMore();
        getData(false);
    }

    private void initLoadMore() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(true);
            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
    }

    /**
     * 请求数据
     */
    private void getData(Boolean b){
        try {
            presentor.queryData(b,PAGE_SIZE,pageInfo);
            if (!b){
                pageInfo.reset();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveData(Boolean b, List<AlarmData> data) {
        if (b){
            refreshLayout.finishLoadMore();
        }else{
            refreshLayout.finishRefresh();
        }
        if (pageInfo.isFirstPage()){
            table.setData(data);
        }else{
            table.addData(data,b);
        }
        pageInfo.nextPage();
    }

    @Override
    public void falied() {

    }

    @Override
    public void tableRefrash(Boolean b) {
        refreshLayout.finishRefresh(b);
    }

    @Override
    public void tableLoadMore(Boolean b) {
        refreshLayout.finishLoadMore(b);
    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
        ExampleUtil.showToast(message, getActivity());
    }
}
