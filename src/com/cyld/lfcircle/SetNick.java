package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SetNick extends Activity {
	private TextView tvTitle;
	private ImageView ivYonghu;
	private ImageView ivHoutui;
	private Button btnSaveset;
	private EditText etText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setnick);
		setView();
		setListener();
	}

	private void setListener() {
		ivHoutui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnSaveset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("nick", etText.getText().toString());
				SetNick.this.setResult(1, intent);

//				new Thread() {
//					public void run() {
//						linkServer();
//					};
//				}.start();

				SetNick.this.finish();
			}
		});
	}

	private void setView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivYonghu = (ImageView) findViewById(R.id.iv_yonghu);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		btnSaveset = (Button) findViewById(R.id.btn_saveset);
		etText = (EditText) findViewById(R.id.et_text);

		ivYonghu.setVisibility(View.INVISIBLE);
		tvTitle.setText("昵称设置");
	}

	public void linkServer() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		
		String userid = PrefUtils.getString(getApplicationContext(), "userid", "001");
		String password = PrefUtils.getString(getApplicationContext(), "password", "123456");
		
		try {
			json1.put("userid", userid);
			json1.put("password", password);
			json1.put("usernickname", etText.getText().toString());
			json1.put("usersex", "1");
			json1.put("userbirthday", "1991-08-14");
			json1.put("userabstract", "个人简介");
			json1.put("address", "山东省  青岛市");
			// for (String key : pa.keySet()) {
			// json.put(key,pa.get(key));
			// }
			json.put("data", json1);
			json.put("requestCode", "001007");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountServices.pcjoy.cn/app.ashx";
		callService(url, json);
	}

	private void callService(String url, JSONObject json) {

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
//				1,设置成功0,设置失败
//				-1,用户未注册 -2昵称已被使用
				String json = responseInfo.result;
				try {
					JSONObject js = new JSONObject(json);
//					if (js.getString("responseCode").equals("1")) {
//						
//						Log.e("abc.......", "成功");
//						Log.e("abc:::::::", js.getString("Message"));
//					}
					switch (Integer.parseInt(js.getString("responseCode"))) {
					case 1:
						PrefUtils.setString(getApplicationContext(), "usernickname", etText.getText().toString());
						Toast.makeText(getApplicationContext(), js.getString("message"), 0).show();
						break;
					case 0:
						Toast.makeText(getApplicationContext(), "修改失败", 0).show();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "用户不存在", 0).show();
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg + "解析失败1111111111111111");
			}
		});
	}

}
