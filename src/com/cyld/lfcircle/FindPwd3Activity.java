package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cyld.lfcircle.utils.MD5;
import com.cyld.lfcircle.utils.PrefUtils;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FindPwd3Activity extends Activity {

	private ImageView ivHoutui;
	private String validatecode;
	private String findphone;
	private Button btnSaveset;
	private EditText etInputpassword;
	private EditText etInputpassword2;
	private String md5password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_findpassword3);
		init();
		initView();
		initListener();
	}

	private void init() {
		Intent intent = getIntent();
		validatecode = intent.getStringExtra("validatecode");
		findphone = intent.getStringExtra("findphone");
	}

	private void initView() {
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		btnSaveset = (Button) findViewById(R.id.btn_saveset);
		etInputpassword = (EditText) findViewById(R.id.et_inputpassword);
		etInputpassword2 = (EditText) findViewById(R.id.et_inputpassword2);
	}

	private void initListener() {

		// 监听后退
		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FindPwd3Activity.this.finish();
			}
		});

		// 监听保存设置
		btnSaveset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etInputpassword.getText().toString()
						.equals(etInputpassword2.getText().toString())) {
					new Thread() {
						@Override
						public void run() {
							super.run();
							linkServer();
						}
					}.start();
				} else {
					etInputpassword.setText("");
					etInputpassword2.setText("");
					Toast.makeText(getApplicationContext(),
							"两次输入的密码不一致,请重新输入！", 0).show();
				}
			}
		});
	}

	// 请求服务器
	private void linkServer() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();

		md5password = MD5.encode(etInputpassword.getText().toString());
		try {
			json1.put("phone", findphone);
			json1.put("password", md5password);
			json1.put("validatecode", validatecode);
			json.put("data", json1);
			json.put("requestCode", "001005");
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
					// 1,注册成功0,注册失败 -1,手机格式错误
					// -2,手机已注册 -3,验证码错误
					switch (Integer.parseInt(js.getString("responseCode"))) {
					case 0:
						Toast.makeText(getApplicationContext(), "修改失败！",
								Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(),
								FindPwd1Activity.class));
						break;
					case 1:
						Toast.makeText(getApplicationContext(), "恭喜您：密码修改成功！",
								Toast.LENGTH_SHORT).show();

						// 保存MD5密码在本地
						PrefUtils.setString(getApplicationContext(),
								"password", md5password);
						startActivity(new Intent(getApplicationContext(),
								LoginActivity.class));
						FindPwd3Activity.this.finish();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "手机格式错误",
								Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(),
								FindPwd1Activity.class));
						break;
					case -2:
						Toast.makeText(getApplicationContext(), "手机号未注册",
								Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(),
								FindPwd1Activity.class));
						break;
					case -3:
						Toast.makeText(getApplicationContext(), "验证码错误",
								Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(),
								FindPwd1Activity.class));
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
