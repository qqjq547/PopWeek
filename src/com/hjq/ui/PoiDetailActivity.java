package com.hjq.ui;

import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.hjq.week.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PoiDetailActivity extends Activity{
	GeoPoint geoPoint;
	TextView title_tv,addr_tv,phone_tv,post_tv;
	
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_poidetail);
	Intent mIntent=getIntent();
	title_tv=(TextView)findViewById(R.id.de_titile);
	addr_tv=(TextView)findViewById(R.id.de_address);
	phone_tv=(TextView)findViewById(R.id.de_phone);
	post_tv=(TextView)findViewById(R.id.de_postCode);
	title_tv.setText(mIntent.getStringExtra("name"));
	addr_tv.setText(mIntent.getStringExtra("address"));
	phone_tv.setText(mIntent.getStringExtra("phoneNum"));
	post_tv.setText(mIntent.getStringExtra("postCode"));
	
}
}
