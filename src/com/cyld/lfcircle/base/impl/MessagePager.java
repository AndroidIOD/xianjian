package com.cyld.lfcircle.base.impl;

import android.app.Activity;
import com.cyld.lfcircle.base.BasePager;

/**
 * 消息页面
 * 
 * @author Kevin
 * 
 */
public class MessagePager extends BasePager {


	public MessagePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
//		tvTitle.setText("消息");// 修改标题
//		setSlidingMenuEnable(true);//打开侧边栏
//
//		TextView text = new TextView(mActivity);
//		text.setText("消息页面");
//		text.setTextColor(Color.RED);
//		text.setTextSize(25);
//		text.setGravity(Gravity.CENTER);
//
//		// 向FrameLayout中动态添加布局
//		flContent.addView(text);
//
//		//getDataFromServer();
	}

	/**
	 * 从服务器获取数据
	 */
	private void getDataFromServer() {
		
	}

	/**
	 * 解析网络数据
	 * 
	 * @param result
	 */
	protected void parseData(String result) {
		
	}

}
