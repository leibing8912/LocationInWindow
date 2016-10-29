package cn.jianke.getlocationinwindowdemo.httprequest;

import android.app.Activity;
import android.app.Dialog;

import cn.jianke.getlocationinwindowdemo.httprequest.httpresponse.BaseResponse;
import cn.jianke.getlocationinwindowdemo.module.manager.AppManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className: JkApiCallback
 * @classDescription: 统一Api回调
 * @author: leibing
 * @createTime: 2016/08/30
 */
public class JkApiCallback<T> implements Callback<T> {
    // 回调
    private ApiCallback<T> mCallback;
    // 页面实例
    private Activity activity;
    // 加载对话框
    private Dialog loadingDialog;

    /**
     * Constructor
     * @author leibing
     * @createTime 2016/08/30
     * @lastModify 2016/08/30
     * @param mCallback 回调
     * @param activity 页面实例
     * @return
     */
    public JkApiCallback(ApiCallback<T> mCallback, Activity activity, Dialog loadingDialog){
        this.mCallback = mCallback;
        this.activity = activity;
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (activity == null){
            throw new NullPointerException("activity == null");
        }
        if (mCallback == null){
            throw new NullPointerException("mCallback == null");
        }
        if (loadingDialog == null){
            throw new NullPointerException("loadingDialog == null");
        }
        // 处理是否当前页，如果非当前页则无需回调更新UI
        if (!AppManager.getInstance().isCurrent(activity)){
            // activity去引用,避免内存泄漏
            activity = null;
            return;
        }

        // 关闭加载对话框
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }

        // 处理回调
        if (response != null && response.body() != null){
            if (((BaseResponse)response.body()).isSuccess()){
                mCallback.onSuccess((T)response.body());
            }else {
                mCallback.onError(((BaseResponse) response.body()).getErrormsg());
            }
        }else {
            mCallback.onFailure();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (mCallback == null){
            throw new NullPointerException("mCallback == null");
        }
        mCallback.onFailure();
    }
}
