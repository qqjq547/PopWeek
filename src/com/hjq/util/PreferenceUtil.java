package com.hjq.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * 记录用户名，密码之类的首选项
 *
 */
public class PreferenceUtil {
	private static PreferenceUtil preference = null;
	private SharedPreferences sharedPreference;
	private String packageName = "";
	
	private static final String USERNAME = "username"; //登录名
	private static final String PASSWORD = "password";  //密码
	private static final String REMINDWORD = "remindword"; //是否保留密码
	private static final String AUTOLOGIN = "autologin";
	private static final String TIMES = "times";
	private static final String UID = "uid";
	private static final String CITY = "city";
	
	public static synchronized PreferenceUtil getInstance(Context context){
		if(preference == null)
			preference = new PreferenceUtil(context);
		return preference;
	}
	
	
	public PreferenceUtil(Context context){
		packageName = context.getPackageName() + "_preferences";
		sharedPreference = context.getSharedPreferences(
				packageName, context.MODE_PRIVATE);
	}
	
	
	public String getUserName(){
		String loginName = sharedPreference.getString(USERNAME, "");
		return loginName;
	}
	
	
	public void setUserName(String username){
		Editor editor = sharedPreference.edit();
		editor.putString(USERNAME, username);
		editor.commit();
	}
	
	
	public String getPassword(){
		String password = sharedPreference.getString(PASSWORD, "");
		return password;
	}
	
	
	public void setPassword(String password){
		Editor editor = sharedPreference.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}
	
	
	public boolean isRemindWord(){
		Boolean isSavePwd = sharedPreference.getBoolean(REMINDWORD, false);
		return isSavePwd;
	}
	
	
	public void setRemindWord(Boolean isSave){
		Editor edit = sharedPreference.edit();
		edit.putBoolean(REMINDWORD, isSave);
		edit.commit();
	}
	public boolean isAutoLogin(){
		Boolean isautologin = sharedPreference.getBoolean(AUTOLOGIN, false);
		return isautologin;
	}
	
	
	public void setAutoLogin(Boolean isAuto){
		Editor edit = sharedPreference.edit();
		edit.putBoolean(AUTOLOGIN, isAuto);
		edit.commit();
	}
	public int getTimes(){
		int times = sharedPreference.getInt(TIMES, 0);
		return times;
	}
	
	
	public void setTimes(int times){
		Editor edit = sharedPreference.edit();
		edit.putInt(TIMES, times);
		edit.commit();
	}
	public String getUid(){
		String uid = sharedPreference.getString(UID, "");
		return uid;
	}
	
	
	public void setUid(String uid){
		Editor edit = sharedPreference.edit();
		edit.putString(UID, uid);
		edit.commit();
	}
	public String getCity(){
		String uid = sharedPreference.getString(CITY, "");
		return uid;
	}
	
	
	public void setCity(String city){
		Editor edit = sharedPreference.edit();
		edit.putString(CITY, city);
		edit.commit();
	}
}

