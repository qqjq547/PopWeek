package com.hjq.widget;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKSearch;
import com.hjq.ui.PoiDetailActivity;

public class MyPoiOverlay extends PoiOverlay {
    
    MKSearch mSearch;
    Activity mactivity;
    public MyPoiOverlay(Activity activity, MapView mapView, MKSearch search) {
        super(activity, mapView);
        mSearch = search;
        mactivity=activity;
    }

    @Override
    protected boolean onTap(int i) {
        super.onTap(i);
        MKPoiInfo info = getPoi(i);
//        Log.e("hjq","uid="+ info.uid);
//        Log.e("hjq","hasCaterDetails="+ info.hasCaterDetails);
//        Log.e("hjq","postCode="+ info.postCode);
//        Log.e("hjq","isPano="+ info.isPano);
//        Log.e("hjq","toString="+ info.toString());
        if (info.hasCaterDetails) {
            mSearch.poiDetailSearch(info.uid);
        }else {
			Intent mIntent=new Intent(mactivity, PoiDetailActivity.class);
			mIntent.putExtra("name", info.name);
			mIntent.putExtra("address", info.address);
			mIntent.putExtra("phoneNum", info.phoneNum);
			mIntent.putExtra("postCode", info.postCode);
			mIntent.putExtra("LatitudeE6", info.pt.getLatitudeE6());
			mIntent.putExtra("LongitudeE6", info.pt.getLongitudeE6());
			mactivity.startActivity(mIntent);
		}
        return true;
    }

    
}
