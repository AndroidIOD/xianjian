package com.cyld.lfcircle.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.cyld.lfcircle.R;

public class PopupWindowExpression  extends PopupWindow{
	Context context;
	
	public PopupWindowExpression(Context context) {
		
		this.context=context;
	}
	
	
	public void kk(){
		LayoutInflater inflater = (LayoutInflater) context
		 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View conentView = inflater.inflate(R.layout.popwindow_item, null);
		    this.setContentView(conentView);
		     this.setWidth(LayoutParams.FILL_PARENT);
	        //设置SelectPicPopupWindow弹出窗体的高  
	        this.setHeight(200);  
	        //设置SelectPicPopupWindow弹出窗体可点击  
	        this.setFocusable(true);  
	       
			this.setOutsideTouchable(true);
			this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	        //实例化一个ColorDrawable颜色为半透明  
			
			this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.arrows_left));
	        
	} 
   
 
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			
			this.showAsDropDown(parent,0,0);
		} else {
			this.dismiss();
		}
	}
	
 
	
	
	
	
}
