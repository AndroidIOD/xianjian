package com.cyld.lfcircle.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 页签详情页
 * 
 * @author Kevin
 * 
 */
public class TabDetailPager extends BaseMenuDetailPager {

	public TabDetailPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	private TextView tvText;

	

	@Override
	public View initViews() {
		tvText = new TextView(mActivity);
		tvText.setText("页签详情页");
		tvText.setTextColor(Color.RED);
		tvText.setTextSize(25);
		tvText.setGravity(Gravity.CENTER);
		return tvText;
	}

	@Override
	public void initData() {
	}

}
