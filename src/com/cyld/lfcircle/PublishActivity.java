package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpException;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cyld.lfcircle.utils.Utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PublishActivity extends Activity implements OnClickListener {
//	、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、这是关联、、、、、、、、、、、、、、、、、、、、、、、、、、、
	private LayoutInflater mInflater;
	private PopupWindow popupWindow;

	private GridView gridView;

	private ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
	
//	、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
	private ImageButton ib_publish_left_arrows;// 发表页面的取消按钮
	private ImageButton ib_add_pic;// 添加图片的按钮
	private ImageButton ib_tupian_publish;// 添加图片的按钮
	private ImageButton  ib_biaoqing_publish ;
	private EditText et_publish_theme;
	private EditText et_content_publish;
	private Button bt_publish;
	private Bitmap bitmap;
	private static final int PHOTO_REQUEST_GALLERY = 1; //相册
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 取消actionBar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publish);
		ib_publish_left_arrows = (ImageButton) findViewById(R.id.ib_publish_left_arrows);
		ib_tupian_publish = (ImageButton) findViewById(R.id.ib_tupian_publish);
		ib_biaoqing_publish = (ImageButton)findViewById(R.id.ib_biaoqing_publish);
		ib_add_pic = (ImageButton) findViewById(R.id.ib_add_pic);
		et_publish_theme = (EditText) findViewById(R.id.et_publish_theme); // 标题
		et_content_publish = (EditText) findViewById(R.id.et_content_publish);// 发表内容
		bt_publish = (Button) findViewById(R.id.bt_publish);// 发表按钮
		ib_publish_left_arrows.setOnClickListener(this);
		ib_tupian_publish.setOnClickListener(this);
		ib_biaoqing_publish.setOnClickListener(this);
		ib_add_pic.setOnClickListener(this);

		initListener();

	}

	private void initListener() {

		// 监听发表按钮
		bt_publish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// 解析数据
				new Thread() {
					@Override
					public void run() {
						parseData();
					}
				}.start();

			}
		});
		
//       监听添加表情按钮
		ib_biaoqing_publish.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
		 
				kkk(v);
								
			}

			private void kkk(View view) {
				 View contentView = LayoutInflater.from(getApplication()).inflate(
			                R.layout.popwindow_item, null);
			        // 设置按钮的点击事件
				 GridView button = (GridView) contentView.findViewById(R.id.gridview);
			       
			        final PopupWindow popupWindow = new PopupWindow(contentView,
			                 500, 500, true);

			        popupWindow.setTouchable(true);
 
			        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
			        // 我觉得这里是API的一个bug
			        popupWindow.setBackgroundDrawable(getResources().getDrawable(
			                R.drawable.background_transparent));

			        // 设置好参数之后再show
			        popupWindow.showAsDropDown(view);

			    }
		 
				
			
		});
		
//		 监听添加图片按钮
		ib_tupian_publish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ib_add_pic.getVisibility() == View.VISIBLE) {
					ib_add_pic.setVisibility(View.INVISIBLE);
				} else {
					ib_add_pic.setVisibility(View.VISIBLE);
				}
			}
		});

		// 监听添加图片按钮
		ib_add_pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gallery();
			}
		});
	}
	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}

		}else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				bitmap = data.getParcelableExtra("data");
				ib_add_pic.setImageBitmap(bitmap);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 剪切图片
	 * 
	 * @function:
	 * @author:Jerry
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private void parseData() {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "20000");
			json.put("userId", "10005");
			json.put("T_ID", "1");
			json.put("title", et_publish_theme.getText().toString());
			json.put("detail", et_content_publish.getText().toString());
			json.put("ClientId", "2");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://qzappservice.pcjoy.cn/Edit.ashx";
		callService(url, json);

	}

	private void callService(String url, JSONObject json) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();

		try {
			params.setBodyEntity(new StringEntity(json.toString(), HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json = responseInfo.result;
				Log.e("aaaaaaaaaaarrr", json);
				try {
					JSONObject jo = new JSONObject(json);
					if (jo.getString("result").equals("发表话题成功")) {
						Utils.showToast(getApplicationContext(), "发表话题成功");
						PublishActivity.this.finish();
					}
					// Utils.showToast(getApplicationContext(), jo.result);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
			}

			@Override
			public void onFailure(
					com.lidroid.xutils.exception.HttpException error, String msg) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_publish_left_arrows:
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in,
					R.anim.tran_pre_out);
			break;
		case R.id.ib_tupian_publish:

			break;

		case R.id.ib_add_pic:
			Toast.makeText(this, "----------------", 0).show();
			break;

		default:
			break;
		}
//		-----------------------------------------------------------------------
		
	}
}
