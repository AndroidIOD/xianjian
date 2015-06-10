package com.cyld.lfcircle.base.impl.CirclePagerNet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyld.lfcircle.R;
import com.cyld.lfcircle.TopicActivity;
import com.cyld.lfcircle.domain.BaguaJavabean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class BaguaActivity extends Activity /**implements OnClickListener*/  {	
//	定义的javabean对象
 	 BaguaJavabean glb ;
 	
	DisplayImageOptions options = new DisplayImageOptions.Builder()  
	.showImageOnLoading(R.drawable.ic_launcher)  
	.showImageOnFail(R.drawable.ic_launcher)  
	.cacheInMemory(true)  
	.cacheOnDisk(true)  
	.bitmapConfig(Bitmap.Config.RGB_565)  
	.build();  

	//全局变量，xml布局中的控件按钮
	public View       v ;
//	public ListViewAdapter listAdapter ;
	public ImageButton ib_list_topic_arrows_left;
	public ListView mListUser ;
	public ImageView mImageZhiding1 ;
	public ImageView mImageZhiding2 ;
	public ImageView mImageZhiding3 ;
	public ImageView mImageTopic ;
	public ImageView ib_list_topic_publish ;
	//   --------------------------------------
	public TextView  mTextTopic ;
	public TextView  mTextHuati ;
	public TextView  mzhiding1 ;
	public TextView  mzhiding2 ;
	public TextView  mzhiding3 ;
	public TextView  mtextviewxj ;

	//   ---------------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(BaguaActivity.this,TopicActivity.class);
		startActivity(intent);
		//	定义加载网络的方法
//		setContentView(R.layout.circlebaguaactivity);	
//		mListUser  = (ListView) findViewById(R.id.lv_bagua);
//		mImageTopic = (ImageView)findViewById(R.id.iv_pic);
//		mtextviewxj = (TextView)findViewById(R.id.textViewxj);
//		mImageZhiding1 = (ImageView)findViewById(R.id.iv_notice1);
//		mImageZhiding2 = (ImageView)findViewById(R.id.iv_notice2);
//		mImageZhiding3 = (ImageView)findViewById(R.id.iv_notice3);
//		mzhiding1 = (TextView)findViewById(R.id.textView1);
//		mzhiding2 = (TextView)findViewById(R.id.textView2);
//		mzhiding3 = (TextView)findViewById(R.id.textView3);
//		ib_list_topic_arrows_left = (ImageButton) findViewById(R.id.ib_list_topic_arrows_left);	
//		ib_list_topic_arrows_left.setOnClickListener(this);
////		点击评论按钮
//		ib_list_topic_publish = (ImageButton) findViewById(R.id.ib_list_topic_publish);
////		点击时的评论按钮添加事件
//		ib_list_topic_publish.setOnClickListener(this);
//		
//		
//		 parseData(); 
//	}
//	private void parseData() {
//		JSONObject json = new JSONObject();
//		try {
//			json.put("code", "10001");
//			json.put("T_ID", "1");
//			json.put("page", "1");
//			json.put("pageCount", "20");
//			json.put("ordinal","1");
//		} 
//		catch (JSONException e) {
//			e.printStackTrace();
//		}
//		String url = "http://qzappservice.pcjoy.cn/Data.ashx";
//		Log.e("jsonString...........................", json.toString());
//		callService(url, json);//调用HttpUtils函数
//	}
//	private void callService(String url, JSONObject json) {
//		HttpUtils utils = new HttpUtils();
//		RequestParams params = new RequestParams();//请求参数对象
//		try {
//			params.setBodyEntity(new StringEntity(json.toString()));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
//			@Override
//			public void onSuccess(ResponseInfo<String> responseInfo) {
//				String json = responseInfo.result;
//				//			Log.e("jsonInfo...............................", json);
//				Gson gson = new Gson();
//				glb = gson.fromJson(json, BaguaJavabean.class);
//				
//				System.out.println(glb);
//				Log.e("jsonString...........................",glb.smallList.size()+"kkkkkkkkkkk");
////				dd();
//				
//				
//				
//			
//				
//
//			}
//			@Override
//			public void onFailure(HttpException error, String msg) {
//				Log.e("Fail::::::::::::::::", msg);
//				System.out.println("shibaill....."+msg);		  
//			}
//		});
////		listAdapter = new ListViewAdapter();
////		mListUser.setAdapter(listAdapter);
//		//	创建适配器item的点击事件
//		mListUser.setOnItemClickListener(new OnItemClickListener() {
//
//
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				//		   该界面是点击item之后的页面，现在还没有实现
//				//			enterTopic();
//		
//
//			}
//
//		});
//		
//	}
//	
//	@Override
//	//  还没设置点击事件
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.ib_list_topic_arrows_left:
//			// 返回到圈子页面
//			finish();
//			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
//			break;
//		case R.id.ib_list_topic_publish:
//			//		评论按钮事件
//			TreadToastUtils.showToast(this, "仙剑奇侠传");
////			 点击进入发表
//					this.startActivity(new Intent(BaguaActivity.this,
//							PublishActivity.class));
//			break;
//		case R.id.ib_list_topic_inform:
//			// 点击进入消息界面
//			TreadToastUtils.showToast(this, "新消息");
//			break;
//
//		default:
//			break;
//		}
//	}
//

	}
	
}


