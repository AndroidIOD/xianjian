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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FindPwd1Activity extends Activity {
	private ImageView ivHoutui;
	private Button btnNext;
	private EditText etFindphonepwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_findpassword1);
		initView();
		initListener();
	}

	private void initListener() {

		// 监听后退
		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FindPwd1Activity.this.finish();
			}
		});

		// 监听下一步按钮
		btnNext.setOnClickListener(new OnClickListener() {

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

	private void initView() {
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		btnNext = (Button) findViewById(R.id.btn_next);
		etFindphonepwd = (EditText) findViewById(R.id.et_findphonepwd);
	}

	// 请求服务器
	private void linkServer() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		try {
			json1.put("phone", etFindphonepwd.getText().toString());
			json.put("data", json1);
			json.put("requestCode", "001001");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountappservice.pcjoy.cn/app.ashx";
		parseJson(url, json);
	}

	// 解析服务器JSON
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
					switch (Integer.parseInt(js.getString("responseCode"))) {
					case 0:
						Toast.makeText(getApplicationContext(), "验证码发送失败！", 0)
								.show();
						break;
					case 1:
						// 请求成功 跳转到注册2
						Intent intent = new Intent();
						intent.setClass(FindPwd1Activity.this,
								FindPwd2Activity.class);
						intent.putExtra("findphone", etFindphonepwd
								.getText().toString());
						startActivity(intent);
						FindPwd1Activity.this.finish();//关闭当前页
						
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "手机格式错误！", 0)
								.show();
						break;

					default:
						break;
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

}
