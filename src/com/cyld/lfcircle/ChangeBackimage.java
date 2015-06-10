package com.cyld.lfcircle;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.cyld.lfcircle.fragment.DongWuFragment;
import com.cyld.lfcircle.fragment.GameFragment;
import com.cyld.lfcircle.fragment.LianPuFragment;
import com.cyld.lfcircle.fragment.ShengXiaoFragment;
import com.cyld.lfcircle.R;

public class ChangeBackimage extends FragmentActivity {

	private TextView tvTitle;
	private ImageView ivHoutui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changebackimage);

		initView();
		initListener();

		FragmentTabHost fTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		fTabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);

		// TabWidget tabWidget = fTabHost.getTabWidget();

		TabSpec tabSpec1 = fTabHost.newTabSpec("youxi");
		tabSpec1.setIndicator("游戏");
		fTabHost.addTab(tabSpec1, GameFragment.class, null);

		TabSpec tabSpec2 = fTabHost.newTabSpec("shengxiao");
		tabSpec2.setIndicator("生肖");
		fTabHost.addTab(tabSpec2, ShengXiaoFragment.class, null);

		TabSpec tabSpec3 = fTabHost.newTabSpec("lianpu");
		tabSpec3.setIndicator("脸谱");
		fTabHost.addTab(tabSpec3, LianPuFragment.class, null);

		TabSpec tabSpec4 = fTabHost.newTabSpec("dongwu");
		tabSpec4.setIndicator("动物");
		fTabHost.addTab(tabSpec4, DongWuFragment.class, null);

		fTabHost.setCurrentTab(0);
	}

	private void initListener() {
		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChangeBackimage.this.finish();
			}
		});
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);

		tvTitle.setText("更换封面");
	}

}
