package cn.jianke.getlocationinwindowdemo.module.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jianke.getlocationinwindowdemo.R;
import cn.jianke.getlocationinwindowdemo.httprequest.ApiCallback;
import cn.jianke.getlocationinwindowdemo.httprequest.api.ApiSymptomDetail;
import cn.jianke.getlocationinwindowdemo.httprequest.httpresponse.SymptomDetailResponse;
import cn.jianke.getlocationinwindowdemo.module.util.HTMLSpirit;
import cn.jianke.getlocationinwindowdemo.module.util.StringUtil;
import cn.jianke.getlocationinwindowdemo.module.util.Unicode2String;
import cn.jianke.getlocationinwindowdemo.module.widget.ObservableScrollView;
import static cn.jianke.getlocationinwindowdemo.module.activity.MainActivity.SYMPTOM_ID;

/**
 * @className: SymptomDetailActivity
 * @classDescription: 症状详情
 * @author: leibing
 * @createTime: 2016/10/29
 */
public class SymptomDetailActivity extends BaseActivity implements View.OnClickListener{
    // 症状id
    private String id = "";
    // 顶部横向滑动控件
    private HorizontalScrollView symptomDetailHscv;
    // 顶部tab动态加载容器
    private LinearLayout symptomDetailContainerLy;
    // 滑动控件
    private ObservableScrollView symptomDetailScv;
    // 症状模块
    private LinearLayout symptomModuleLy;
    // 症状标题
    private TextView symptomTitleTv;
    // 症状内容
    private TextView symptomContentTv;
    // 病因模块
    private LinearLayout pathogenyModuleLy;
    // 病因内容
    private TextView pathogenyContentTv;
    // 检查模块
    private LinearLayout checkoutModuleLy;
    // 检查内容
    private TextView checkoutContentTv;
    // 诊断模块
    private LinearLayout diagnoseModuleLy;
    // 诊断内容
    private TextView diagnoseContentTv;
    // 预防模块
    private LinearLayout preventModuleLy;
    // 预防内容
    private TextView preventContentTv;
    // 食疗模块
    private LinearLayout foodTreatModuleLy;
    // 食疗内容
    private TextView foodTreatContentTv;
    // 症状详情数据
    private SymptomDetailResponse symptomDetailResponse;
    // 症状详情顶部tab横向宽度坐标
    private final int[] symptomDetailHorizontalWidth = new int[6];
    // 症状详情滑动高度坐标
    private final int[] symtomDetailScvHeight = new int[6];
    // 是否顶部点击事件
    private boolean isTopClick = false;
    // 上次点击点顶部tab item的位置
    private int lastPosition = 0;
    // 症状详情api
    private ApiSymptomDetail mApiSymptomDetail;

    @Override
    protected void setContentView() {
        // 指定布局
        setContentView(R.layout.activity_symptom_detail);
    }

    @Override
    protected void initView() {
        // 滑动控件
        symptomDetailScv = (ObservableScrollView) findViewById(R.id.scv_symptom_detail);
        // 顶部横向滑动控件
        symptomDetailHscv = (HorizontalScrollView) findViewById(R.id.hscv_symptom_detail);
        // 顶部tab动态加载容器
        symptomDetailContainerLy = (LinearLayout) findViewById(R.id.ly_symptom_detail_container);
        // 症状模块
        symptomModuleLy = (LinearLayout) findViewById(R.id.ly_symptom_module);
        // 症状标题
        symptomTitleTv = (TextView) findViewById(R.id.tv_symptom_title);
        // 症状内容
        symptomContentTv = (TextView) findViewById(R.id.tv_symptom_content);
        // 病因模块
        pathogenyModuleLy = (LinearLayout) findViewById(R.id.ly_pathogeny_module);
        // 病因内容
        pathogenyContentTv = (TextView) findViewById(R.id.tv_pathogeny_content);
        // 检查模块
        checkoutModuleLy = (LinearLayout) findViewById(R.id.ly_checkout_module);
        // 检查内容
        checkoutContentTv = (TextView) findViewById(R.id.tv_checkout_content);
        // 诊断模块
        diagnoseModuleLy = (LinearLayout) findViewById(R.id.ly_diagnose_module);
        // 诊断内容
        diagnoseContentTv = (TextView) findViewById(R.id.tv_diagnose_content);
        // 预防模块
        preventModuleLy = (LinearLayout) findViewById(R.id.ly_prevent_module);
        // 预防内容
        preventContentTv = (TextView) findViewById(R.id.tv_prevent_content);
        // 食疗模块
        foodTreatModuleLy = (LinearLayout) findViewById(R.id.ly_food_treat_module);
        // 食疗内容
        foodTreatContentTv = (TextView) findViewById(R.id.tv_food_treat_content);

        // 获取意图传值
        getIntentData();
        // 初始化症状详情api
        mApiSymptomDetail = new ApiSymptomDetail();
        // 初始化症状详情顶部tab
        initSymptomDetailHorizontalTab();
    }

    @Override
    protected void registerListener() {
        // 退出当前页面
        findViewById(R.id.iv_actionbar_back).setOnClickListener(this);
        // 设置滑动监听
        if (symptomDetailScv != null)
            symptomDetailScv.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                @Override
                public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                            int oldx, int oldy, int oritention) {
                        if (isTopClick) {
                            isTopClick = false;
                            return;
                         }
                        // 症状详情
                        // 如果症状详情顶部tab横向宽度坐标或者症状详情滑动高度坐标则返回
                        if (symtomDetailScvHeight == null || symptomDetailHorizontalWidth == null)
                            return;
                        if (y >= symtomDetailScvHeight[0] && y < symtomDetailScvHeight[1]) {
                            // 症状
                            setSymtomHorizontalCurrentPostion(symptomDetailContainerLy.getChildAt(0),
                                    false, symptomDetailHorizontalWidth[0], y);
                        } else if (y >= symtomDetailScvHeight[1] && y < symtomDetailScvHeight[2]) {
                            // 病因
                            setSymtomHorizontalCurrentPostion(symptomDetailContainerLy.getChildAt(1),
                                    false, symptomDetailHorizontalWidth[1], y);
                        } else if (y >= symtomDetailScvHeight[2] && y < symtomDetailScvHeight[3]) {
                            // 检查
                            setSymtomHorizontalCurrentPostion(symptomDetailContainerLy.getChildAt(2),
                                    false, symptomDetailHorizontalWidth[2], y);
                        } else if (y >= symtomDetailScvHeight[3] && y < symtomDetailScvHeight[4]) {
                            // 诊断
                            setSymtomHorizontalCurrentPostion(symptomDetailContainerLy.getChildAt(3),
                                    false, symptomDetailHorizontalWidth[3], y);
                        } else if (y >= symtomDetailScvHeight[4] && y < symtomDetailScvHeight[5]) {
                            // 预防
                            setSymtomHorizontalCurrentPostion(symptomDetailContainerLy.getChildAt(4),
                                    false, symptomDetailHorizontalWidth[4], y);
                        } else if (y >= symtomDetailScvHeight[5]) {
                            // 食疗
                            setSymtomHorizontalCurrentPostion(symptomDetailContainerLy.getChildAt(5),
                                    false, symptomDetailHorizontalWidth[5], y);
                        }
                }
            });
    }

    @Override
    protected void getData() {
        if (mApiSymptomDetail != null){
            // 请求数据
            mApiSymptomDetail.getSymptomDetail(id, SymptomDetailActivity.this,
                    new ApiCallback<SymptomDetailResponse>() {
                @Override
                public void onSuccess(SymptomDetailResponse response) {
                    // 更新UI
                    updateSymptomUI(response);
                }

                @Override
                public void onError(String err_msg) {
                    Toast.makeText(SymptomDetailActivity.this,
                            err_msg, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(SymptomDetailActivity.this,
                            "网络不给力", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 更新症状详情UI
     * @author leibing
     * @createTime 2016/10/08
     * @lastModify 2016/10/08
     * @param response 症状详情数据
     * @return
     */
    private void updateSymptomUI(SymptomDetailResponse response) {
        // 更新症状详情数据
        symptomDetailResponse = response;
        // 症状
        if (StringUtil.isNotEmpty(symptomDetailResponse.namecn)
                && StringUtil.isNotEmpty(HTMLSpirit.removeHtmlTag(
                Unicode2String.decodeUnicode(symptomDetailResponse.summarize)))){
            // 症状标题
            symptomTitleTv.setText(symptomDetailResponse.namecn);
            // 症状内容
            symptomContentTv.setText(HTMLSpirit.removeHtmlTag(
                    Unicode2String.decodeUnicode(symptomDetailResponse.summarize)));
        }
        if (StringUtil.isNotEmpty(HTMLSpirit.removeHtmlTag(
                Unicode2String.decodeUnicode(symptomDetailResponse.pathogeny)))){
            // 病因内容
            pathogenyContentTv.setText(HTMLSpirit.removeHtmlTag(
                    Unicode2String.decodeUnicode(symptomDetailResponse.pathogeny)));
        }
        if (StringUtil.isNotEmpty(HTMLSpirit.removeHtmlTag(
                Unicode2String.decodeUnicode(symptomDetailResponse.diagnoses)))){
            // 检查内容
            checkoutContentTv.setText(HTMLSpirit.removeHtmlTag(
                    Unicode2String.decodeUnicode(symptomDetailResponse.diagnoses)));
        }
        if (StringUtil.isNotEmpty(HTMLSpirit.removeHtmlTag(
                Unicode2String.decodeUnicode(symptomDetailResponse.differential)))){
            // 诊断内容
            diagnoseContentTv.setText(HTMLSpirit.removeHtmlTag(
                    Unicode2String.decodeUnicode(symptomDetailResponse.differential)));
        }
        if (StringUtil.isNotEmpty(HTMLSpirit.removeHtmlTag(
                Unicode2String.decodeUnicode(symptomDetailResponse.prevent)))){
            // 预防内容
            preventContentTv.setText(HTMLSpirit.removeHtmlTag(
                    Unicode2String.decodeUnicode(symptomDetailResponse.prevent)));
        }
        if (StringUtil.isNotEmpty(HTMLSpirit.removeHtmlTag(
                Unicode2String.decodeUnicode(symptomDetailResponse.foodtreat)))){
            // 食疗内容
            foodTreatContentTv.setText(HTMLSpirit.removeHtmlTag(
                    Unicode2String.decodeUnicode(symptomDetailResponse.foodtreat)));
        }

        // 设置高度和宽度
        savesymtomDetailScvHeight();
        saveSymptomDetailHorizontalWidth();
    }

    /**
     * 获取意图传值
     * @author leibing
     * @createTime 2016/10/29
     * @lastModify 2016/10/29
     * @param
     * @return
     */
    private void getIntentData() {
        // 获取意图传值
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            // 症状id
            id = bundle.getString(SYMPTOM_ID, "");
        }
    }

    /**
     * 初始化症状详情顶部tab
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param
     * @return
     */
    private void initSymptomDetailHorizontalTab() {
        // 获取顶部滑动item名称
        String[] array = getResources().getStringArray(
                R.array.symptom_detail_navigation_name);
        // 初始化监听
        OnSymtomHorizontalScClickedListener listener = new OnSymtomHorizontalScClickedListener();
        for (int i = 0; i < array.length; i++) {
            // 获取子view
            View childView = LayoutInflater.from(SymptomDetailActivity.this).inflate(
                    R.layout.check_details_single_navigation_model, null);
            // 实例化子view控件
            TextView titleTv = (TextView)
                    childView.findViewById(R.id.check_details_navigation_textView);
            // 给子view控件初始化值
            titleTv.setText(array[i]);
            // 子view设置tag
            childView.setTag(i);
            // 子view设置监听
            childView.setOnClickListener(listener);
            // 子view添加到父容器
            symptomDetailContainerLy.addView(childView);
        }
        // 默认为选中第一个
        getNavigationImageView(0).setVisibility(View.VISIBLE);
        getNavigationTextView(0).setTextColor(
                getResources().getColor(R.color.title_color));
    }

    /**
     * 设置症状详情顶部tab位置
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param view
     * @param fromClick 是否点击
     * @param x x坐标
     * @param y y坐标
     * @return
     */
    public  void setSymtomHorizontalCurrentPostion(View view, boolean fromClick, int x, int y){
        // 如果不是点击事件并横向滑动控件不为空则滑动到指定坐标
        if (!fromClick && symptomDetailHscv != null) {
            symptomDetailHscv.scrollTo(x, y);
        }
        if (view == null){
            return;
        }
        if (view.getTag() == null)
            return;
        // 获取当前位置
        int position = (Integer) view.getTag();
        // 如果当前位置非上次位置
        if (lastPosition != position){
            // 如果顶部tab动态加载容器为空，则重新实例化
            if (symptomDetailContainerLy == null) {
                // 顶部tab动态加载容器
                symptomDetailContainerLy = (LinearLayout) findViewById(R.id.ly_symptom_detail_container);
            }
            // 设置上次位置蓝色下滑线不可见
            getNavigationImageView(lastPosition).setVisibility(View.INVISIBLE);
            // 设置上次位置字体颜色为黑色
            getNavigationTextView(lastPosition).setTextColor(
                    getResources().getColor(R.color.text_color_black));
            // 设置当前位置蓝色下划线可见
            getNavigationImageView(position).setVisibility(View.VISIBLE);
            // 设置当前位置字体颜色为蓝色
            getNavigationTextView(position).setTextColor(
                    getResources().getColor(R.color.title_color));
        }
        lastPosition = position;
        if (symptomDetailScv != null && fromClick) {
            symptomDetailScv.scrollTo(0, symtomDetailScvHeight[position]);
        }
    }

    /**
     * 保存症状详情顶部tab横向宽度坐标
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param
     * @return
     */
    private void saveSymptomDetailHorizontalWidth() {
        int[] location = new int[2];
        for (int i = 0; i < symptomDetailContainerLy.getChildCount(); i++) {
            getSingleNavigation(i).getLocationInWindow(location);
            symptomDetailHorizontalWidth[i] = location[0];
        }
    }

    /**
     * 保存症状详情滑动高度坐标
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param
     * @return
     */
    private void savesymtomDetailScvHeight() {
            // 症状详情
            final int[] symptomModuleLoc = new int[2];
            final int[] symptomLocation = new int[2];
            // 症状模块
            symptomModuleLy.post(new Runnable() {
                @Override
                public void run() {
                    symptomModuleLy.getLocationInWindow(symptomModuleLoc);
                    symtomDetailScvHeight[0] = 0;
                }
            });
            // 病因模块
            pathogenyModuleLy.post(new Runnable() {
                @Override
                public void run() {
                    pathogenyModuleLy.getLocationInWindow(symptomLocation);
                    symtomDetailScvHeight[1] = symptomLocation[1] - symptomModuleLoc[1];
                }
            });
            // 检查模块
            checkoutModuleLy.post(new Runnable() {
                @Override
                public void run() {
                    checkoutModuleLy.getLocationInWindow(symptomLocation);
                    symtomDetailScvHeight[2] = symptomLocation[1] - symptomModuleLoc[1];
                }
            });
            // 诊断模块
            diagnoseModuleLy.post(new Runnable() {
                @Override
                public void run() {
                    diagnoseModuleLy.getLocationInWindow(symptomLocation);
                    symtomDetailScvHeight[3] = symptomLocation[1] -symptomModuleLoc[1];
                }
            });
            // 预防模块
            preventModuleLy.post(new Runnable() {
                @Override
                public void run() {
                    preventModuleLy.getLocationInWindow(symptomLocation);
                    symtomDetailScvHeight[4] = symptomLocation[1] - symptomModuleLoc[1];
                }
            });
            foodTreatModuleLy.post(new Runnable() {
                @Override
                public void run() {
                    // 食疗模块
                    foodTreatModuleLy.getLocationInWindow(symptomLocation);
                    symtomDetailScvHeight[5] = symptomLocation[1] - symptomModuleLoc[1];
                }
            });
    }

    /**
     * 症状详情顶部tab横向监听
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param
     * @return
     */
    class OnSymtomHorizontalScClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            isTopClick = true;
            setSymtomHorizontalCurrentPostion(view, true, 0, 0);
        }
    }

    /**
     * 根据索引获取顶部滑动栏TextView实例
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param index 索引值
     * @return
     */
    private TextView getNavigationTextView(int index) {
        if (symptomDetailContainerLy == null)
            return null;
        return (TextView) symptomDetailContainerLy.getChildAt(index).findViewById(
                R.id.check_details_navigation_textView);
    }

    /**
     * 根据索引获取顶部滑动栏ImageView实例
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param index 索引值
     * @return
     */
    private ImageView getNavigationImageView(int index) {
        if (symptomDetailContainerLy == null)
            return null;
        return (ImageView) symptomDetailContainerLy.getChildAt(index).findViewById(
                R.id.check_details_navigation_imageView);
    }

    /**
     * 根据索引获取顶部滑动栏View实例
     * @author leibing
     * @createTime 2016/10/19
     * @lastModify 2016/10/19
     * @param index 索引值
     * @return
     */
    private View getSingleNavigation(int index) {
        if (symptomDetailContainerLy == null)
            return null;
        return symptomDetailContainerLy.getChildAt(index);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_actionbar_back:
                // 退出当前页面
                this.finish();
                break;
            default:
                break;
        }
    }
}
