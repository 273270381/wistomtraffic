package com.analysis.wisdomtraffic.ui.filemanage;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.adapter.PhotoPagerAdapter;
import com.analysis.wisdomtraffic.been.AppOperator;
import com.analysis.wisdomtraffic.been.TosMessage;
import com.analysis.wisdomtraffic.been.base.BaseActivity;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.utils.DataUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.analysis.wisdomtraffic.been.AppConfig.DEFAULT_SAVE_IMAGE_PATH;
import static com.analysis.wisdomtraffic.been.AppConfig.DEFAULT_SAVE_VIDEO_PATH;


/**
 * Created by hejunfeng on 2020/7/24 0024
 */
public class PictureActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.share)
    ImageButton shareBtn;
    @BindView(R.id.delate)
    ImageButton delateBtn;
    @BindView(R.id.refresh)
    ImageButton refresh;
    private List<String> urlList = new ArrayList<>();
    private Boolean flag = false;
    private String url;
    private String cameraName;
    private int position;
    private Boolean isVideo;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0 :
                    position = msg.arg1;
                    urlList = (List<String>) msg.obj;
                    setAdapter();
                    break;
            }
        }
    };

    private void setAdapter() {
        PhotoPagerAdapter viewPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), (ArrayList<String>) urlList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                url = urlList.get(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_picture;
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        flag = intent.getBooleanExtra("flag",false);
        isVideo = intent.getBooleanExtra("isVideo",false);
        cameraName = intent.getStringExtra("cameraname");
        getData(url);

        if (!flag){
            delateBtn.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
        }else{
            delateBtn.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
        }
    }

    private void getData(String s) {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                String path = "";
                if (isVideo){
                    path = DEFAULT_SAVE_VIDEO_PATH+cameraName;
                }else{
                    path = DEFAULT_SAVE_IMAGE_PATH+cameraName;
                }
                List<String> urls = DataUtils.getImagePathFromSD(path);
                int position = urls.lastIndexOf(s);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.arg1 = position;
                msg.obj = urls;
                handler.sendMessage(msg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TosMessage msg){
        switch (msg.getType()){

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.share,R.id.refresh,R.id.delate})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                shareImg("分享","分享","分享",Uri.parse(url));
                break;
            case R.id.refresh:
                reFresh();
                break;
            case R.id.delate:
                delete(url);
                break;
        }
    }

    private void delete(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
            Toast.makeText(PictureActivity.this, "文件已删除!", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(TosMessage.getInstance(1,path));
            finish();
        }
    }

    private void reFresh() {
        String path = urlList.get(0);
        File file = new File(path);
        if (file.exists()&&file.isFile()){
            file.delete();
            //Toast.makeText(PictureActivity.this,"文件已删除!", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent();
            intent1.setAction("com.delate.pic");
            sendBroadcast(intent1);
            finish();
        } else {
            String[] str = path.split("\\.");
            String s1 = str[0].substring(0, str[0].length() - 5);
            String path2 = s1 + "." + str[1];
            File file2 = new File(path2);
            if (file2.exists()&&file2.isFile()){
                file2.delete();
                //Toast.makeText(PictureActivity.this,"文件已删除!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent1.setAction("com.delate.pic");
                sendBroadcast(intent1);
                finish();
            }
        }
    }

    /**
     * 分享图片和文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     * @param uri
     *            图片资源URI
     */
    private void shareImg(String dlgTitle, String subject, String content, Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (content != null && !"".equals(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }
        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }

}
