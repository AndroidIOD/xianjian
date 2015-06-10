package com.cyld.lfcircle;

import java.io.UnsupportedEncodingException;

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

import com.cyld.lfcircle.domain.MyTopicBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class MyTopic extends Activity implements IXListViewListener{
	private ImageView ivHoutui;
	private ListView lvTopiclist;
	private TextView tvTitle;
	private ImageView ivYonghu;
	private XListView mListView;
	private Handler mHandler;

	public MyTopicBean mtb = new MyTopicBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mytopic);

		mListView = (XListView) findViewById(R.id.lv_topiclist);
		mListView.setPullLoadEnable(true);

		mListView.setXListViewListener(this);
		mHandler = new Handler();

		parseJson();
		setView();
		setListener();
	}

	private void parseJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("code", "10010");
			json.put("userId", "10005");
			json.put("page", "1");
			json.put("pageCount", "20");
			json.put("ordinal", "1");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://qzappservice.pcjoy.cn/Data.ashx";
		System.out.println(json.toString());
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
				mtb = gson.fromJson(json, MyTopicBean.class);
				lvTopiclist.setAdapter(new MyTopicAdapter());

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
			}
		});
	}

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

	private void setView() {
		lvTopiclist = (ListView) findViewById(R.id.lv_topiclist);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivYonghu = (ImageView) findViewById(R.id.iv_yonghu);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);

		ivYonghu.setVisibility(View.INVISIBLE);
		lvTopiclist.setDivider(null);// 去掉listview的分割线
		tvTitle.setText("我的话题");
	}

	private void setListener() {

		ivHoutui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyTopic.this.finish();
			}
		});

	}

	class MyTopicAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mtb.list.size();
		}

		@Override
		public Object getItem(int position) {
			return mtb.list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(MyTopic.this, R.layout.item_mytopic,
						null);
				holder = new ViewHolder();
				holder.tvMytopictitle = (TextView) convertView
						.findViewById(R.id.tv_mytopictitle);
				holder.tvMytopicdetail = (TextView) convertView
						.findViewById(R.id.tv_mytopicdetail);
				holder.tvXianhuashu = (TextView) convertView
						.findViewById(R.id.tv_xianhuashu);
				holder.tvEggshu = (TextView) convertView
						.findViewById(R.id.tv_eggshu);
				holder.tvHuifushu = (TextView) convertView
						.findViewById(R.id.tv_huifushu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvMytopictitle.setText(mtb.list.get(position).title);
			holder.tvMytopicdetail.setText(mtb.list.get(position).content);
			holder.tvXianhuashu.setText(mtb.list.get(position).good);
			holder.tvEggshu.setText(mtb.list.get(position).bad);
			holder.tvHuifushu.setText(mtb.list.get(position).num);
			return convertView;
		}
	}

	static class ViewHolder {
		public TextView tvMytopictitle;
		public TextView tvMytopicdetail;
		public TextView tvXianhuashu;
		public TextView tvEggshu;
		public TextView tvHuifushu;
	}

}
