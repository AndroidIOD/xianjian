package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.nightweaver.view.RoundImageView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cyld.lfcircle.utils.PrefUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.summerxia.dateselector.widget.LocationSelectorDialogBuilder;
import com.summerxia.dateselector.widget.LocationSelectorDialogBuilder.OnSaveLocationLister;

public class EditActivity extends Activity implements OnClickListener,
		OnSaveLocationLister {
	// private DatePicker dpShengri;
	private DatePickerDialog.OnDateSetListener dateListener;
	private TextView tvMan;
	private TextView tvWoman;
	private TextView tvBaomi;
	private TextView tvCancel;
	private TextView tvXingbie;
	private PopupWindow pop;
	private RoundImageView ivTouxiang;

	private LocationSelectorDialogBuilder locationBuilder;
	private TextView tvDiqu; // 地区textview
	private TextView tvNicheng; // 昵称Text
	private TextView tvShengri;
	private String sex = "2";
	private EditText etSign;
	private Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit);

		setView();
	}

	private void setView() {
		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		ImageView ivYonghu = (ImageView) findViewById(R.id.iv_yonghu);
		LinearLayout llShengri = (LinearLayout) findViewById(R.id.ll_shengri);
		ImageView ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		LinearLayout llSex = (LinearLayout) findViewById(R.id.ll_sex);
		tvXingbie = (TextView) findViewById(R.id.tv_xingbie);
		ivTouxiang = (RoundImageView) findViewById(R.id.iv_touxiang);
		LinearLayout llArea = (LinearLayout) findViewById(R.id.ll_area); // 地区
		tvDiqu = (TextView) findViewById(R.id.tv_diqu);
		LinearLayout llNick = (LinearLayout) findViewById(R.id.ll_nick);
		tvNicheng = (TextView) findViewById(R.id.tv_nicheng);
		LinearLayout llTitleImage = (LinearLayout) findViewById(R.id.ll_titleimage);
		LinearLayout llSign = (LinearLayout) findViewById(R.id.ll_sign); // 个性签名
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		etSign = (EditText) findViewById(R.id.et_sign);

		llArea.setOnClickListener(this);
		llNick.setOnClickListener(this);

		// 提交数据
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				linkServer();
			}

		});

		// 更换头像监听
		llTitleImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(EditActivity.this, ChangeImage.class));
			}
		});

		llSex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditActivity.this.showPopwindow();
			}

		});

		tvTitle.setText("资料修改 ");
		ivYonghu.setVisibility(View.INVISIBLE);
		llShengri.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// dpShengri.setVisibility(View.VISIBLE);
				// 日期选择器对话框
				Calendar calendar = Calendar.getInstance();
				DatePickerDialog dialog = new DatePickerDialog(
						EditActivity.this, dateListener, calendar
								.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}

		});

		// 日期选择器对话框监听
		dateListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth) {
				tvShengri = (TextView) findViewById(R.id.tv_shengri);
				// Calendar月份是从0开始,所以month要加1
				tvShengri.setText(year + "-" + (month + 1) + "-" + dayOfMonth
						);
				PrefUtils.setString(getApplicationContext(), "userbirthday",
						tvShengri.getText().toString());
			}
		};

		// 后退监听
		ivHoutui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditActivity.this.finish();
			}
		});
	}

	private void linkServer() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		try {
			// json.put("code", "10008");
			// json.put("userId", "1");
			// json.put("T_ID", "1");
			// json.put("page", "1");
			// json.put("pageCount", "20");
			// json.put("ordinal", "1");
			// json1.put("Usr_id", "1");
			json1.put("userid",
					PrefUtils.getString(getApplicationContext(), "userid", "1"));
			json1.put("password", PrefUtils.getString(getApplicationContext(),
					"md5password", "1"));
			json1.put("usernickname", tvNicheng.getText().toString());
			if (tvXingbie.getText().toString().equals("男")) {
				sex = "0";
			} else if (tvXingbie.getText().toString().equals("女")) {
				sex = "1";
			} else {
				sex = "2";
			}
			json1.put("usersex", sex);
			json1.put("userbirthday", tvShengri.getText().toString());
			json1.put("userabstract", etSign.getText().toString());
			json1.put("address", tvDiqu.getText().toString());

			json.put("requestCode", "001007");
			json.put("data", json1);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountappservice.pcjoy.cn/app.ashx";
		callService(url, json);

	}

	private void callService(String url, JSONObject json) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();

		Log.e("aaaaaaaaaaa", json.toString());
		try {
			params.setBodyEntity(new StringEntity(json.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json = responseInfo.result;

				Log.e("success:::::::::::::::", json);
				// TopicCollectTest tct = new TopicCollectTest();
				// Gson gson = new Gson();
				// tct = gson.fromJson(json, TopicCollectTest.class);
				//
				// String uu = tct.list.get(0).user.Userhead;
				// Log.e("....................", uu);

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
			}
		});
	}

	public void showPopwindow() {

		// 一个自定义的布局，作为显示的内容
		View v = LayoutInflater.from(this).inflate(R.layout.pop_list, null);
		tvMan = (TextView) v.findViewById(R.id.tv_man);
		tvWoman = (TextView) v.findViewById(R.id.tv_woman);
		tvBaomi = (TextView) v.findViewById(R.id.tv_baomi);
		tvCancel = (TextView) v.findViewById(R.id.tv_cancel);

		pop = new PopupWindow(v, android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, true);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置好参数之后再show
		if (pop != null && pop.isShowing()) {
			pop.dismiss();
		}

		// 设置popWindow的显示和消失动画
		pop.setAnimationStyle(R.style.mypopwindow_anim_style);

		// 在底部显示
		pop.showAtLocation(EditActivity.this.findViewById(R.id.ll_sex),
				Gravity.BOTTOM, 0, 0);

		tvMan.setOnClickListener(this);
		tvWoman.setOnClickListener(this);
		tvBaomi.setOnClickListener(this);
		tvCancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_man:
			tvXingbie.setText("男");
			PrefUtils.setString(getApplicationContext(), "usersex", "0");
			pop.dismiss();
			break;

		case R.id.tv_woman:
			tvXingbie.setText("女");
			PrefUtils.setString(getApplicationContext(), "usersex", "1");
			pop.dismiss();
			break;
		case R.id.tv_baomi:
			tvXingbie.setText("保密");
			PrefUtils.setString(getApplicationContext(), "usersex", "2");
			pop.dismiss();
			break;
		case R.id.tv_cancel:
			pop.dismiss();
			break;
		case R.id.ll_area: // 地区选择器
			if (locationBuilder == null) {
				locationBuilder = LocationSelectorDialogBuilder
						.getInstance(this);
				locationBuilder.setOnSaveLocationLister(this);
			}
			locationBuilder.show();

			break;
		case R.id.ll_nick:
			startActivityForResult(
					new Intent(EditActivity.this, SetNick.class), 1);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			String nickname = data.getExtras().getString("nick");
			tvNicheng.setText(nickname);
			PrefUtils.setString(getApplicationContext(), "usernickname",
					nickname);
		}
	}

	@Override
	public void onSaveLocation(String location, String provinceId, String cityId) {
		tvDiqu.setText(location);
		PrefUtils.setString(getApplicationContext(), "address", location);
	}

}
