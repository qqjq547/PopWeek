package com.hjq.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil {
    public static void showDialog(Context context,String title,String msg,String leftbtn,String rightbtn,
    		OnClickListener LeftOnClickListener,OnClickListener RightOnClickListener,boolean cancelable) {
		AlertDialog.Builder builder=new AlertDialog.Builder(context).setCancelable(cancelable);
	     builder.setTitle(title)
	    .setMessage(msg)
		.setNegativeButton(leftbtn, LeftOnClickListener)
		.setPositiveButton(rightbtn, RightOnClickListener).create().show();
	}
    public static void showDialog(Context context,int titleid,int msgid,int leftbtnid,int rightbtnid,
    		OnClickListener LeftOnClickListener,OnClickListener RightOnClickListener,boolean cancelable) {
		AlertDialog.Builder builder=new AlertDialog.Builder(context).setCancelable(false);
		 builder.setTitle(titleid);
		 builder.setMessage(msgid)
		.setNegativeButton(leftbtnid, LeftOnClickListener)
		.setPositiveButton(rightbtnid, RightOnClickListener).create().show();
	}
    public static void showNoTitleDialog(Context context,String msg,String leftbtn,String rightbtn,
    		OnClickListener LeftOnClickListener,OnClickListener RightOnClickListener,boolean cancelable) {
		AlertDialog.Builder builder=new AlertDialog.Builder(context).setCancelable(cancelable); 
		builder.setMessage(msg)
		.setNegativeButton(leftbtn, LeftOnClickListener)
		.setPositiveButton(rightbtn, RightOnClickListener).create().show();
	}
    public static void showNoTitleDialog(Context context,int msgid,int leftbtnid,int rightbtnid,
    		OnClickListener LeftOnClickListener,OnClickListener RightOnClickListener,boolean cancelable) {
		AlertDialog.Builder builder=new AlertDialog.Builder(context).setCancelable(cancelable);
		 builder.setMessage(msgid)
		.setNegativeButton(leftbtnid, LeftOnClickListener)
		.setPositiveButton(rightbtnid, RightOnClickListener).create().show();
	}
}
