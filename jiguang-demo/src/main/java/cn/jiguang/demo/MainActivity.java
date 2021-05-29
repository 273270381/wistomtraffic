package cn.jiguang.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // jpush_click_start
    public void onClickJPush(View v) {
        try{
            Intent intent = new Intent();
            intent.setClassName(getPackageName(),"cn.jiguang.demo.jpush.MainActivity");
            startActivity(intent);
        }catch (Throwable e){
            Toast.makeText(this,"can't find jpush demo",Toast.LENGTH_SHORT).show();
        }
    }
    // jpush_click_end
}
