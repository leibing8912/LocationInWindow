package cn.jianke.getlocationinwindowdemo.module.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import cn.jianke.getlocationinwindowdemo.module.manager.AppManager;

/**
 * @className: BaseActivity
 * @classDescription: 页面基类
 * @author: leibing
 * @createTime: 2016/10/29
 */
public abstract class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManager.getInstance().addActivity(this);
        // 设置布局
        setContentView();
        // 初始化控件
        initView();
        // 注册监听
        registerListener();
        // 加载网络数据
        getData();
    }

    @Override
    protected void onDestroy() {
        // 从堆栈移除Activity
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    // 设置布局
    protected abstract void setContentView();
    // 初始化控件
    protected abstract void initView();
    // 注册监听
    protected abstract void registerListener();
    // 加载网络数据
    protected abstract void getData();
}
