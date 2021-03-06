package com.analysis.wisdomtraffic.ui.filemanage;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.analysis.wisdomtraffic.R;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import java.io.File;
public class PhotoFragment extends Fragment {
    private String url;
    private PhotoView mPhotoView;
    private VideoView mVideoView;
    private ImageView mvideoplay;
    private String UrlEnd;

    /**
     * 获取这个fragment需要展示图片的url
     * @param url
     * @return
     */
    public static PhotoFragment newInstance(String url) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        UrlEnd = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img, container, false);
        mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
        mVideoView = (VideoView) view.findViewById(R.id.videoview);
        mvideoplay = (ImageView) view.findViewById(R.id.videoplay);
        //设置缩放类型，默认ScaleType.CENTER（可以不设置）
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER);
        //设置控制条
        MediaController mediaController = new MediaController(inflater.getContext());
        mVideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(mVideoView);
        mediaController.show();

        mVideoView.setVideoURI(Uri.parse(url));
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //ToastUtils.showToast(getContext(),"长按事件");
                return true;
            }
        });


        mPhotoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                //ToastUtils.showToast(getContext(),"点击事件，真实项目中可关闭activity");
            }
        });
        mvideoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.setClickable(true);
                mVideoView.start();
                mVideoView.setVisibility(View.VISIBLE);
                mvideoplay.setVisibility(View.GONE);
                mPhotoView.setVisibility(View.GONE);
            }
        });
        File file = new File(url);
        if (file.exists()){
            com.bumptech.glide.Glide.with(getContext())
                    .load(url)
                    .fitCenter()//缩放图像测量出来等于或小于ImageView的边界范围,该图像将会完全显示
                    .into(mPhotoView);
        }else{
            String[] urls = url.split("\\.");
            String imgPath = urls[0].substring(0,urls[0].length()-5)+"."+urls[1];
            com.bumptech.glide.Glide.with(getContext())
                    .load(imgPath)
                    .fitCenter()//缩放图像测量出来等于或小于ImageView的边界范围,该图像将会完全显示
                    .into(mPhotoView);
        }
        //      }

        if(UrlEnd.equals("mp4")){
            mvideoplay.setVisibility(View.VISIBLE);
        }
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mvideoplay.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

}
