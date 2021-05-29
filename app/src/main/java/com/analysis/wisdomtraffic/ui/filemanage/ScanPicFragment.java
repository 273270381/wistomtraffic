package com.analysis.wisdomtraffic.ui.filemanage;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.PicNodeAdapter;
import com.analysis.wisdomtraffic.been.AppOperator;
import com.analysis.wisdomtraffic.been.ItemData;
import com.analysis.wisdomtraffic.been.RootData;
import com.analysis.wisdomtraffic.been.TosMessage;
import com.analysis.wisdomtraffic.been.base.BaseFragment;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.ui.filemanage.provider.LongClickCallBack;
import com.analysis.wisdomtraffic.ui.filemanage.provider.SecondNodeProvider;
import com.analysis.wisdomtraffic.utils.AnimationUtils;
import com.analysis.wisdomtraffic.utils.DataUtils;
import com.analysis.wisdomtraffic.utils.DensityUtil;
import com.analysis.wisdomtraffic.utils.ToastNotRepeat;
import com.analysis.wisdomtraffic.view.ScrollItemView;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.cocosw.bottomsheet.BottomSheet;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import static com.analysis.wisdomtraffic.been.AppConfig.DEFAULT_SAVE_IMAGE_PATH;
import static com.analysis.wisdomtraffic.utils.DataUtils.removeDuplicateWithOrder;

/**
 * @Author hejunfeng
 * @Date 14:58 2021/3/26 0026
 * @Description com.analysis.wisdomtraffic.ui.filemanage
 **/
public class ScanPicFragment extends BaseFragment{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.footLayout)
    ScrollItemView footLayout;

    private PicNodeAdapter adapter;
    private String camera_name;
    private String excPath;
    private SecondNodeProvider secondNodeProvider;
    private boolean isShowCheck = false;
    private List<String> checkList = new ArrayList<>();
    private int height;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    List<BaseNode> datas = (List<BaseNode>) msg.obj;
                    Log.d("hjf","datas.size : "+datas.size());
                    adapter.setList(datas);
                    recyclerView.scheduleLayoutAnimation();
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
    protected void initWidget(View mRoot) {
        height = DensityUtil.dip2px(getActivity(),81);
        footLayout.scrollTo(0,-height);
        EventBus.getDefault().register(this);
        camera_name = getArguments().getString("name");
        secondNodeProvider = new SecondNodeProvider(camera_name);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        adapter = new PicNodeAdapter();
        adapter.setNodeProvider(secondNodeProvider);
        recyclerView.setAdapter(adapter);

        footLayout.setShareClickListener(new ScrollItemView.ScrollOnClickListener() {
            @Override
            public void click() {
                checkList = secondNodeProvider.getMap();
                senfiles("分享",checkList);
            }
        });

        footLayout.setDeleteClickListener(new ScrollItemView.ScrollOnClickListener() {
            @Override
            public void click() {
                checkList = secondNodeProvider.getMap();
                deleteFile(checkList);
            }
        });

    }


    @Override
    protected void initData() {
        excPath = DEFAULT_SAVE_IMAGE_PATH+camera_name;
        getData(excPath);
        secondNodeProvider.setLongClickCallBack(new LongClickCallBack() {

            @Override
            public void showCheckBox() {
                if (!isShowCheck){
                    secondNodeProvider.setShowCheckBox(true);
                    adapter.notifyDataSetChanged();
                }else{
                    secondNodeProvider.setShowCheckBox(false);
                    adapter.notifyDataSetChanged();
                }
                isShowCheck = !isShowCheck;
            }

            @Override
            public void addFooter() {
                if (isShowCheck){
                    footLayout.ScrollTo(0,height);
                    AnimationUtils.startZoomAnim(recyclerView,recyclerView.getHeight()-height);
                }else{
                    footLayout.ScrollTo(0,-height);
                    AnimationUtils.startZoomAnim(recyclerView,recyclerView.getHeight()+height);
                }
                checkList.clear();
                secondNodeProvider.clearMap();

            }
        });
    }



    /**
     * 文件发送
     * @param dlgTitle
     * @param urls
     */
    private void senfiles( String dlgTitle, List<String> urls) {
        ArrayList<Uri> files = new ArrayList<>();
        if (urls.size() > 0){
            for (String url : urls){
                Uri uri = Uri.parse(url);
                files.add(uri);
            }
            if (files.size() == 0) {
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.setType("*/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // 设置弹出框标题
            // 自定义标题
            if (dlgTitle != null && !"".equals(dlgTitle)) {
                startActivity(Intent.createChooser(intent, dlgTitle));
            } else { // 系统默认标题
                startActivity(intent);
            }
        }else{
            ToastNotRepeat.show(getActivity(),"请选择文件");
        }
    }

    private void deleteFile(List<String> urls){
        if (urls.size() > 0){
            for (String url : urls){
                File file = new File(url);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            isShowCheck = false;
            secondNodeProvider.setShowCheckBox(false);
            footLayout.ScrollTo(0,-height);
            AnimationUtils.startZoomAnim(recyclerView,recyclerView.getHeight()+height);
            ToastNotRepeat.show(getActivity(),"删除成功");
            getData(excPath);
            adapter.notifyDataSetChanged();
        }else{
            ToastNotRepeat.show(getActivity(),"请选择文件");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scan_pic2;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TosMessage msg){
        switch (msg.getType()){
            case 1:
                Log.d("hjf","TosMessage "+msg.getType());
                getData(excPath);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
    }

    public void getData(String path){
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                List<String> pathList = DataUtils.getImagePathFromSD(path);
                List<String> dateList = removeDuplicateWithOrder(DataUtils.getFileTime(pathList));
                List<BaseNode> list = new ArrayList<>();
                for (String dataStr : dateList){
                    List<BaseNode> items = new ArrayList<>();
                    for (String pathStr : pathList){
                        long time = new File(pathStr).lastModified();
                        String date = format.format(time);
                        if (dataStr.equals(date)){
                            ItemData itemData = new ItemData(pathStr);
                            items.add(itemData);
                        }
                    }
                    RootData rootData = new RootData(items,dataStr);
                    list.add(rootData);
                }
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        });
    }
}
