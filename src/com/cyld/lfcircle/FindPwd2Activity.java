package com.cyld.lfcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FindPwd2Activity extends Activity {

	private ImageView ivHoutui;
	private Button btnNextbtn;
	private TimeCount time;
	private Button btnResendbtn;
	private EditText etFindyanzhengma;
	private String findphone;
	private TextView tvSendedphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_findpassword2);
		init();
		initView();
		initListener();

		// 定义总共的时长和时间间隔
		time = new TimeCount(59000, 1000);
		time.start();// 开始计时
	}

	private void initListener() {

		// 监听后退
		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FindPwd2Activity.this.finish();
			}
		});

		// 监听下一步
		btnNextbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(FindPwd2Activity.this, FindPwd3Activity.class);
				intent.putExtra("validatecode", etFindyanzhengma.getText()
						.toString());
				intent.putExtra("findphone", findphone);
				startActivity(intent);
				FindPwd2Activity.this.finish();
			}
		});
	}
	
	private void init() {
		Intent intent = getIntent();
		findphone = intent.getStringExtra("findphone");
	}


	private void initView() {
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		btnNextbtn = (Button) findViewById(R.id.btn_nextbtn);
		
		tvSendedphone = (TextView) findViewById(R.id.tv_sendedphone);
		tvSendedphone.setText(findphone);

		btnResendbtn = (Button) findViewById(R.id.btn_resendbtn);
		etFindyanzhengma = (EditText) findViewById(R.id.et_findyanzhengma);
	}

	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btnResendbtn.setText("重新发送");
			btnResendbtn.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btnResendbtn.setClickable(false);
			btnResendbtn.setText(millisUntilFinished / 1000 + "秒");
		}
	}

}
