package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.nightweaver.view.RoundImageView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyld.lfcircle.domain.TopicListBean;
import com.cyld.lfcircle.utils.TreadToastUtils;
import com.cyld.lfcircle.view.GridViewForScrollView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

/**
 * 某吧的详情页
 * 
 * @author kevin
 * 
 */
public class TopicActivity extends Activity implements OnClickListener {

	private ImageButton ib_list_topic_arrows_left; // 反回的箭头
	private ListView lv_list_topic_comment; // 帖子列表的listview
	private View theme_list_header;
	private ImageButton ib_list_topic_publish;
	private PtrFrameLayout ptrFrameLayout;
	private TopicListBean tlb;
	private ImageView ivShuaxin;
	private RotateAnimation rotate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消actioBar的方法
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 话题列表布局
		setContentView(R.layout.list_topic_content);

		// 请求服务器
		linkServer();

		// 初始化下拉刷新控件，没有做
		// initPullToRefreshView();

		// 获取布局中的控件
		ib_list_topic_arrows_left = (ImageButton) findViewById(R.id.ib_list_topic_arrows_left);
		// ListView控件
		lv_list_topic_comment = (ListView) findViewById(R.id.lv_list_topic_comment);
		// 点击评论按钮
		ib_list_topic_publish = (ImageButton) findViewById(R.id.ib_list_topic_publish);
		theme_list_header = View // k是主题列表。添加theme_list_header到View顶部即可啦啦啦
				.inflate(this, R.layout.theme_list_header, null);
		ivShuaxin = (ImageView) findViewById(R.id.iv_shuaxin); // 右下角刷新按钮

		// 给ListView添加头
		lv_list_topic_comment.addHeaderView(theme_list_header);

		// 去除item之间的分割线
		lv_list_topic_comment.setDivider(null);
		// 给返回箭头添加事件
		ib_list_topic_arrows_left.setOnClickListener(this);
		// 点击时的评论按钮添加事件
		ib_list_topic_publish.setOnClickListener(this);
		// listview监听
		lv_list_topic_comment
				.setOnItemClickListener(new MyOnItemClickListener());
		// 刷新按钮监听
		ivShuaxin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnim();
			}
		});

	}
	/**
	 * 开启动画
	 */
	private void startAnim() {
		rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(1000);// 动画时间
		rotate.setFillAfter(true);// 保持动画状态

		// 监听刷新动画
		rotate.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				linkServer();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});

		ivShuaxin.startAnimation(rotate);

	}

	private void linkServer() {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "10001");
			json.put("T_ID", "1");
			json.put("page", "1");
			json.put("pageCount", "10");
			json.put("ordinal", "2");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://qzappservice.pcjoy.cn/Data.ashx";
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
				String json = responseInfo.result;

				// Log.e("success:::::::::::::::", json);

				tlb = new TopicListBean();
				Gson gson = new Gson();
				tlb = gson.fromJson(json, TopicListBean.class);

				// String uu = tlb.topicList.dataList.get(4).user.Userhead;
				// Log.e("....................", uu);
				// 给listview添加适配器
				lv_list_topic_comment.setAdapter(new ArticleAdapter());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
			}
		});
	}

	@Override
	// 点击的哪个item
	public void onClick(View v) {
		// 获取item的id
		switch (v.getId()) {
		case R.id.ib_list_topic_arrows_left:
			// 返回到圈子页面
			finish();
			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			break;
		case R.id.ib_list_topic_publish:
			// 评论按钮事件
			TreadToastUtils.showToast(this, "仙剑奇侠传");
			// 发表
			this.startActivity(new Intent(TopicActivity.this,
					PublishActivity.class));
			break;
		case R.id.ib_list_topic_inform:
			// 消息
			TreadToastUtils.showToast(this, "新消息");
			break;

		default:
			break;
		}
	}

	class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			enterDetailsActivity();
		}
	}

	static class ViewHolder {
		RoundImageView iv_userpic;// 头像
		TextView tv_username;// 用户名
		ImageView iv_genderpic;// 性别
		TextView tv_userlevel; // 等级
		TextView tv_commentnumber; // 回复数
		TextView tv_time;// 回复时间
		TextView tv_topictitle;// 小话题标题
		TextView tv_topicdetail;// 小话题详情
		TextView hua; // 鲜花数
		TextView cai; // 菜数

		// 话题图片
		GridViewForScrollView thumbnailGridView;
	}

	class ArticleAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return tlb.topicList.dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = View.inflate(TopicActivity.this,
						R.layout.list_comment_content, null);
				holder = new ViewHolder();

				holder.iv_userpic = (RoundImageView) convertView
						.findViewById(R.id.iv_userpic); // 头像
				holder.tv_username = (TextView) convertView
						.findViewById(R.id.tv_username); // 昵称
				holder.iv_genderpic = (ImageView) convertView
						.findViewById(R.id.iv_genderpic); // 性别
				holder.tv_userlevel = (TextView) convertView
						.findViewById(R.id.tv_userlevel); // 等级
				holder.tv_commentnumber = (TextView) convertView
						.findViewById(R.id.tv_commentnumber); // 回复数
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_time);// 回复时间
				holder.tv_topictitle = (TextView) convertView
						.findViewById(R.id.tv_topictitle);// 小话题标题
				holder.tv_topicdetail = (TextView) convertView
						.findViewById(R.id.tv_topicdetail);// 话题详情
				holder.hua = (TextView) convertView.findViewById(R.id.hua); // 鲜花数
				holder.cai = (TextView) convertView.findViewById(R.id.cai); // 菜数

				convertView.setTag(holder);
				// 话题列表中每个帖子中的图片，点进去会进入一个查看图片的页面，目前没有做呢
				holder.thumbnailGridView = (GridViewForScrollView) convertView
						.findViewById(R.id.gridview_topics_thumbnail);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Picasso.with(getApplicationContext())
					.load(tlb.topicList.dataList.get(position).user.Userhead)
					.into(holder.iv_userpic);

			holder.tv_username
					.setText(tlb.topicList.dataList.get(position).user.Usernickname);

			if (tlb.topicList.dataList.get(position).user.UserSex.equals("1")) {
				holder.iv_genderpic.setImageResource(R.drawable.woman);
			} else if (tlb.topicList.dataList.get(position).user.UserSex
					.equals("0")) {
				holder.iv_genderpic.setImageResource(R.drawable.man);
			} else {
				holder.iv_genderpic.setVisibility(View.GONE);
			}

			// String userleve =
			// tlb.topicList.dataList.get(position).user.UserLeve;
			// Log.e("eeeeeeeeeeeeeee", userleve+"9999999");
			// if(userleve.isEmpty()){
			// holder.tv_userlevel.setText("LV."+"0");
			// }else{
			holder.tv_userlevel.setText("LV."
					+ tlb.topicList.dataList.get(position).user.UserLeve);

			// }

			holder.tv_commentnumber.setText(tlb.topicList.dataList
					.get(position).num);
			holder.tv_topictitle
					.setText(tlb.topicList.dataList.get(position).title);
			holder.tv_topicdetail
					.setText(tlb.topicList.dataList.get(position).content);
			holder.hua.setText(tlb.topicList.dataList.get(position).good);
			holder.cai.setText(tlb.topicList.dataList.get(position).bad);
			holder.tv_time.setText(tlb.topicList.dataList.get(position).time);

			return convertView;
		}
	}

	public void enterDetailsActivity() {
		startActivity(new Intent(TopicActivity.this, DetailsActivity.class));
		this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

}
