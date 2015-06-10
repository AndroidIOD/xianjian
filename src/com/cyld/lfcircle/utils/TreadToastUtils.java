package com.cyld.lfcircle.utils;
import android.app.Activity;
import android.widget.Toast;
/**
 * 
 * 	吐司的utils  非主线程也可以吐司
 * @author 
 *
 */
public class TreadToastUtils {
	public static void showToast(final Activity context,final String msg){
		if("main".equals(Thread.currentThread().getName())){
			Toast.makeText(context, msg, 1).show();
		}else{
			context.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, msg, 1).show();
				}
			});
		}
	}
}
