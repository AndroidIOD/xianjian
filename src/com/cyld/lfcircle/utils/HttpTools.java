package com.cyld.lfcircle.utils;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;


import android.util.Log;

public class HttpTools {

 

    final static String LOG_TAG = "HttpTools";

    /**
     * 调用直接传JSONObject ，因服务端，有的必须是INT
     * 
     * @param pathName
     * @param jsonInfo
     * @return
     */
    public static Object CallService( String pathName, JSONObject jsonInfo) {
        try {

            HttpClient client = new DefaultHttpClient();
            
            HttpParams params = client.getParams();
            //设置超时
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            
            String t = pathName;
            HttpPost httpPost = new HttpPost(t);
            
            //设置参数
            ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("jsonInfo", jsonInfo.toString()));

            UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "utf-8");
            httpPost.setEntity(p_entity);
            
            //请求
           // Log.v("HttpTools" , t + " begin request " + SystemInfo.nowDateTime());
            HttpResponse response = client.execute(httpPost);
          //  Log.v("HttpTools" , t + " end request " + SystemInfo.nowDateTime());
            return convertStreamToString(response.getEntity().getContent());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage() + "..................", e);
            return "";
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        Log.i("123465489744156", sb.toString());
        return sb.toString();

    }
}
