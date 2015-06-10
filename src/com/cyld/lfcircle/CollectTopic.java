package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;
import org.nightweaver.view.RoundImageView;
import org.apache.http.entity.StringEntity;
import org.com.cctest.view.XListView;
import org.com.cctest.view.XListView.IXListViewListener;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyld.lfcircle.domain.TopicCollectBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class CollectTopic extends Activity implements IXListViewListener {
	private TextView tvTitle;
	private ImageView ivHoutui;
	private ImageView ivYonghu;
	private ListView lvTopiclist;
	private static final String TAG = "TAG";
	private XListView mListView;
	// private ArrayAdapter<String> mAdapter;
	// private ArrayList<String> items = new ArrayList<String>();
	private TopiclistAdapter mAdapter;
	private Handler mHandler;
	// private int start = 0;
	// private static int refreshCnt = 0;

	public BitmapUtils butils;

	/*
	 * 存放解析bean的集合
	 */
	static TopicCollectBean tcb = new TopicCollectBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collecttopic);
		// geneItems();
		mListView = (XListView) findViewById(R.id.lv_topiclist);
		mListView.setPullLoadEnable(true);
		// mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		// mListView.setAdapter(mAdapter);

		mListView.setXListViewListener(this);
		mHandler = new Handler();
		butils = new BitmapUtils(this);

		setView();
		setListener();
		parseJson();
	}

	private void setView() {

		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		ivYonghu = (ImageView) findViewById(R.id.iv_yonghu);
		lvTopiclist = (ListView) findViewById(R.id.lv_topiclist);
		ivYonghu.setVisibility(View.INVISIBLE);
		tvTitle.setText("话题收藏");
		lvTopiclist.setDivider(null);
		// utils.configDefaultLoadingImage(R.drawable.zhanweitu);
	}

	private void setListener() {

		CallBack callback = new CallBack();
		ivHoutui.setOnClickListener(callback);

	}

	class TopiclistAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return tcb.list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tcb.list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(CollectTopic.this,
						R.layout.item_list2, null);
				holder = new ViewHolder();
				holder.ivTitleimage = (RoundImageView) convertView
						.findViewById(R.id.iv_titleimage);
				holder.tvUsernickname = (TextView) convertView
						.findViewById(R.id.tv_usernickname);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.tv_time);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title1);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvUsernickname
					.setText(tcb.list.get(position).user.Usernickname);
			holder.tvTime.setText(tcb.list.get(position).time);
			holder.tvTitle.setText(tcb.list.get(position).title);
			butils.display(holder.ivTitleimage,tcb.list.get(position).user.Userhead);

			return convertView;
		}
	}

	static class ViewHolder {
		public RoundImageView ivTitleimage;
		public TextView tvUsernickname;
		public TextView tvTime;
		public TextView tvTitle;
	}

	public void parseJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "10008");
			json.put("userId", "1");
			json.put("page", "1");
			json.put("pageCount", "20");
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
				
				Gson gson = new Gson();
				tcb = gson.fromJson(json, TopicCollectBean.class);

				lvTopiclist.setAdapter(new TopiclistAdapter());

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
			}
		});
	}

	class CallBack implements OnClickListener {

		@Override
		public void onClick(View v) {
			CollectTopic.this.finish();
		}

	}

	// private void geneItems() {
	// for (int i = 0; i != 20; ++i) {
	// items.add("refresh cnt " + (++start));
	// }
	// }

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// start = ++refreshCnt;
				// items.clear();
				// geneItems();
				// mAdapter.notifyDataSetChanged();
				// mAdapter = new ArrayAdapter<String>(CollectTopic.this,
				// R.layout.list_item, items);
				// mListView.setAdapter(mAdapter);
				parseJson();
				onLoad();
			}
		}, 500);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// geneItems();
				parseJson();
				// mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 500);

	}

}
