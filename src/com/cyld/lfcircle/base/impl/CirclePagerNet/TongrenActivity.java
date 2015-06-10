package com.cyld.lfcircle.base.impl.CirclePagerNet;


import com.cyld.lfcircle.R;
import com.cyld.lfcircle.TopicActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TongrenActivity extends Activity /**implements OnClickListener*/ {
//	
	 DisplayImageOptions options = new DisplayImageOptions.Builder()  
     .showImageOnLoading(R.drawable.ic_launcher)  
     .showImageOnFail(R.drawable.ic_launcher)  
     .cacheInMemory(true)  
     .cacheOnDisk(true)  
     .bitmapConfig(Bitmap.Config.RGB_565)  
     .build();  
	 
	
   //全局变量，xml布局中的控件按钮
   public ImageView ib_list_topic_arrows_left ;
   public View      v ;
   public ImageView mImageUser ;
   public ImageView mImagePingLun ;
   public TextView  mTextUser ;
   public TextView  mTextPingLubNumber ;
   public TextView  mTextHuaTi ;
   public TextView  mTextHuaTiContent;
   public TextView  mTextContent ;
// 发表按钮
 private ImageButton ib_list_topic_publish;
   
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setRequestedOrientation(BIND_NOT_FOREGROUND);
	Intent intent = new Intent(TongrenActivity.this,TopicActivity.class);
	startActivity(intent);
//	setContentView(R.layout.circletongrenactivity);
//	ib_list_topic_arrows_left = (ImageView)findViewById(R.id.ib_list_topic_arrows_left);
//	ib_list_topic_arrows_left.setOnClickListener(this);	
//	ib_list_topic_arrows_left.setOnClickListener(this);
////	点击评论按钮
//	ib_list_topic_publish = (ImageButton) findViewById(R.id.ib_list_topic_publish);
////	点击时的评论按钮添加事件
//	ib_list_topic_publish.setOnClickListener(this);
//    }
//
//public void onClick(View v) {
//	
////	  获取item的id
//	switch (v.getId()) {
//	case R.id.ib_list_topic_arrows_left:
////		 点击返回到圈子页面
//		finish();
//		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
//		break;
//	case R.id.ib_list_topic_publish:
////		评论按钮事件
//		TreadToastUtils.showToast(this, "仙剑奇侠传");
////		 点击进入发表
//		this.startActivity(new Intent(TongrenActivity.this,
//				PublishActivity.class));
//		break;
//	case R.id.ib_list_topic_inform:
//		// 点击进入消息界面
//		TreadToastUtils.showToast(this, "新消息");
//		break;
//
//	default:
//		break;
//	}
}
  
}
