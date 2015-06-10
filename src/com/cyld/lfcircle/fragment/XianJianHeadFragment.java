package com.cyld.lfcircle.fragment;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cyld.lfcircle.R;
import com.cyld.lfcircle.domain.HeadListBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class XianJianHeadFragment extends Fragment {
	private View view;
	private GridView gvXianjiantitle;
	private HeadListBean hlb;
	private BitmapUtils butils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_xianjiantitle, null);

		initView();
		initListener();
		return view;
	}

	private void parseServer() {
		JSONObject json = new JSONObject();
		try {
			json.put("requestCode", "001017");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = "http://accountappservice.pcjoy.cn/app.ashx";
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

				Log.e("success:::::::::::::::", json);
				hlb = new HeadListBean();
				Gson gson = new Gson();
				hlb = gson.fromJson(json, HeadListBean.class);
				gvXianjiantitle.setAdapter(new HeadAdapter());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
			}
		});
	}

	private void initView() {
		parseServer();
		gvXianjiantitle = (GridView) view.findViewById(R.id.gv_xianjiantitle);

	}

	private void initListener() {
	}

	class HeadAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return hlb.data.a.size();
		}

		@Override
		public String getItem(int position) {
			return hlb.data.a.get(position).imgurl;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.item_image,
						null);

				holder = new ViewHolder();
				holder.ivhead = (ImageView) convertView
						.findViewById(R.id.iv_head);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// holder.ivhead.setImageResource(R.drawable.aa01);
			// 用XUTILS加载图片
			// butils = new BitmapUtils(getActivity());
			// butils.display(holder.ivhead, hlb.data.a.get(position).imgurl);

			// 用毕加索加载图片
//			Picasso.with(getActivity()).load(getItem(position)).resize(60, 60)
//					.centerCrop().into(holder.ivhead);
			Picasso.with(getActivity()).load(getItem(position)).into(holder.ivhead);


			return convertView;
		}

	}

	static class ViewHolder {
		public ImageView ivhead;
	}

}
