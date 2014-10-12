package com.hjq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {
	 public static final String URL_LOGIN="http://engine.lezhuan.me/login.action";
	 public static final String URL_REGISTER="http://engine.lezhuan.me/register.action";
	 public static final String URL_TASKLIST="http://engine.lezhuan.me/tasklist.action";
	 public static final String URL_TASKREQUEST="http://engine.lezhuan.me/taskrequest.action";
	 public static final String URL_OPENAPP="http://engine.lezhuan.me/openapp.action";
	 public static final String URL_FINISHLIST="http://engine.lezhuan.me/finishlist.action";
	 public static final String URL_GATHER="http://engine.lezhuan.me/gather.action";
	 public static final String URL_EXCHANGE="http://engine.lezhuan.me/exchange.action";
	 public static final String URL_EXCHANGELIST="http://engine.lezhuan.me/exchangelist.action";
	 public static final String URL_TASKINFO="http://engine.lezhuan.me/tastinfo.action";
	 public static String post(String url,NameValuePair... nameValuePairs) {
		   HttpClient  httpClient=new DefaultHttpClient();
		   String msg="";
		   HttpPost post=new HttpPost(url);
		   List<NameValuePair> params =new ArrayList<NameValuePair>();
		   for (int i = 0; i < nameValuePairs.length; i++) {
			params.add(nameValuePairs[i]);
	    	}
		   try {
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response=httpClient.execute(post);
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
			   msg=EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return msg;        
		   }   
	 public static String get(String url) {
		   HttpClient  httpClient=new DefaultHttpClient();
		   String msg="";
		   HttpGet get=new HttpGet(url);
			HttpResponse response;
			try {
				response = httpClient.execute(get);
				HttpEntity entity=response.getEntity();
				if (entity!=null) {
					BufferedReader br=new BufferedReader(new InputStreamReader(entity.getContent()));
					String line=null;
					while ((line=br.readLine())!=null) {
					msg+=line;				
					}
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		   return msg;		         
		   } 
	public static String conn(String url){
		 String result = "";
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			if (conn.getResponseCode() != 200) {
				return "error";
			}
			conn.connect();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	        		conn.getInputStream()));
	        String lines;
	        while ((lines = reader.readLine()) != null) {
	        	result=result+lines;
	        }
	       
	        reader.close();
	        // 断开连接
	        conn.disconnect();
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
}
