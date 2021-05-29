package com.analysis.wisdomtraffic.ui.hydrology.realdata;

import com.analysis.wisdomtraffic.been.HistoryData;
import com.analysis.wisdomtraffic.been.RealData;
import com.analysis.wisdomtraffic.been.base.BaseView;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 16:48 2021/5/24 0024
 * @Description com.analysis.wisdomtraffic.ui.hydrology.realdata
 **/
public interface RealDataView extends BaseView {
    void receiveData(Boolean b, List<RealData> ls);

    void falied();

    void tableRefrash(Boolean b);

    void tableLoadMore(Boolean b);
}
