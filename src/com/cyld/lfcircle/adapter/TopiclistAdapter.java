package com.cyld.lfcircle.adapter;

import com.cyld.lfcircle.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TopiclistAdapter extends BaseAdapter{
	
	private Context context;
	public TopiclistAdapter(Context context){
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_mytopic, null);
			holder = new ViewHolder();
			holder.tvMytopictitle = (TextView) convertView.findViewById(R.id.tv_mytopictitle);
			holder.tvMytopicdetail = (TextView) convertView.findViewById(R.id.tv_mytopicdetail);
			holder.tvXianhuashu = (TextView) convertView.findViewById(R.id.tv_xianhuashu);
			holder.tvEggshu = (TextView) convertView.findViewById(R.id.tv_eggshu);
			holder.tvHuifushu = (TextView) convertView.findViewById(R.id.tv_huifushu);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	static class ViewHolder{
		public TextView tvMytopictitle;
		public TextView tvMytopicdetail;
		public TextView tvXianhuashu;
		public TextView tvEggshu;
		public TextView tvHuifushu;
	}

}
