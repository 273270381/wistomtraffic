package com.analysis.wisdomtraffic.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.analysis.wisdomtraffic.OSCApplication;
import com.analysis.wisdomtraffic.R;
import com.analysis.wisdomtraffic.been.AlarmContant;
import com.analysis.wisdomtraffic.been.base.BaseActivity;
import com.analysis.wisdomtraffic.been.base.BasePresenter;
import com.analysis.wisdomtraffic.utils.OSCSharedPreference;
import com.analysis.wisdomtraffic.utils.ToastNotRepeat;
import com.videogo.openapi.EZOpenSDK;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hejunfeng on 2020/7/18 0018
 */
public class LogInActivity extends BaseActivity implements LoginView, View.OnClickListener, View.OnFocusChangeListener
                                            , EasyPermissions.PermissionCallbacks {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_login_username)
    EditText etUsername;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button button;
    @BindView(R.id.ll_login_pwd)
    LinearLayout ll_login_pwd;
    @BindView(R.id.ll_login_username)
    LinearLayout ll_login_username;
    @BindView(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;
    @BindView(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private static String[] allpermissions = {
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.VIBRATE,
            Manifest.permission.CAMERA,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO
    };

    private LoginPresenter loginPresenter;
    private String strUsername, strPassword;;
    private String name ,password;
    private String TAG = "LoginActivity";

    @Override
    public BasePresenter getPresenter() {
        return loginPresenter;
    }

    @Override
    public void initPresenter() {
        loginPresenter = new LoginPresenter(new LoginModelImpl());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    public static void show(Context context){
        context.startActivity(new Intent(context, LogInActivity.class));
    }

    @Override
    protected void initData() {
        super.initData();
        requestPermission();
        name = OSCSharedPreference.getInstance().getUserName();
        password = OSCSharedPreference.getInstance().getPassWord();
        if (!name.equals("")&&!password.equals("")){
            etUsername.setText(name);
            etPassword.setText(password);
        }else{
            if (!name.equals("")){
                etUsername.setText(name);
            }
        }
        try {

            Typeface tf = Typeface.createFromAsset(getAssets(), "font/FZHZGBJW.TTF");
            title.setTypeface(tf);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void initWidget() {
        etUsername.setOnFocusChangeListener(this);
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username =s.toString().trim();
                if (username.length() > 0){
                    ll_login_username.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.VISIBLE);
                }else{
                    ll_login_username.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd)){
                    button.setBackgroundResource(R.drawable.bg_login_submit);
                    button.setTextColor(getResources().getColor(R.color.white));
                }else{
                    button.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    button.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        etPassword.setOnFocusChangeListener(this);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    ll_login_pwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginPwdDel.setVisibility(View.VISIBLE);
                } else {
                    mIvLoginPwdDel.setVisibility(View.INVISIBLE);
                }

                String pwd = etPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    button.setBackgroundResource(R.drawable.bg_login_submit);
                    button.setTextColor(getResources().getColor(R.color.white));
                } else {
                    button.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    button.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });
    }

    @Override
    public void showData(String s) {
        Log.d(TAG,"result=" + s);
    }

    @Override
    public void setAccessToken(String accessToken) {
        Intent intent = new Intent();
        intent.setAction("com.action.OAUTH_SUCCESS_ACTION_TRAFFIC");
        EZOpenSDK.getInstance().setAccessToken(accessToken);
        LogInActivity.this.sendBroadcast(intent);
        LogInActivity.this.finish();
    }


    @Override
    public void setUserType(String strUsername) {
        if (strUsername.equals("chengguan")){
            OSCApplication.setUser_type(6);
        }else if (strUsername.equals("shiwuju")){
            OSCApplication.setUser_type(7);
        }else if(strUsername.equals("huanbaoju")){
            OSCApplication.setUser_type(8);
        }else if (strUsername.equals("zhifaju")){
            OSCApplication.setUser_type(9);
        }else if (strUsername.equals("fazhanju")){
            OSCApplication.setUser_type(10);
        }else if (strUsername.equals("admin")){
            OSCApplication.setUser_type(11);
        }
    }

    @Override
    public void showToast(String message) {
        ToastNotRepeat.show(this, message);
    }


    @OnClick({R.id.btn_login, R.id.iv_login_username_del, R.id.iv_login_pwd_del})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                strUsername = etUsername.getText().toString().trim();
                strPassword = etPassword.getText().toString().trim();
                String url = AlarmContant.service_url_hjf+"deeplearning-user/admin/login";
                loginPresenter.LogIn(this, strUsername, strPassword, url);
                break;
            case R.id.iv_login_username_del:
                etUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                etPassword.setText(null);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_login_username:
                if (hasFocus){
                    ll_login_username.setActivated(true);
                    ll_login_pwd.setActivated(false);
                }
                break;
            case R.id.et_login_password:
                if (hasFocus) {
                    ll_login_pwd.setActivated(true);
                    ll_login_username.setActivated(false);
                }
                break;
        }
    }
    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    private void requestPermission(){
        if (EasyPermissions.hasPermissions(this, allpermissions)){
            Log.d(TAG, "onClick: 获取读写内存权限,Camera权限和wifi权限");
        }else{
            EasyPermissions.requestPermissions(this, "没有该权限，此应用程序可能无法正常工作，是否现在开启", RC_EXTERNAL_STORAGE, allpermissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
