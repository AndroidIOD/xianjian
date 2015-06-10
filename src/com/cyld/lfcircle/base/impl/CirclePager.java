package com.cyld.lfcircle.base.impl;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyld.lfcircle.R;
import com.cyld.lfcircle.SearchActivity;
import com.cyld.lfcircle.TopicActivity;
import com.cyld.lfcircle.base.BasePager;
import com.cyld.lfcircle.base.impl.CirclePagerNet.BaguaActivity;
import com.cyld.lfcircle.base.impl.CirclePagerNet.DanjiActivity;
import com.cyld.lfcircle.base.impl.CirclePagerNet.ErshouActivity;
import com.cyld.lfcircle.base.impl.CirclePagerNet.TongrenActivity;
import com.cyld.lfcircle.domain.GameListBean;
import com.cyld.lfcircle.domain.GameListBean.LYJ;
import com.cyld.lfcircle.ui.RoundImageView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/** 
 * 圈子的页面
 */
public class CirclePager extends BasePager implements OnClickListener {
	 DisplayImageOptions options = new DisplayImageOptions.Builder()  
     .showImageOnLoading(R.drawable.ic_launcher)  
     .showImageOnFail(R.drawable.ic_launcher)  
     .cacheInMemory(true)  
     .cacheOnDisk(true)  
     .bitmapConfig(Bitmap.Config.RGB_565)  
     .build();   
	 //这是第二个集合
	static List<LYJ> lists = new ArrayList<LYJ>();//只能创建一个类
//	这是第一个集合
	static List<LYJ> listsbutton = new ArrayList<LYJ>();//只能创建一个类
	private ListView topiclistview;// 话题列表
	private View edition; // 圈子页的4个版块
	// private Activity mContext; //上下文
	private static final String[] topicnames = { "仙剑奇侠传", "轩辕剑", "英雄无敌", "光之子",
			"新增", "1", "2", "3", "4", "5" };
	
	private ImageButton ib_search;// 搜索的按钮
	private View v;// 表头
	private TextView tv_bagua;// 八卦
	private TextView tv_danji;// 单机
	private TextView tv_tongren;// 同人
	private TextView tv_ershou;// 二手
	
	private TopicAdapter topdapter;
	private RoundImageView           ibbagua;
	private RoundImageView           lbdanji;
	private RoundImageView           ibtongren;
	private RoundImageView           ibershou ;
	
	public CirclePager(Activity activity) {
		super(activity);
	}
	public CirclePager() {
	}
//进入网络
	private void getData() {
//		网络方法
		parseJson();
	}	
//解析数据的方法
	private void parseJson() {
		//json对象
		JSONObject json = new JSONObject();
//		请求参数
		try {
			json.put("code", "10000");
			json.put("topicId", "0");
			json.put("type", null);
			json.put("postsId", "0");
			json.put("userId", 0);
			json.put("T_ITTable", null);
			json.put("T_ID", 0);
			json.put("detail", null);
			json.put("title", null);
			json.put("image", null);
		} catch (JSONException e) {
			e.printStackTrace();
		}
//    访问的URL
		String url = "http://qzappservice.pcjoy.cn/Data.ashx";
		System.out.println(json.toString());
		//调用HttpUtils函数
		callService(url, json);		
	}		 
//回调函数
	private void callService(String url, JSONObject json) {
		HttpUtils utils = new HttpUtils();
		RequestParams params = new RequestParams();//请求参数对象
		try {
			params.setBodyEntity(new StringEntity(json.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			private GameListBean gamelistbean;
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json = responseInfo.result;
                //  谷歌开源的框架
				Gson gson = new Gson();
				gamelistbean = gson.fromJson(json, GameListBean.class);
				if(gamelistbean.list1!=null && gamelistbean.list2!=null){
				 listsbutton.addAll(gamelistbean.list1);
				lists.addAll(gamelistbean.list2);
				dd();
				}
				//话题列表适配器。进行listview的布局
				if(topdapter!=null)
//					有时候我们需要修改已经生成的列
//					表，添加或者修改数据，notifyDataSetChanged()可以在修改适配器绑定的数组后，
//					不用重新刷新Activity，通知Activity更新ListView
				topdapter.notifyDataSetChanged();			 
				} 		
			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("Fail::::::::::::::::", msg);
				System.out.println("shibaill....."+msg);				  
			}
		});
	}
	@Override
	//一运行Activity就调用该法initData
	public void initData() {
//		调用该方法进入加载网络数据，也就是让问网络
		getData();
		
		setSlidingMenuEnable(true);// 打开侧边栏

		v = View.inflate(mActivity, R.layout.circle, null);
		//edition = View.inflate(mActivity, R.layout.edition, null);
//		替换布局即可
		flContent.removeAllViews();
//获取最上层的按钮，进行动态的获取按钮图片和数据
		 
		
		
		
		
		
     
		
		// 向FrameLayout中动态添加布局
		 ib_search = (ImageButton) v.findViewById(R.id.ib_search);
//		 添加到framelayout中
	 	flContent.addView(v);
		topiclistview = (ListView) v.findViewById(R.id.lv);//可以从这里调用接口，获得动态数据
 //	topiclistview.addHeaderView(edition);// 加4个头版块
		
		
		
		
		
		
//给搜索按钮添加事件
		ib_search.setOnClickListener(this); 
//		创建适配器对象//从这里调用服务器接口，进行搜索
		topdapter = new TopicAdapter();
//		创建适配器
		topiclistview.setAdapter(topdapter);
//		创建适配器Item点击事件
		topiclistview.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				enterTopic();
				
			}

		});
	}

	// 详情页
	public void enterTopic() {
//		点击圈子里面的每个条目进入该页面
		mActivity.startActivity(new Intent(mActivity, TopicActivity.class));
		finish();
		mActivity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
	
	public void dd(){
         tv_bagua = (TextView)v.findViewById(R.id.tv_bagua);
         tv_bagua.setText(listsbutton.get(0).T_Name);
         
         tv_danji = (TextView)v.findViewById(R.id.tv_danji);
         tv_danji.setText(listsbutton.get(1).T_Name);
         
         tv_tongren = (TextView)v.findViewById(R.id.tv_tongren);
         tv_tongren.setText(listsbutton.get(2).T_Name);
         
         tv_ershou = (TextView)v.findViewById(R.id.tv_ershou);
         tv_ershou.setText(listsbutton.get(3).T_Name);
//         进入第一个按钮的页面
	     ibbagua = (RoundImageView)v . findViewById(R.id.ib_bagua);          
          ImageLoader.getInstance().displayImage(listsbutton.get(0).T_ImgSrc,ibbagua, options);
         ibbagua.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterSearchActivitybagua();
				
			}
		});
         
         
//          单机进入第二个页面
          lbdanji = 	(RoundImageView)v . findViewById(R.id.ib_danji);
          ImageLoader.getInstance().displayImage(listsbutton.get(1).T_ImgSrc,lbdanji, options);
          lbdanji.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterSearchActivitydanji();
			}			     	  
          });
//          进入第三个页面
          ibtongren = 	(RoundImageView)v . findViewById(R.id.ib_tongren);          
          ImageLoader.getInstance().displayImage(listsbutton.get(2).T_ImgSrc,ibtongren, options);
          ibtongren.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterSearchActivitytongren();
			}
        	  
          });
//          进入第四个页面
          ibershou = 	(RoundImageView)v . findViewById(R.id.ib_ershou);          
          ImageLoader.getInstance().displayImage(listsbutton.get(3).T_ImgSrc,ibershou, options);
          ibershou.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterSearchActivityershou();
			}
        	  
          });
		
	}
	
	
	
	
//	创建集合，用于存放从网络获取的数据对像
     
      
	class TopicAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if(lists!=null ){
			   return lists.size();
			}                         
			return 0;
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//创建ViewHolder
			ViewHolder holder;
			if (convertView == null) {
				//把item变成view对象
				convertView = View.inflate(mActivity, R.layout.list_topic_item,
						null);
//				创建holder对象
				holder = new ViewHolder();
//                       图片的id进行获取数据  contentView已经获取了条目中的全=全部id，所以用content直接find即可拉
				holder.iv_topiccategory_pic = (ImageView) convertView
						.findViewById(R.id.iv_topiccategory_pic);//图片
				holder.tv_topiccategory_title = (TextView) convertView
						.findViewById(R.id.tv_topiccategory_title);//标题
				holder.tv_topiccategory_topicnumber = (TextView) convertView
						.findViewById(R.id.tv_topiccategory_topicnumber);//话题数字
				holder.tv_topiccategory_content = (TextView) convertView//话题内容
						.findViewById(R.id.tv_topiccategory_content);
				convertView.setTag(holder);//这里是set设置
			} else {//如果缓存中（content）数据不为空，直接就可以进行holder进行绑定content中的数据id
				holder = (ViewHolder) convertView.getTag();//这里是get获取
			} 
//			判断json对象是否为空
			if(lists==null || lists.size()<0){
				return convertView;
			}
		 
			//这里动态调用获取服务器的数据
			
//			tv_bagua.setText(lists.get(position).T_ImgSrc);
			holder.tv_topiccategory_title.setText(lists.get(position).T_Name);
			holder.tv_topiccategory_content.setText(lists.get(position).T_Details);
			holder.tv_topiccategory_topicnumber.setText("话题:"+lists.get(position).T_Num);
			 
//	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
//	                .showImageOnLoading(R.drawable.ic_launcher)  
//	                .showImageOnFail(R.drawable.ic_launcher)  
//	                .cacheInMemory(true)  
//	                .cacheOnDisk(true)  
//	                .bitmapConfig(Bitmap.Config.RGB_565)  
//	                .build();  	          
	        ImageLoader.getInstance().displayImage(lists.get(position).T_ImgSrc, holder.iv_topiccategory_pic, options);
     			
			return convertView;
		}
           
	}

	static class ViewHolder {
		ImageView iv_topiccategory_pic;//图片
		TextView tv_topiccategory_title;//标题
		TextView tv_topiccategory_topicnumber;//话题数量
		TextView tv_topiccategory_content;//话题内容
	}

	@Override
	//搜索
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_search:
			Toast.makeText(mActivity, "---------------", 0).show();
			enterSearchActivity();
			break;

		default:
			break;
		}
	}
	private void enterSearchActivityershou() {
		// TODO Auto-generated method stub
		mActivity.startActivity(new Intent(mActivity, ErshouActivity.class));
		finish();//添加finish可以缓解内存压力
		mActivity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
//	单击事件进入同人作品界面
	private void enterSearchActivitytongren() {
		// TODO Auto-generated method stub
		mActivity.startActivity(new Intent(mActivity, TongrenActivity.class));
		finish();//添加finish可以缓解内存压力
		mActivity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
//	单击事件加载单机游戏界面
	private void enterSearchActivitydanji() {
		// TODO Auto-generated method stub
		mActivity.startActivity(new Intent(mActivity, DanjiActivity.class));
		finish();//添加finish可以缓解内存压力
		mActivity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
//	单击事件加载八卦页面
	private void enterSearchActivitybagua() {
		mActivity.startActivity(new Intent(mActivity, BaguaActivity.class));
		finish();//添加finish可以缓解内存压力
		mActivity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	//单击事件，加载搜索页面
	private void enterSearchActivity() {
		mActivity.startActivity(new Intent(mActivity, SearchActivity.class));
		finish();//添加finish可以缓解内存压力
		mActivity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

}
