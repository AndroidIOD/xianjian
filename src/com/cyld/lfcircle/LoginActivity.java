package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;
import android.widget.Toast;

import com.cyld.lfcircle.domain.LoginBean;
import com.cyld.lfcircle.utils.MD5;
import com.cyld.lfcircle.utils.PrefUtils;
import com.cyld.lfcircle.utils.Utils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 用户登陆页
 * 
 * @author LiuBin
 * 
 */
public class LoginActivity extends Activity {
	private TextView tvZhuce;
	private ImageView ivHoutui;
	private EditText etInputusername;
	private EditText etInputpassword;
	private Button btnLogin;

	public LoginBean lbean = new LoginBean();
	private TextView tvForgetpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		setView();
		setListener();
	}
	
	private void setView() {
		tvZhuce = (TextView) findViewById(R.id.tv_zhuce);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		tvForgetpwd = (TextView) findViewById(R.id.tv_forgetpwd); // 忘记密码

		etInputusername = (EditText) findViewById(R.id.et_inputusername);// 用户名
		etInputpassword = (EditText) findViewById(R.id.et_inputpassword);// 密码
		btnLogin = (Button) findViewById(R.id.btn_login);// 登陆按钮

	}

	private void setListener() {

		// 监听注册
		tvZhuce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,
						Register1Activity.class));
			}
		});

		// 监听后退
		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});

		// 监听登陆
		btnLogin.setOnClickListener(new OnClickListener() {
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

		// 忘记密码
		tvForgetpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,FindPwd1Activity.class));
			}
		});
	}

	// 请求服务器
	private void linkServer() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		try {
			json1.put("phone", etInputusername.getText().toString());
			json1.put("password",
					MD5.encode(etInputpassword.getText().toString()));
			json.put("data", json1);
			json.put("requestCode", "001004");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountappservice.pcjoy.cn/app.ashx";
		parseJson(url, json);

	}

	// 解析JSON
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

				try {
					String js = responseInfo.result;
					JSONObject jb = new JSONObject(js);

					switch (Integer.parseInt(jb.getString("responseCode"))) {
					case 1:
						PrefUtils.setBoolean(getApplicationContext(),
								"islogin", true);
						Gson gson = new Gson();
						Intent intent = new Intent();
						lbean = gson.fromJson(js, LoginBean.class);
						intent.putExtra("LoginBean", lbean);
						PrefUtils.setString(getApplicationContext(), "userid",
								lbean.data.user.Userid);
						
						String md5password = MD5.encode(etInputpassword.getText().toString());
						PrefUtils.setString(getApplicationContext(), "password",
								etInputpassword.getText().toString());
						PrefUtils
								.setString(getApplicationContext(), "md5password", md5password);
						
						Utils.showToast(getApplicationContext(), "登陆成功！");
						Log.e("usersex::", lbean.data.user.Userid);

						LoginActivity.this.setResult(1, intent);
						LoginActivity.this.finish();
						break;

					case 0:
						Toast.makeText(getApplicationContext(), "登陆失败！", 1)
								.show();
						startActivity(new Intent(getApplicationContext(),
								Register1Activity.class));
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "手机/邮箱格式错误！", 1)
								.show();
						startActivity(new Intent(getApplicationContext(),
								Register1Activity.class));
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
