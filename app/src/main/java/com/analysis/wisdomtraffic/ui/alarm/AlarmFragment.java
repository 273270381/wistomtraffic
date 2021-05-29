package com.analysis.wisdomtraffic.ui.alarm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.AlarmAdapter;
import com.analysis.wisdomtraffic.adapter.ImageAdapter;
import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.AlarmMessage;
import com.analysis.wisdomtraffic.been.CustomAnimation;
import com.analysis.wisdomtraffic.been.TosMessage;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.baidumap.AlarmDetail;
import com.analysis.wisdomtraffic.utils.DensityUtil;
import com.analysis.wisdomtraffic.utils.ExampleUtil;
import com.analysis.wisdomtraffic.utils.ToastNotRepeat;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Author hejunfeng
 * @Date 16:38 2021/3/11 0011
 * @Description com.analysis.wisdomtraffic.ui.alarm
 **/
public class AlarmFragment extends BaseFragment implements AlarmView{
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 20;
    private BaseQuickAdapter mAdapter;
    private PageInfo pageInfo = new PageInfo();
    private AlarmPresenter presenter;
    private ImageAdapter adapter;
    private List<Map<String ,String>> data = new ArrayList<>();

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void initPresenter() {
        presenter = new AlarmPresenter(new AlModelImpl());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_alarm;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getUrls();
        mAdapter = new AlarmAdapter();
        mAdapter.setAnimationEnable(true);
        adapter = new ImageAdapter(data,getActivity());
        View view = getHeaderView();
        mAdapter.addHeaderView(view);
        mRecyclerView.setAdapter(mAdapter);
        initRefreshLayout();
        initLoadMore();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LatLng latLng = new LatLng(33.9228782653808,118.19874572753906);
                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmDetail.class);
                intent.putExtra("alarmMessage", (Parcelable) mAdapter.getData().get(position));
                startActivity(intent);
            }
        });
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

    private View getHeaderView(){
        View view = getLayoutInflater().inflate(R.layout.head_view, mRecyclerView, false);
        int width = DensityUtil.getDisplayWidth() - DensityUtil.dip2px(getActivity(), 160);
        float height = width / 1.8f;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        Banner banner = view.findViewById(R.id.banner);
        banner.setLayoutParams(lp);
        banner.addBannerLifecycleObserver(this)
                .setAdapter(adapter)
                .setBannerGalleryEffect(60,10)
                .addPageTransformer(new AlphaPageTransformer())
                .setIndicator(new CircleIndicator(getActivity()))
                .setOnBannerListener((data1, position1) -> {
                }).start();
        banner.isAutoLoop(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 进入页面，刷新数据
        mSwipeRefreshLayout.setRefreshing(true);
        try {
            refresh();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initLoadMore() {
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                try {
                    loadMore();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        mAdapter.setAdapterAnimation(new CustomAnimation());
    }

    private void refresh() throws UnsupportedEncodingException {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        // 下拉刷新，需要重置页数
        pageInfo.reset();
        request();
    }

    private void loadMore() throws UnsupportedEncodingException {
        request();
    }

    /**
     * 请求数据
     */
    private void request() throws UnsupportedEncodingException {
        presenter.queryData(PAGE_SIZE,pageInfo);
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    refresh();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TosMessage msg){
        switch (msg.getType()){
            case 0:
                Log.d("hjf","text="+((AlarmMessage)msg.getObject()).getMonitorypoint());
                mAdapter.addData(0,msg.getObject());
                mRecyclerView.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void receiveData(List<AlarmMessage> data) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        if (pageInfo.isFirstPage()){
            mAdapter.setList(data);
        }else{
            mAdapter.addData(data);
        }
        if (data.size() < PAGE_SIZE) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }else{
            mAdapter.getLoadMoreModule().loadMoreComplete();
        }
        pageInfo.nextPage();
    }

    @Override
    public void falied() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        mAdapter.getLoadMoreModule().loadMoreFail();
    }

    @Override
    public void finishRefresh(Boolean b) {

    }

    @Override
    public void finishLoadMore(Boolean b) {

    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
        ExampleUtil.showToast(message, getActivity());
    }
}
