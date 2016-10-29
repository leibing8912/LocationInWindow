package cn.jianke.getlocationinwindowdemo.module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cn.jianke.getlocationinwindowdemo.R;

/**
 * @className:MainActivity
 * @classDescription: 首页
 * @author: leibing
 * @createTime: 2016/10/29
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    // 症状id标识
    public final static String SYMPTOM_ID = "id";
    // 症状id
    private String id = "337376";

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void registerListener() {
        findViewById(R.id.btn_main).setOnClickListener(this);
    }

    @Override
    protected void getData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_main:
                // 跳转症状详情页面
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(SYMPTOM_ID, id);
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, SymptomDetailActivity.class);
                startActivity(intent);
                break;
        }
    }
}
