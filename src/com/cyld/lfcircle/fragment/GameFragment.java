package com.cyld.lfcircle.fragment;

import com.cyld.lfcircle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GameFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_game, null);
		GridView gvGame = (GridView) view.findViewById(R.id.gv_game);
		gvGame.setAdapter(new GameListAdapter());
		return view;
	}

	class GameListAdapter extends BaseAdapter {

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

			holder.ivhead.setImageResource(R.drawable.aa01);
			return convertView;
		}

	}

	static class ViewHolder {
		public ImageView ivhead;
	}

}
