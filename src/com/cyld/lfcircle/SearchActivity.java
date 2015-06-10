package com.cyld.lfcircle;
import com.cyld.lfcircle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchActivity extends Activity implements OnClickListener{
	private Button serach_cancle;//搜索页面的取消

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serach);
		initView();
	}

	private void initView() {
		serach_cancle = (Button) findViewById(R.id.serach_cancle);
		serach_cancle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.serach_cancle:
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			break;

		default:
			break;
		}
	}
}
