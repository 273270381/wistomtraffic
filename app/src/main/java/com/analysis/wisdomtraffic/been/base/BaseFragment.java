package com.analysis.wisdomtraffic.been.base;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.utils.CommonToast;
import com.analysis.wisdomtraffic.utils.DialogHelper;
import com.analysis.wisdomtraffic.utils.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基础类
 * Created by hejunfeng on 2020/7/17 0017
 */
public abstract  class BaseFragment extends Fragment implements BaseView {
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    private RequestManager mImgLoader;
    protected LayoutInflater mInflater;
    protected Unbinder unbinder;
    public abstract BasePresenter getPresenter();
    public abstract void initPresenter();

    private ProgressDialog _waitDialog;
    private AlertDialog _comformDialog;
    private ProgressDialog _updating;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        initPresenter();
        if (getPresenter() != null){
            getPresenter().attachView(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        if (getPresenter() != null){
            getPresenter().detachView();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            onBindViewBefore(mRoot);
            unbinder = ButterKnife.bind(this, mRoot);
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        initStatusBar();
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    protected  void initData(){

    };

    protected  void initWidget(View mRoot){

    };

    private void onBindViewBefore(View mRoot) {
    }


    protected void initStatusBar(){
        //沉浸式状态栏
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
        mImgLoader = null;
        mBundle = null;
    }

    protected abstract int getLayoutId();


    protected void initBundle(Bundle mBundle) {
    }

    protected <T extends View> T findView(int viewId){
        return(T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key){
        if (mBundle == null){
            return null;
        }
        return (T) mBundle.getSerializable(key);
    }


    /**
     * 获取一个图片加载管理器
     * @return RequestManager
     */
    public synchronized RequestManager getImgLoader(){
        if (mImgLoader == null)
            mImgLoader = Glide.with(mContext);
        return mImgLoader;
    }

    /***
     * 从网络中加载数据
     * @param viewId   view的id
     * @param imageUrl 图片地址
     */
    protected void setImageFromNet(int viewId, String imageUrl) {
        setImageFromNet(viewId, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId      view的id
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(int viewId, String imageUrl, int placeholder) {
        ImageView imageView = findView(viewId);
        setImageFromNet(imageView, imageUrl, placeholder);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView imageView
     * @param imageUrl  图片地址
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl) {
        setImageFromNet(imageView, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl, int placeholder) {
        ImageLoader.loadImage(getImgLoader(), imageView, imageUrl, placeholder);
    }

    protected void setText(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
    }

    protected void setText(int viewId, String text, String emptyTip) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText(emptyTip);
            return;
        }
        textView.setText(text);
    }

    protected void setTextEmptyGone(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
    }

    protected <T extends View> T setGone(int id) {
        T view = findView(id);
        view.setVisibility(View.GONE);
        return view;
    }

    protected <T extends View> T setVisibility(int id) {
        T view = findView(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected void setInVisibility(int id) {
        findView(id).setVisibility(View.INVISIBLE);
    }

    protected void onRestartInstance(Bundle bundle) {

    }

    @SuppressLint("ObsoleteSdkInt,PrivateApi")
    private static int getStatusHeight(Context context) {
        int statusHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                statusHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    @Override
    public ProgressDialog showUpdating(String title) {
        if (_updating == null){
            _updating = DialogHelper.getProgressDialog(getActivity());
        }
        if (_updating != null){
            _updating.setTitle(title);
            _updating.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            _updating.setCancelable(false);// 设置允许取消
            _updating.show();
        }
        return _updating;
    }

    @Override
    public void setProgress(Integer integer) {
        if (_updating != null){
            _updating.setProgress(integer.intValue());
        }
    }

    @Override
    public void hideUpdating() {
        if (_updating != null){
            try {
                _updating.dismiss();
                _updating = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public AlertDialog showConfirmDialog(String msg, String title, DialogInterface.OnClickListener listener) {
        if (_comformDialog == null){
            _comformDialog = DialogHelper.getConfirmDialog(getActivity(), msg, listener).create();
        }
        if (_comformDialog != null){
            _comformDialog.setTitle(title);
            _comformDialog.show();
        }
        return _comformDialog;
    }

    @Override
    public void hideConfirmDialog() {
        if (_comformDialog != null){
            try {
                _comformDialog.dismiss();
                _comformDialog = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ProgressDialog showLoading() {
        return showLoading(R.string.loading);
    }

    @Override
    public ProgressDialog showLoading(int resid) {
        return showLoading(getString(resid));
    }

    @Override
    public ProgressDialog showLoading(String message) {
        if (_waitDialog == null) {
            _waitDialog = DialogHelper.getProgressDialog(getActivity(), message);
        }
        if (_waitDialog != null) {
            _waitDialog.setMessage(message);
            _waitDialog.show();
        }
        return _waitDialog;
    }

    @Override
    public void hideLoading() {
        if (_waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this.getActivity());
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public void showToast(String message) {

    }
}
