package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.nightweaver.view.RoundImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyld.lfcircle.domain.PersonMainBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PersonalMain extends Activity {
	private TextView tvEditdata;
	private ImageView ivHoutui;
	private ImageView ivFengmian;
	private TextView tvPersonmainnick;
	private RoundImageView ivPersonmaintouxiang;
	private TextView tvAge;
	private TextView tvPersonmainarea;
	private TextView tvPersonmainlevel;
	private ImageView ivSexman;
	private ImageView ivSexwoman;
	private TextView tvHyh;
	private ImageView ivCrjh;
	private ImageView ivNscm;
	private ImageView ivYfhj;
	private ImageView ivJhwm;
	private ImageView ivRlge;
	private ImageView ivYhby;
	private BitmapUtils butils;
	public PersonMainBean pmbean = new PersonMainBean();
	private TextView tvXhshu;
	private TextView tvZdshu;
	private TextView tvPersonmainsign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personalmain);
		butils = new BitmapUtils(this);
		setView();
		setListener();
		// 解析网络数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				linkServer();
			}
		}).start();
	}

	// 请求服务器
	private void linkServer() {

		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		//读出用户ID
		//String userid = PrefUtils.getString(getApplicationContext(), "userid", "1");
		try {
			json1.put("Usr_id", "1");
			json.put("requestCode", "001013");
			json.put("data", json1);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountservice.pcjoy.cn/app.ashx";
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

					Gson gson = new Gson();
					pmbean = gson.fromJson(js, PersonMainBean.class);
					
					butils.display(ivFengmian, pmbean.Usr_bgimg); //封面
					tvXhshu.setText(pmbean.Usr_Proped.get(0).get(1).Up_count);//鲜花数
					tvZdshu.setText(pmbean.Usr_Proped.get(0).get(0).Up_count);//炸弹数
					
					//设置性别
					switch (Integer.parseInt(pmbean.Usr_sex)) {
					case 0:
						ivSexman.setVisibility(View.VISIBLE);
						ivSexwoman.setVisibility(View.INVISIBLE);
						break;
					case 1:
						ivSexman.setVisibility(View.INVISIBLE);
						ivSexwoman.setVisibility(View.VISIBLE);
						break;
					case 2:
						ivSexman.setVisibility(View.GONE);
						ivSexwoman.setVisibility(View.GONE);
						break;
					}
					
					//设置昵称
					tvPersonmainnick.setText(pmbean.Usr_nickname);
					
					//个人主页头像
					butils.display(ivPersonmaintouxiang, pmbean.Usr_head);
					
					//年龄
					tvAge.setText(pmbean.Usr_age+"岁");
					
					//等级
					tvPersonmainlevel.setText("LV."+pmbean.Usr_level);
					
					//后援会
					tvHyh.setText(pmbean.Usr_group);
					
					//地址
					tvPersonmainarea.setText(pmbean.Usr_address);
					
					//个性签名
					tvPersonmainsign.setText(pmbean.Usr_abstract);
					
					//成就
					for(int i = 0;i<pmbean.Usr_Glory.get(0).size();i++){
						
						switch (i) {
						case 0:
							butils.display(ivCrjh, pmbean.Usr_Glory.get(0).get(i).G_img);
							break;
						case 1:
							butils.display(ivNscm, pmbean.Usr_Glory.get(0).get(i).G_img);
							break;
						case 2:
							butils.display(ivYfhj, pmbean.Usr_Glory.get(0).get(i).G_img);
							break;
						case 3:
							butils.display(ivJhwm, pmbean.Usr_Glory.get(0).get(i).G_img);
							break;
						case 4:
							butils.display(ivRlge, pmbean.Usr_Glory.get(0).get(i).G_img);
							break;
						case 5:
							butils.display(ivYhby, pmbean.Usr_Glory.get(0).get(i).G_img);
							break;
						}
					}
				} catch (Exception e) {
					Log.e("eeeee", e.getMessage().toString());
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg + "+解析失败");
			}
		});

	}

	private void setListener() {

		// 编辑资料
		tvEditdata.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonalMain.this, EditActivity.class));
			}
		});

		// 后退
		ivHoutui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PersonalMain.this.finish();
			}
		});

		// 封面
		ivFengmian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PersonalMain.this,
						ChangeBackimage.class));
			}
		});
	}

	private void setView() {

		tvEditdata = (TextView) findViewById(R.id.tv_editdata);// 编辑
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);// 后退
		ivFengmian = (ImageView) findViewById(R.id.iv_fengmian);// 封面
		tvPersonmainnick = (TextView) findViewById(R.id.tv_personmainnick);// 昵称
		ivPersonmaintouxiang = (RoundImageView) findViewById(R.id.iv_personmaintouxiang);// 个人主页头像
		tvAge = (TextView) findViewById(R.id.tv_age);// 年龄
		tvPersonmainarea = (TextView) findViewById(R.id.tv_personmainarea);// 地区
		tvPersonmainlevel = (TextView) findViewById(R.id.tv_personmainlevel);// 等级
		ivSexman = (ImageView) findViewById(R.id.iv_sexman);// 性别：男
		ivSexwoman = (ImageView) findViewById(R.id.iv_sexwoman);// 性别：女
		tvHyh = (TextView) findViewById(R.id.tv_hyh);// 后援会
		tvXhshu = (TextView) findViewById(R.id.tv_xhshu);//鲜花数
		tvZdshu = (TextView) findViewById(R.id.tv_zdshu);//炸弹数
		tvPersonmainsign = (TextView) findViewById(R.id.tv_personmainsign);//个性签名
		
		// 下面是6个成就的ID
		ivCrjh = (ImageView) findViewById(R.id.iv_crjh);
		ivNscm = (ImageView) findViewById(R.id.iv_nscm);
		ivYfhj = (ImageView) findViewById(R.id.iv_yfhj);
		ivJhwm = (ImageView) findViewById(R.id.iv_jhwm);
		ivRlge = (ImageView) findViewById(R.id.iv_rlge);
		ivYhby = (ImageView) findViewById(R.id.iv_yhby);

	}
}
