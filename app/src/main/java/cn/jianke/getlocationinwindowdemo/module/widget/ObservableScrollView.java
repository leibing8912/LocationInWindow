package cn.jianke.getlocationinwindowdemo.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @className:ObservableScrollView
 * @classDescription: 自定滑动view，用于监听滑动事件
 * @author: leibing
 * @createTime: 2016/10/29
 */
public class ObservableScrollView extends ScrollView {
	// 滑动监听
	private ScrollViewListener scrollViewListener;
	// ScrollView正在向上滑动
	public static final int SCROLL_UP = 0x01;
	// ScrollView正在向下滑动
	public static final int SCROLL_DOWN = 0x10;

	public ObservableScrollView(Context context) {
		super(context);
	}

	public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置滑动监听
	 * @author leibing
	 * @createTime 2016/10/29
	 * @lastModify 2016/10/29
	 * @param scrollViewListener 滑动监听
	 * @return
	 */
	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			if (oldy > y) {
				// 向下滑动
				scrollViewListener.onScrollChanged(this, x, y, oldx, oldy,SCROLL_DOWN);
			} else if (oldy < y) {
				// 向上滑动
				scrollViewListener.onScrollChanged(this, x, y, oldx, oldy,SCROLL_UP);
			}
		}
	}

	/**
	 * @interfaceName: ScrollViewListener
	 * @interfaceDescription: 滑动监听
	 * @author: leibing
	 * @createTime: 2016/10/29
	 */
	public interface ScrollViewListener {
		void onScrollChanged(ObservableScrollView scrollView, int x, int y,
							 int oldx, int oldy, int oritention);
	}
}