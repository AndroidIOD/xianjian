package com.cyld.lfcircle;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;

import com.cyld.lfcircle.fragment.DengwuHeadFragment;
import com.cyld.lfcircle.fragment.LianPuHeadFragment;
import com.cyld.lfcircle.fragment.ShengXiaoHeadFragment;
import com.cyld.lfcircle.fragment.XianJianHeadFragment;

/**
 * 更换头像页面
 * 
 * @author Administrator
 * 
 */
public class ChangeImage extends FragmentActivity {
	private ImageView ivHoutui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changeimage);
		FragmentTabHost fttabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		fttabhost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);

		TabSpec tabSpec1 = fttabhost.newTabSpec("xianjian");
		tabSpec1.setIndicator("仙剑");
		fttabhost.addTab(tabSpec1, XianJianHeadFragment.class, null);

		TabSpec tabSpec2 = fttabhost.newTabSpec("shengxiao");
		tabSpec2.setIndicator("生肖");
		fttabhost.addTab(tabSpec2, ShengXiaoHeadFragment.class, null);

		TabSpec tabSpec3 = fttabhost.newTabSpec("lianpu");
		tabSpec3.setIndicator("脸谱");
		fttabhost.addTab(tabSpec3, LianPuHeadFragment.class, null);

		TabSpec tabSpec4 = fttabhost.newTabSpec("dengwu");
		tabSpec4.setIndicator("动物");
		fttabhost.addTab(tabSpec4, DengwuHeadFragment.class, null);

		initView();
		initListener();
	}

	private void initListener() {
		ivHoutui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChangeImage.this.finish();
			}
		});
	}

	private void initView() {
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
	}
}
