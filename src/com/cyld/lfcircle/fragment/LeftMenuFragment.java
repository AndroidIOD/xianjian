package com.cyld.lfcircle.fragment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.nightweaver.view.RoundImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyld.lfcircle.CollectTopic;
import com.cyld.lfcircle.LoginActivity;
import com.cyld.lfcircle.MainActivity;
import com.cyld.lfcircle.MyTopic;
import com.cyld.lfcircle.MyfriendActivity;
import com.cyld.lfcircle.PersonalMain;
import com.cyld.lfcircle.R;
import com.cyld.lfcircle.domain.LoginBean;
import com.cyld.lfcircle.utils.PrefUtils;
import com.cyld.lfcircle.utils.StreamTool;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

//import com.itheima.zhbj52.base.TabDetailPager.ViewHolder;

/**
 * 侧边栏
 * 
 * @author Kevin
 * 
 */
public class LeftMenuFragment extends BaseFragment {

	LoginBean lbean;

	@ViewInject(R.id.lv_cebian)
	private ListView lvCebian;

	@ViewInject(R.id.iv_denglu)
	private RoundImageView ivDenglu;

	@ViewInject(R.id.tv_denglu)
	private TextView tvDenglu;

	public BitmapUtils butils;

	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/download_test/";

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		// 如果已经登陆加载图片
		if (PrefUtils.getBoolean(mActivity, "islogin", false)) {
			String userid = PrefUtils.getString(getActivity(), "userid", "-1");
			File mFile=new File(ALBUM_PATH+"head.jpg");
	        //若该文件存在
	        if (mFile.exists()) {
	            Bitmap bitmap=BitmapFactory.decodeFile(ALBUM_PATH+"head.jpg");
	            ivDenglu.setImageBitmap(bitmap);
	        }
	        
	        tvDenglu.setText(userid);
	        
		}

	}

	
	
	@Override
	public void initData() {
		LeftListAdapter llAdapter = new LeftListAdapter();
		lvCebian.setAdapter(llAdapter);
		lvCebian.setOnItemClickListener(new ListAdapter());

		ivDenglu.setImageResource(R.drawable.yonghudeng);
		ivDenglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(new Intent(mActivity,LoginActivity.class));
				if (PrefUtils.getBoolean(getActivity(), "islogin", false)) {

					startActivity(new Intent(mActivity, PersonalMain.class));

				} else {
					startActivityForResult(new Intent(mActivity,
							LoginActivity.class), 1);
				}
			}
		});
	}

	// 接收数据
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		loadHeadImage(data);

	}

	private void loadHeadImage(Intent data) {
		try {
			lbean = new LoginBean();
			lbean = (LoginBean) data.getSerializableExtra("LoginBean");
			// 此处有BUG
			butils = new BitmapUtils(getActivity());
			Log.e("userid::::::", lbean.data.user.Userid);
			Log.e("userhead", lbean.data.user.Userhead);
			tvDenglu.setText(lbean.data.user.Userid);
			// butils.display(ivDenglu, lbean.data.user.Userhead);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						URL url = new URL(lbean.data.user.Userhead);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("GET"); // 设置请求方法为GET
						conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
						InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
						byte[] data = StreamTool.readInputStream(inputStream); // 获得图片的二进制数据
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length); // 生成位图
						saveFile(bitmap);
						Message me = handler.obtainMessage();
						me.obj = bitmap;
						handler.sendMessage(me);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}).start();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// butils.display(ivDenglu, userhead);

	}

	/**
	 * 保存图片到SD卡
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public void saveFile(Bitmap bm) throws IOException {
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + "head.jpg");
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

			ivDenglu.setImageBitmap((Bitmap) msg.obj); // 显示图片
		};
	};

	class ListAdapter implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			switch (position) {

			// 我的好友
			case 0:
				startActivity(new Intent(mActivity, MyfriendActivity.class));
				toggleSlidingMenu();
				break;

			// 收藏的话题
			case 1:
				startActivity(new Intent(mActivity, CollectTopic.class));
				toggleSlidingMenu();
				break;

			// 我的话题
			case 2:
				startActivity(new Intent(mActivity, MyTopic.class));
				toggleSlidingMenu();
				break;

			default:
				break;
			}

		}

	}

	class LeftListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 6;
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
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.item_leftfragment, null);
				holder = new ViewHolder();
				holder.leftIcon = (ImageView) convertView
						.findViewById(R.id.left_icon);
				holder.tvIconname = (TextView) convertView
						.findViewById(R.id.tv_iconname);

				holder.tvLedou = (TextView) convertView
						.findViewById(R.id.tv_ledou);

				holder.ivXiangyou = (ImageView) convertView
						.findViewById(R.id.iv_xiangyou);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			switch (position) {
			case 0:
				holder.tvIconname.setText("我的好友");
				holder.leftIcon.setImageResource(R.drawable.haoyoulieb48);
				break;
			case 1:
				holder.tvIconname.setText("收藏话题");
				holder.leftIcon.setImageResource(R.drawable.shoucang48);
				break;
			case 2:
				holder.tvIconname.setText("我的话题");
				holder.leftIcon.setImageResource(R.drawable.wodehuati48);
				break;
			case 3:
				holder.tvIconname.setText("我的乐豆");
				holder.tvLedou.setText("155522");
				holder.leftIcon.setImageResource(R.drawable.ledou48);
				break;
			case 4:
				holder.tvIconname.setText("道具仓库");
				holder.leftIcon.setImageResource(R.drawable.cangku48);
				break;
			case 5:
				holder.tvIconname.setText("设置");
				holder.leftIcon.setImageResource(R.drawable.shezhi48);
				break;
			}

			return convertView;
		}
	}

	static class ViewHolder {
		public ImageView leftIcon;
		public TextView tvIconname;
		public TextView tvLedou;
		public ImageView ivXiangyou;
	}

	/**
	 * e 切换SlidingMenu的状态
	 * 
	 * @param b
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();// 切换状态, 显示时隐藏, 隐藏时显示
	}

	/**
	 * 设置当前菜单详情页
	 * 
	 * @param position
	 */
	// protected void setCurrentMenuDetailPager(int position) {
	// MainActivity mainUi = (MainActivity) mActivity;
	// ContentFragment fragment = mainUi.getContentFragment();// 获取主页面fragment
	// MessagePager pager = fragment.getNewsCenterPager();// 获取--页面
	// pager.setCurrentMenuDetailPager(position);// 设置当前菜单详情页
	// }

	/**
	 * 侧边栏数据适配器
	 * 
	 * @author Kevin
	 * 
	 */
	// class MenuAdapter extends BaseAdapter {
	//
	// @Override
	// public int getCount() {
	// return mMenuList.size();
	// }
	//
	// @Override
	// public NewsMenuData getItem(int position) {
	// return mMenuList.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// View view = View.inflate(mActivity, R.layout.list_menu_item, null);
	// TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
	// NewsMenuData newsMenuData = getItem(position);
	// tvTitle.setText(newsMenuData.title);
	//
	// if (mCurrentPos == position) {// 判断当前绘制的view是否被选中
	// // 显示红色
	// tvTitle.setEnabled(true);
	// } else {
	// // 显示白色
	// tvTitle.setEnabled(false);
	// }
	//
	// return view;
	// }
	//
	// }

}
