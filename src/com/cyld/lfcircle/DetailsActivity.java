package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpException;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cyld.lfcircle.utils.TreadToastUtils;
import com.cyld.lfcircle.utils.Utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class DetailsActivity extends Activity implements OnClickListener {
	private ImageButton ib_details_arrows_left;// 发表页返回的箭头
	private ListView lv_floor;// 楼层的listview
	private ImageButton bt_menu;// 菜单按钮
	private Context mContext = null;// 上下文
	static private GridView mGridView; // 菜单gridview
	private ImageButton ib_floor_huifu;// 每个楼层回复的按钮
	private Button btn_comment_to_topic ;
	private  EditText  et_response ;
	private static final String[] names = { "收藏", "分享", "跳转", "举报", "倒叙查看",
			"提醒", "删除", };
	private static final int[] icons = { R.drawable.item0, R.drawable.item1,
			R.drawable.item2, R.drawable.item3, R.drawable.item4,
			R.drawable.item5, R.drawable.item6, };
	private View ll_details_header;
	private PopupWindow pop; // popwindow窗口
//JINTIANTAMADEYESHUIZHILE
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details);
		btn_comment_to_topic = (Button)findViewById(R.id.btn_comment_to_topic);
		
		mContext = this;	
		ib_details_arrows_left = (ImageButton) findViewById(R.id.ib_details_arrows_left);
		bt_menu = (ImageButton) findViewById(R.id.bt_menu);
		ib_details_arrows_left.setOnClickListener(this);
		bt_menu.setOnClickListener(this);
		ll_details_header = View.inflate(mContext, R.layout.details_header1,null);
		lv_floor = (ListView) findViewById(R.id.lv_floor);
		lv_floor.addHeaderView(ll_details_header);
		lv_floor.setAdapter(new FloorAdapter());
		// 楼层
		lv_floor.setOnItemClickListener(new FloorOnItemClickListener());
      initListener();
	}
	private void initListener() {
		// 监听发表按钮
		btn_comment_to_topic.setOnClickListener(new OnClickListener() {
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
	}
//		一下是获取网络服务器的代码zhentamaderiabaoelezaiwaibaosdonshigan
		private void parseData() {
			JSONObject json = new JSONObject();
			try {
				json.put("code", "20003");
				json.put("topId", "3");
				json.put("postsId", "2");
				json.put("userId", "123");
				json.put("T_ID", "1");
				json.put("detail", "1");
				json.put("title", "null");
				json.put("P_tab", "1");
//				json.put("title", et_publish_theme.getText().toString());
//				json.put("detail", et_content_publish.getText().toString());
//				json.put("ClientId", "2");
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
//							PublishActivity.this.finish();
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
//		以上是获取网络代码
	class FloorOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			finish();
			enterCommentDeyails();
		}

	}

	/**
	 * 
	 * 楼层的adapter
	 * 
	 */
	class FloorAdapter extends BaseAdapter {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			int x = 1;
			if (convertView == null) {
				convertView = View.inflate(DetailsActivity.this,
						R.layout.list_floor, null);

				holder = new ViewHolder();

				holder.iv_floor_user = (ImageView) convertView
						.findViewById(R.id.iv_floor_user);
				holder.tv_floor_username = (TextView) convertView
						.findViewById(R.id.tv_floor_username);
				holder.tv_floornumber = (TextView) convertView
						.findViewById(R.id.tv_floornumber);
				holder.tv_commenttime = (TextView) convertView
						.findViewById(R.id.tv_commenttime);
				 
				convertView.setTag(holder);
			} else {
			}

			return convertView;
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}

	/**
	 * 举报页面
	 */
	private void enterReport() {
		startActivity(new Intent(DetailsActivity.this, ReportActivity.class));
	}

	protected void enterCommentDeyails() {
		startActivity(new Intent(DetailsActivity.this, CommentDetails.class));
		this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//页面进出的动画
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_details_arrows_left:
			finish();
			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			break;
		case R.id.bt_menu:
			// TreadToastUtils.showToast(this, "popwindow");
			showPopwindow();
			break;
		case R.id.ib_floor_huifu:

			TreadToastUtils.showToast(this, "popwindow");
			 //showFloorHuifu();
			break;
		default:
			break;
		}
	}
 
	/**
	 * 回复按钮的弹出框
	 * 
	 */
	private void showFloorHuifu() {
		View v = LayoutInflater.from(this).inflate(
				R.layout.popwindow_floor_huifu, null);
		new PopupWindow(v, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
	}

	private void showPopwindow() {

		// 一个自定义的布局，作为显示的内容
		View v = LayoutInflater.from(mContext).inflate(R.layout.gridview, null);
		mGridView = (GridView) v.findViewById(R.id.gridview);
		mGridView.setAdapter(new GridViewAdapter());
	
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Toast.makeText(DetailsActivity.this, "收藏", 0).show();
					break;
				case 1:
					Toast.makeText(DetailsActivity.this, "fx", 0).show();
					Share();
					pop.dismiss();
					break;
				case 2:
					Toast.makeText(DetailsActivity.this, "tz", 0).show();
					break;
				case 3:
					Toast.makeText(DetailsActivity.this, "jb", 0).show();
					pop.dismiss();
					enterReport();
					break;
				case 4:
					Toast.makeText(DetailsActivity.this, "daoxu", 0).show();
					break;
				case 5:
					Toast.makeText(DetailsActivity.this, "tx", 0).show();
					break;
				case 6:
					Toast.makeText(DetailsActivity.this, "sc", 0).show();
					break;

				default:
					break;
				}
			}

		});
		pop = new PopupWindow(v, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置好参数之后再show
		if (pop != null && pop.isShowing()) {
			pop.dismiss(); 
		} else {
			View vv = findViewById(R.id.rl_title);
			pop.showAsDropDown(vv, 0, 0);//放在vv的下面	
		}
	}
//新人要研究一下...
	/**
	 * gridview的adapter
	 * 
	 */
	class GridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(DetailsActivity.this,
					R.layout.list_gridview, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_home_item);
			ImageView iv_icon = (ImageView) view
					.findViewById(R.id.iv_home_item);
			tv_name.setText(names[position]);
			iv_icon.setImageResource(icons[position]);
			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_floor_user;
		TextView tv_floor_username;
		TextView tv_floornumber;
		TextView tv_commenttime;
	}

	/**
	 * 分享
	 * 
	 */
	protected void Share() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
		
	}

}
