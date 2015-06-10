package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Register2Activity extends Activity {
	private TextView tvSendedphone;
	private String registerphone;
	private ImageView ivHoutui;
	private Button btnNextbtn;
	private TextView tvResendbtn; //重新发送按钮
	private EditText etYanzhengma; //输入验证码的edittext
	private TimeCount time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register2);

		init();
		setView();
		setListener();
		
		//定义总共的时长和时间间隔
		time = new TimeCount(59000, 1000);
		time.start();// 开始计时

	}

	private void setListener() {

		// 监听后退
		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Register2Activity.this.finish();
			}
		});

		// 监听下一步
		btnNextbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				intent.setClass(Register2Activity.this, Register3Activity.class);  
				intent.putExtra("validatecode", etYanzhengma.getText().toString());
				intent.putExtra("registerphone", registerphone);
				startActivity(intent);
			}
		});

		// 监听重新发送
		tvResendbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread() {
					@Override
					public void run() {
						super.run();
						linkServer();
					}
				}.start();
			}
		});
	}

	private void init() {
		Intent intent = getIntent();
		registerphone = intent.getStringExtra("registerphone");
	}

	private void setView() {
		findViewById(R.id.tv_next).setVisibility(View.INVISIBLE);
		tvSendedphone = (TextView) findViewById(R.id.tv_sendedphone);
		tvSendedphone.setText(registerphone);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		btnNextbtn = (Button) findViewById(R.id.btn_nextbtn);
		tvResendbtn = (TextView) findViewById(R.id.tv_resendbtn);
		etYanzhengma = (EditText) findViewById(R.id.et_yanzhengma2);
	}

	// 请求服务器
	private void linkServer() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		try {
			json1.put("phone", registerphone);
			json.put("data", json1);
			json.put("requestCode", "001001");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountappservice.pcjoy.cn/app.ashx";
		parseJson(url, json);
	}

	private void parseJson(String url, JSONObject json) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();

		try {
			params.setBodyEntity(new StringEntity(json.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json = responseInfo.result;
				// {"responseCode":"1","message":"手机验证码发送成功"}
				try {
					JSONObject js = new JSONObject(json);
					if (js.getString("responseCode").equals("1")) {
						// 如果请求成功  set到text上
						tvSendedphone.setText(registerphone);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg + "+解析失败");
			}
		});
	}
	
	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			tvResendbtn.setText("重新发送");
			tvResendbtn.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			tvResendbtn.setClickable(false);
			tvResendbtn.setText(millisUntilFinished / 1000 + "秒");
		}
	}

}
