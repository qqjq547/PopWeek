package com.hjq.util;

import java.security.PublicKey;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class BaiduContent {
private Context mContext;
private MKSearch mSearch = null; 
private BMapManager mapManager;
public BaiduContent(Context context) {
	super();
	mContext = context;
	mapManager=new BMapManager(context);
	mapManager.init(new MKGeneralListener() {
		
		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	 mSearch = new MKSearch();
     mSearch.init(mapManager, new MKSearchListener(){
         //在此处理详情页结果
         @Override
         public void onGetPoiDetailSearchResult(int type, int error) {
             if (error != 0) {
                 Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
             }
             else {
                 Toast.makeText(mContext, "成功，查看详情页面", Toast.LENGTH_SHORT).show();
             }
         }

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			// TODO Auto-generated method stub
			  if (error != 0 || res == null) {
                  Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
                  return;
              }
              // 将地图移动到第一个POI中心点
              if (res.getCurrentNumPois() > 0) {
                  // 将poi结果显示到地图上
//                  MKPoiInfo mk=res.getAllPoi().get(0);

                  //当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
                  for( MKPoiInfo info : res.getAllPoi() ){
                  	if ( info.pt != null ){
                  		Log.e("hjq","address="+info.address+",uid="+info.uid+",pt="
                  	     +info.pt.getLatitudeE6()+" "+info.pt.getLongitudeE6()
                  	     +"name="+info.name);
                  		break;
                  	}
                  }
              } else if (res.getCityListNum() > 0) {
              	//当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                  String strInfo = "在";
                  for (int i = 0; i < res.getCityListNum(); i++) {
                      strInfo += res.getCityListInfo(i).city;
                      strInfo += ",";
                  }
                  strInfo += "找到结果";
                  Toast.makeText(mContext, strInfo, Toast.LENGTH_LONG).show();
              }
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
});
}
public void SearchInCity(String city,String key){
	mSearch.poiSearchInCity(city, key);
}
}
