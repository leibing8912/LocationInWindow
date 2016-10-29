package cn.jianke.getlocationinwindowdemo.module.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import cn.jianke.getlocationinwindowdemo.R;
/**
 * @className: LoadingDialog
 * @classDescription: 自定义加载对话框
 * @author: leibing
 * @createTime: 2016/10/29
 */
public class LoadingDialog {
	/**
	 * 创建自定义对话框
	 * @author leibing
	 * @createTime 2016/10/29
	 * @lastModify 2016/10/29
	 * @param context 上下文
	 * @return
	 */
	public static  Dialog createLoadingDialog(final Context context){
		// 实例化对话框并指定样式
		Dialog loading = new Dialog(context, R.style.loadingDialog);
		// 设置对话框可取消
		loading.setCancelable(true);
		// 指定对话框布局
		loading.setContentView(LayoutInflater.from(context).inflate(R.layout.fullscreen_loading_indicator, null), 
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		return loading;
	}
}
