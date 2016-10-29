package cn.jianke.getlocationinwindowdemo.httprequest.api;

import android.app.Activity;
import android.app.Dialog;
import cn.jianke.getlocationinwindowdemo.httprequest.ApiCallback;
import cn.jianke.getlocationinwindowdemo.httprequest.JkApiCallback;
import cn.jianke.getlocationinwindowdemo.httprequest.JkApiRequest;
import cn.jianke.getlocationinwindowdemo.httprequest.httpresponse.SymptomDetailResponse;
import cn.jianke.getlocationinwindowdemo.module.widget.LoadingDialog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @className: ApiSymptomDetail
 * @classDescription: 症状详情数据
 * @author: leibing
 * @createTime: 2016/10/08
 */
public class ApiSymptomDetail {
    // 主域名
    public static final String HOST = "http://askapi.jianke.com";
    // Api商店
    private ApiStore mApiStore;

    /**
     * Constructor
     * @author luchaoyeu
     * @createTime 2016/9/23
     * @lastModify 2016/9/23
     * @param
     * @return
     */
    public ApiSymptomDetail() {
        // 初始化api
        mApiStore = JkApiRequest.getInstance().create(ApiStore.class, HOST);
    }

    /**
     * 获取症状详情数据
     * @author leibing
     * @createTime 2016/10/07
     * @lastModify 2016/10/07
     * @param id 症状id
     * @param activity 页面实例
     * @param callback 回调
     * @return
     */
    public void getSymptomDetail(String id,  Activity activity,
                                 ApiCallback<SymptomDetailResponse> callback){
        Call<SymptomDetailResponse> mCall =  mApiStore.getSymptomDetail(id);
        mCall.enqueue(new JkApiCallback<SymptomDetailResponse>(callback, activity,
                getLoadingDialog(activity)));
    }

    /**
     * 获取加载对话框
     * @author leibing
     * @createTime 2016/10/29
     * @lastModify 2016/10/29
     * @param
     * @return
     */
    private Dialog getLoadingDialog(Activity activity){
        if (activity == null)
            return null;
        // 初始化对话框
        Dialog loadingDialog = LoadingDialog.createLoadingDialog(activity);
        // 设置对话框的外面点击,不让对话框消失
        loadingDialog.setCanceledOnTouchOutside(false);
        // 显示加载对话框
        loadingDialog.show();
        return loadingDialog;
    }

    /**
     * @interfaceName: ApiStore
     * @interfaceDescription: 症状详情或疾病详情数据
     * @author: leibing
     * @createTime: 2016/08/30
     */
    private interface ApiStore {
        // 获取症状详情数据
        @GET("/app/symptom/detail")
        Call<SymptomDetailResponse> getSymptomDetail(
                @Query("id") String id);
    }
}
