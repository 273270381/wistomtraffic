package com.analysis.wisdomtraffic.ui.monitor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.DeviceListAdapter;
import com.analysis.wisdomtraffic.adapter.PageInfo;
import com.analysis.wisdomtraffic.been.CustomAnimation;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.decicesetting.EZDeviceSettingActivity;
import com.analysis.wisdomtraffic.ui.playback.PlayBackListActivity;
import com.analysis.wisdomtraffic.ui.playback.RemoteListContant;
import com.analysis.wisdomtraffic.ui.realpaly.EZRealPlayActivity;
import com.analysis.wisdomtraffic.utils.ActivityUtils;
import com.analysis.wisdomtraffic.utils.TcpClient;
import com.analysis.wisdomtraffic.utils.ToastNotRepeat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.DateTimeUtil;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author hejunfeng
 * @Date 16:38 2021/3/11 0011
 * @Description com.analysis.wisdomtraffic.ui.monitor
 **/
public class MonitorFragment2 extends BaseFragment implements View.OnClickListener, CameraView{
    @BindView(R.id.no_camera_tip_ly)
    LinearLayout mNoCameraTipLy;
    @BindView(R.id.get_camera_fail_tip_ly)
    LinearLayout mGetCameraFailTipLy;
    @BindView(R.id.text_my)
    TextView mMyDevice;
    @BindView(R.id.btn_add)
    Button mAddBtn;
    @BindView(R.id.btn_user)
    Button mUserBtn;
    @BindView(R.id.get_camera_list_fail_tv)
    TextView mCameraFailTipTv;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View mNoMoreView;
    private BroadcastReceiver mReceiver = null;
    private DeviceListAdapter mAdapter = null;
    private CameraPresenter cameraPresenter;
    private PageInfo pageInfo = new PageInfo();
    private static final int PAGE_SIZE = 3;
    private String TAG = "EZCameraListActivity";
    private List<EZDeviceInfo> deviceInfoList = new ArrayList<>();
    private List<String> arrayList = new ArrayList<>();
    private List<EZCameraInfo> cameraInfoList = new ArrayList<>();
    private TcpClient.OnDataReceiveListener dataReceiveListener;
    private boolean bIsFromSetting = false;
    private TcpClient tcpClient;
    public final static int TAG_CLICK_PLAY = 1;
    public final static int TAG_CLICK_REMOTE_PLAY_BACK = 2;
    public final static int TAG_CLICK_SET_DEVICE = 3;
    public final static int TAG_CLICK_ALARM_LIST = 4;
    private int mClickType;
    private String no = "0";
    private String send_tx;
    public final static int REQUEST_CODE = 100;
    public final static int RESULT_CODE = 101;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monitor;
    }

    @Override
    public BasePresenter getPresenter() {
        return cameraPresenter;
    }

    @Override
    public void initPresenter() {
        cameraPresenter = new CameraPresenter(new CameraModelImpl());
    }

    @Override
    protected void initData() {
        super.initData();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                LogUtil.d(TAG, "onReceive:" + action);
                if (action.equals(Constant.ADD_DEVICE_SUCCESS_ACTION)) {
                    refreshButtonClicked();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ADD_DEVICE_SUCCESS_ACTION);
        getActivity().getApplicationContext().registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initWidget(View mRoot) {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNoMoreView = getLayoutInflater().inflate(R.layout.no_device_more_footer, null);
        mAdapter = new DeviceListAdapter();
        mAdapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(mAdapter);
        initRefreshLayout();
        initLoadMore();
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Map map = mAdapter.getItem(position);
                EZCameraInfo cameraInfo = (EZCameraInfo)map.get("camera");
                EZDeviceInfo deviceInfo = (EZDeviceInfo)map.get("device");
                switch (view.getId()){
                    case R.id.item_play_btn:
                        mClickType = TAG_CLICK_PLAY;
                        if (cameraInfo == null) {
                            LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
                            return;
                        }
                        if (cameraInfo != null) {
                            Intent intent = new Intent(getActivity(), EZRealPlayActivity.class);
                            intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                            intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
                            intent.putExtra("titlename",cameraInfo.getCameraName());
                            startActivityForResult(intent, REQUEST_CODE);
                            return;
                        }
                        break;
                    case R.id.tab_remoteplayback_btn:
                        mClickType = TAG_CLICK_REMOTE_PLAY_BACK;
                        if (cameraInfo == null) {
                            LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
                            return;
                        }
                        if (cameraInfo != null) {
                            LogUtil.d(TAG, "cameralist have one camera");
                            Intent intent = new Intent(getActivity(), PlayBackListActivity.class);
                            intent.putExtra(RemoteListContant.QUERY_DATE_INTENT_KEY, DateTimeUtil.getNow());
                            intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                            startActivity(intent);
                            return;
                        }
                        break;
                    case R.id.tab_setdevice_btn:
                        mClickType = TAG_CLICK_SET_DEVICE;
                        Intent intent = new Intent(getActivity(), EZDeviceSettingActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
                        bundle.putParcelable(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                        intent.putExtra("Bundle", bundle);
                        startActivity(intent);
                        break;
                }
            }
        });

//        mAdapter.setOnClickListener(new EZCameraListAdapter.OnClickListener() {
//            @Override
//            public void onPlayClick(BaseAdapter adapter, View view, int position) {
//                mClickType = TAG_CLICK_PLAY;
//                final EZCameraInfo cameraInfo = mAdapter.getItem(position);
//                final EZDeviceInfo deviceInfo = mAdapter.getDeviceInfoItem(position);
//                if (cameraInfo == null) {
//                    LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
//                    return;
//                }
//                if (cameraInfo != null) {
//                    Intent intent = new Intent(getActivity(), EZRealPlayActivity.class);
//                    intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
//                    intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
//                    intent.putExtra("titlename",cameraInfo.getCameraName());
//                    startActivityForResult(intent, REQUEST_CODE);
//                    return;
//                }
//            }
//
//            @Override
//            public void onDeleteClick(BaseAdapter adapter, View view, int position) {
//                // TODO: 2020/7/27 0027
//            }
//
//            @Override
//            public void onAlarmListClick(BaseAdapter adapter, View view, int position) {
//                // TODO: 2020/7/27 0027
//            }
//
//            @Override
//            public void onRemotePlayBackClick(BaseAdapter adapter, View view, int position) {
//                mClickType = TAG_CLICK_REMOTE_PLAY_BACK;
//                EZCameraInfo cameraInfo = mAdapter.getItem(position);
//                if (cameraInfo == null) {
//                    LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
//                    return;
//                }
//                if (cameraInfo != null) {
//                    LogUtil.d(TAG, "cameralist have one camera");
//                    Intent intent = new Intent(getActivity(), PlayBackListActivity.class);
//                    intent.putExtra(RemoteListContant.QUERY_DATE_INTENT_KEY, DateTimeUtil.getNow());
//                    intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
//                    startActivity(intent);
//                    return;
//                }
//            }
//
//            @Override
//            public void onSetDeviceClick(BaseAdapter adapter, View view, int position) {
//                mClickType = TAG_CLICK_SET_DEVICE;
//                EZDeviceInfo deviceInfo = mAdapter.getDeviceInfoItem(position);
//                EZCameraInfo cameraInfo = mAdapter.getItem(position);
//                Intent intent = new Intent(getActivity(), EZDeviceSettingActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
//                bundle.putParcelable(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
//                intent.putExtra("Bundle", bundle);
//                startActivity(intent);
//                //bIsFromSetting = true;
//            }
//
//            @Override
//            public void onDevicePictureClick(BaseAdapter adapter, View view, int position) {
//                // TODO: 2020/7/27 0027
//            }
//
//            @Override
//            public void onDeviceVideoClick(BaseAdapter adapter, View view, int position) {
//                // TODO: 2020/7/27 0027
//            }
//
//            @Override
//            public void onDeviceDefenceClick(BaseAdapter adapter, View view, int position) {
//                // TODO: 2020/7/27 0027
//            }
//        });
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

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }
        });
    }

    public void setRefresh(){
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        // 下拉刷新，需要重置页数
        pageInfo.reset();
        request();
    }

    private void loadMore() throws UnsupportedEncodingException {
        request();
    }

    private void request() {
        cameraPresenter.getCamersInfoListTask(getActivity().getApplicationContext(), PAGE_SIZE, pageInfo);
    }


    /**
     * 刷新点击
     */
    private void refreshButtonClicked() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoCameraTipLy.setVisibility(View.GONE);
        mGetCameraFailTipLy.setVisibility(View.GONE);
        setRefresh();
    }

    @OnClick({R.id.btn_add, R.id.camera_list_refresh_btn, R.id.no_camera_tip_ly})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                //Intent intent = new Intent(EZCameraListActivity.this, CaptureActivity.class);
                //startActivity(intent);
                break;
            case R.id.camera_list_refresh_btn:
            case R.id.no_camera_tip_ly:
                refreshButtonClicked();
                break;
        }
    }




    /**
     * 从服务器获取最新事件消息
     */
    @Override
    public void getCameraList(List<EZDeviceInfo> result) {
        deviceInfoList.addAll(result);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        List<Map<String,Object>> dataList = new ArrayList<>();
        for (EZDeviceInfo deviceInfo : result){
            //设备在线
            if (deviceInfo.getStatus() != 2){
                List<EZCameraInfo> ls = deviceInfo.getCameraInfoList();
                for (EZCameraInfo cameraInfo : ls){
                    if (!cameraInfo.getCameraName().contains("视频")){
                        Map<String,Object> map = new LinkedHashMap<>();
                        map.put("camera",cameraInfo);
                        map.put("device",deviceInfo);
                        dataList.add(map);
                    }
                }
            }
        }
        if (pageInfo.isFirstPage()){
            mAdapter.setList(dataList);
        }else{
            Log.d("hjf","addList");
            mAdapter.addData(dataList);
        }
        if (dataList.size() < PAGE_SIZE) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }else{
            mAdapter.getLoadMoreModule().loadMoreComplete();
        }
        pageInfo.nextPage();
        if (result.size() == 0){
            mNoCameraTipLy.setVisibility(View.VISIBLE);
            mGetCameraFailTipLy.setVisibility(View.GONE);
        }

        if (result != null) {
//            if (mHeaderOrFooter) {
//                CharSequence dateText = DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date());
//                for (LoadingLayout layout : mListView.getLoadingLayoutProxy(true, false).getLayouts()) {
//                    ((PullToRefreshHeader) layout).setLastRefreshTime(":" + dateText);
//                }
//                mAdapter.clearItem();
//            }
//            if (mAdapter.getCount() == 0 && result.size() == 0) {
//                mListView.setVisibility(View.GONE);
//                mNoCameraTipLy.setVisibility(View.VISIBLE);
//                mGetCameraFailTipLy.setVisibility(View.GONE);
//                mListView.getRefreshableView().removeFooterView(mNoMoreView);
//            } else if (result.size() < 10) {
//                mListView.setFooterRefreshEnabled(false);
//                mListView.getRefreshableView().addFooterView(mNoMoreView);
//            } else if (mHeaderOrFooter) {
//                mListView.setFooterRefreshEnabled(true);
//                mListView.getRefreshableView().removeFooterView(mNoMoreView);
//            }

//            addCameraList(result);
//            mAdapter.notifyDataSetChanged();
//            cameraInfoList = mAdapter.getCameraInfoList();
            //更新封面
            cameraPresenter.refreshPic(mAdapter.getData());
        }
    }

    @Override
    public void setRefreshPic() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int errorCode) {
        switch (errorCode) {
            case ErrorCode.ERROR_WEB_SESSION_ERROR:
            case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
                ActivityUtils.handleSessionException(getActivity());
                break;
            default:
                if (mAdapter.getData().size() == 0) {
                    mRecyclerView.setVisibility(View.GONE);
                    mNoCameraTipLy.setVisibility(View.GONE);
                    mCameraFailTipTv.setText(Utils.getErrorTip(getActivity().getApplicationContext(), R.string.get_camera_list_fail, errorCode));
                    mGetCameraFailTipLy.setVisibility(View.VISIBLE);
                } else {
                    Utils.showToast(getActivity().getApplicationContext(), R.string.get_camera_list_fail, errorCode);
                }
                break;
        }
    }

    @Override
    public void sendTcp(TcpClient tcpClient, String no, String text) {

    }

    @Override
    public void tcpResult(Boolean b) {

    }

//    private void addCameraList(List<EZDeviceInfo> result) {
//        int count = result.size();
//        EZDeviceInfo item = null;
//        for (int i = 0; i < count; i++) {
//            item = result.get(i);
//            List<EZCameraInfo> CameraInfo_list = item.getCameraInfoList();
//            for (EZCameraInfo cameraInfo : CameraInfo_list){
//                mAdapter.addItem(cameraInfo,item);
//            }
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (bIsFromSetting || (mAdapter != null && mAdapter.getData().size() == 0)) {
            refreshButtonClicked();
            bIsFromSetting = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hjf","ondestroy");
        if (mReceiver != null) {
            getActivity().getApplicationContext().unregisterReceiver(mReceiver);
        }
        if (tcpClient!=null){
            if (tcpClient.isConnect()){
                tcpClient.disconnect();
            }
        }
    }

    @Override
    public ProgressDialog showUpdating(String s) {
        return null;
    }

    @Override
    public void setProgress(Integer integer) {

    }

    @Override
    public void hideUpdating() {

    }

    @Override
    public AlertDialog showConfirmDialog(String msg, String title, DialogInterface.OnClickListener listener) {
        return null;
    }

    @Override
    public void hideConfirmDialog() {

    }

    @Override
    public ProgressDialog showLoading() {
        return null;
    }

    @Override
    public ProgressDialog showLoading(int resid) {
        return null;
    }

    @Override
    public ProgressDialog showLoading(String msg) {
        return null;
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String message, int icon, int gravity) {

    }

    @Override
    public void showToast(String message) {
        ToastNotRepeat.show(this.getActivity().getApplicationContext(),message);
    }

}
