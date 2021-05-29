package com.analysis.wisdomtraffic.ui.filemanage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.analysis.wisdomtraffic.OSCApplication;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.ScanPicAdapter;
import com.analysis.wisdomtraffic.been.AppOperator;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.videogo.exception.BaseException;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author hejunfeng
 * @Date 9:41 2021/3/22 0022
 * @Description com.analysis.wisdomtraffic.ui.filemanage
 **/
public class ScanFileFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<EZCameraInfo> list_ezCameras = new ArrayList<>();
    private ScanPicAdapter adapter;
    private String TAG = "hjf";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0 :
                    adapter.setList(list_ezCameras);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(layoutManager);
        EZCameraInfo info = new EZCameraInfo();
        info.setCameraName("最近");
        //list_ezCameras.add(0,info);
        rv.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        adapter = new ScanPicAdapter();
        adapter.setAnimationEnable(true);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent icamera = new Intent(getActivity(), FileActivity.class);
                icamera.putExtra("name",list_ezCameras.get(position).getCameraName());
                startActivity(icamera);
            }
        });
        getDevice();
    }

    private void getDevice() {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<EZDeviceInfo> list_ezdevices = OSCApplication.getOpenSDK().getDeviceList(0,40);
                    if (list_ezdevices.size() != 0 ){
                        for (EZDeviceInfo ezDeviceInfo : list_ezdevices){
                            for (EZCameraInfo cameraInfo : ezDeviceInfo.getCameraInfoList()){
                                if (!cameraInfo.getCameraName().contains("视频")){
                                    list_ezCameras.add(cameraInfo);
                                }
                            }
                        }
//                        EventBus.getDefault().post(TosMessage.getInstance(1,list_ezCamera));
                        Message msg = Message.obtain();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void initWidget(View mRoot) {
        super.initWidget(mRoot);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file_manger;
    }


}
