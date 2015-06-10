package com.cyld.lfcircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class ReportActivity extends Activity implements OnClickListener{
	
	private ImageButton ib_report_arrows_left;//返回的按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);
		initView();
	}

	private void initView() {
		ib_report_arrows_left = (ImageButton) findViewById(R.id.ib_report_arrows_left);
		
		ib_report_arrows_left.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_report_arrows_left:
			finish();
			break;

		default:
			break;
		}
	}

}
