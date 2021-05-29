package com.analysis.wisdomtraffic.ui.hydrology.realdata;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.AlarmData;
import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.RealData;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.alarm.AlModelImpl;
import com.analysis.wisdomtraffic.ui.hydrology.historydata.HistoryDataPresentor;
import com.analysis.wisdomtraffic.ui.hydrology.historydata.HistoryView;
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
 * @Date 15:36 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.ui.hydrology.realdata
 **/
public class RealDataFragment extends BaseFragment implements RealDataView {
    private SmartTable<RealData> table;
    private RefreshLayout refreshLayout;
    private RealDataPresentor presentor;
    private PageInfo pageInfo = new PageInfo();
    @Override
    public BasePresenter getPresenter() {
        return presentor;
    }

    @Override
    public void initPresenter() {
        presentor = new RealDataPresentor(new AlModelImpl());
    }

    @Override
    protected void initWidget(View mRoot) {
        table = (SmartTable<RealData>) mRoot.findViewById(R.id.table);
        refreshLayout = (RefreshLayout)mRoot.findViewById(R.id.refreshLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_real;
    }

    @Override
    protected void initData() {
        //设置全局字体大小
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(getActivity(),15));
        FontStyle.setDefaultTextColor(Color.WHITE);
        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {

                return ContextCompat.getColor(getActivity(), R.color.transparent);
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if (("故障").equals(cellInfo.value)){
                    return ContextCompat.getColor(getContext(),R.color.auto_wifi_tip_red);
                }
                RealData data = table.getTableData().getT().get(cellInfo.row);
                if (("故障").equals(data.getWaterlevelAlarm()) && cellInfo.column.getColumnName().equals("上报水位(m)")){
                    return ContextCompat.getColor(getContext(),R.color.auto_wifi_tip_red);
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
    public void receiveData(Boolean b, List<RealData> data) {
        Log.d("hjf","Data.size :"+data.size());
        if (b){
            refreshLayout.finishLoadMore();
        }else{
            refreshLayout.finishRefresh();
        }
        if (pageInfo.isFirstPage()){
            Log.d("hjf","setData");
            table.setData(data);
        }else{
            Log.d("hjf","addData");
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
