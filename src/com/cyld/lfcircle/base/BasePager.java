package com.cyld.lfcircle.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.cyld.lfcircle.MainActivity;
import com.cyld.lfcircle.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 主页下5个子页面的基类
 * 
 * @author Kevin
 * 
 */
public class BasePager extends Activity {

	public Activity mActivity;
	public View mRootView;// 布局对象
//
//	public TextView tvTitle;// 标题对象

	public FrameLayout flContent;// 内容


	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}
	public BasePager() {
		initViews();
	}

	/**
	 * 初始化布局
	 */
	public void initViews() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
//
//		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
	}

	/**
	 * 切换SlidingMenu的状态
	 * 
	 * @param 
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();// 切换状态, 显示时隐藏, 隐藏时显示
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
    
		
		 
	}
	
 

	
	
	
	/**
	 * 设置侧边栏开启或关闭
	 * 
	 * @param enable
	 */
	public void setSlidingMenuEnable(boolean enable) {
		MainActivity mainUi = (MainActivity) mActivity;

		SlidingMenu slidingMenu = mainUi.getSlidingMenu();

		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

}
