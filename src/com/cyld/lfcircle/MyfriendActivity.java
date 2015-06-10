package com.cyld.lfcircle;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.cyld.lfcircle.adapter.HaoHanAdapter;
import com.cyld.lfcircle.domain.Person;
import com.cyld.lfcircle.utils.Cheeses;

public class MyfriendActivity extends Activity {

	private ArrayList<Person> persons;
	private ListView lvFriend;
	private ImageView ivHoutui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myfriend);

		lvFriend = (ListView) findViewById(R.id.lv_friend);
		ivHoutui = (ImageView) findViewById(R.id.iv_houtui);
		persons = new ArrayList<Person>();

		// 填充数据 , 排序
		fillAndSortData(persons);

		lvFriend.setAdapter(new HaoHanAdapter(MyfriendActivity.this, persons));

		setListener();

	}

	private void setListener() {
		ClickBack clickback = new ClickBack();
		ivHoutui.setOnClickListener(clickback);
	}

	class ClickBack implements OnClickListener {

		@Override
		public void onClick(View v) {
			MyfriendActivity.this.finish();
		}

	}

	private void fillAndSortData(ArrayList<Person> persons) {
		// 填充数据
		for (int i = 0; i < Cheeses.NAMES.length; i++) {
			String name = Cheeses.NAMES[i];
			persons.add(new Person(name));
		}

		// 进行排序
		Collections.sort(persons);
	}

}
