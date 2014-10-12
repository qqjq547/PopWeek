package com.hjq.ui;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
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
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.hjq.app.MyApplication;
import com.hjq.util.BMapUtil;
import com.hjq.week.R;
import com.hjq.widget.MyPoiOverlay;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity implements
		View.OnClickListener {
	LinearLayout menubtn, menubtn1;
	
	MyLocationMapView mMapView = null;
	
	
	private MapController mMapController = null;
	
	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位
	// 弹出泡泡图层
	private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用
	private TextView popupText = null;// 泡泡view
	private View viewCache = null;
	locationOverlay myLocationOverlay = null;
	private GeoPoint curGeoPoint;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content_frame);
		MyApplication app = (MyApplication) getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplicationContext());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(new MyApplication.MyGeneralListener());
		}
		menubtn = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		menubtn1 = (LinearLayout) findViewById(R.id.Linear_above_toHome2);
		menubtn.setOnClickListener(this);
		menubtn1.setOnClickListener(this);
		initMap();
		initLocation();
	}
	public void SearchNearBy(String seach) {
		// TODO Auto-generated method stub
		Log.e("hjq", "SearchNearBy="+curGeoPoint.getLatitudeE6()+","+curGeoPoint.getLongitudeE6());
		
	}

	public void initLocation() {
	

		//地图初始化
        mMapView = (MyLocationMapView)findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
      //创建 弹出泡泡图层
        createPaopao();
        
//    
//       
//        //定位图层初始化
//		myLocationOverlay = new locationOverlay(mMapView);
//		//设置定位数据
//	    myLocationOverlay.setData(locData);
//	    //添加定位图层
//		mMapView.getOverlays().add(myLocationOverlay);
//		myLocationOverlay.enableCompass();
//		//修改定位数据后刷新图层生效
//		mMapView.refresh();
//		modifyLocationOverlayIcon(null);
	}

	private void initMap() {
		// TODO Auto-generated method stub
		MyApplication app = (MyApplication) getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplication());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(new MyApplication.MyGeneralListener());
		}

		mMapView = (MyLocationMapView) findViewById(R.id.bmapView);
		mMapView.getController().enableClick(true);
		mMapView.getController().setZoom(12);

		// 初始化搜索模块，注册搜索事件监听
//		mSearch = new MKSearch();
//		mSearch.init(app.mBMapManager, new MKSearchListener() {
//			// 在此处理详情页结果
//			@Override
//			public void onGetPoiDetailSearchResult(int type, int error) {
//				if (error != 0) {
//					Toast.makeText(MapActivity.this, "抱歉，未找到结果",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(MapActivity.this, "成功，查看详情页面",
//							Toast.LENGTH_SHORT).show();
//				}
//			}

//			/**
//			 * 在此处理poi搜索结果
//			 */
//			public void onGetPoiResult(MKPoiResult res, int type, int error) {
//				// 错误号可参考MKEvent中的定义
//		
//			}
//
//			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
//					int error) {
//			}
//
//			public void onGetTransitRouteResult(MKTransitRouteResult res,
//					int error) {
//			}
//
//			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
//					int error) {
//			}
//
//			public void onGetAddrResult(MKAddrInfo res, int error) {
//			}
//
//			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
//			}
//            
//			/**
//			 * 更新建议列表
//			 */
//			@Override
//			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
////				 if ( res == null || res.getAllSuggestions() == null){
////				 return ;
////				 }
////				 sugAdapter.clear();
////				 for ( MKSuggestionInfo info : res.getAllSuggestions()){
////				 if ( info.key != null)
////				 sugAdapter.add(info.key);
////				 }
////				 sugAdapter.notifyDataSetChanged();
//
//			}
//
//			@Override
//			public void onGetShareUrlResult(MKShareUrlResult result, int type,
//					int error) {
//				// TODO Auto-generated method stub
//
//			}
//		});
	}

    public void modifyLocationOverlayIcon(Drawable marker){
    	//当传入marker为null时，使用默认图标绘制
    	myLocationOverlay.setMarker(marker);
    	//修改图层，需要刷新MapView生效
    	mMapView.refresh();
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Linear_above_toHome:
//			sm.showMenu();
			break;
		case R.id.Linear_above_toHome2:
//			sm.showSecondaryMenu();
			break;

		default:
			break;
		}
	}

    /**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao(){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        //泡泡点击响应回调
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.e("hjq", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
        MyLocationMapView.pop = pop;
	}
	
	  //继承MyLocationOverlay重写dispatchTap实现点击处理
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  			// TODO Auto-generated constructor stub
  		}
  		@Override
  		protected boolean dispatchTap() {
  			// TODO Auto-generated method stub
  			//处理点击事件,弹出泡泡
//  			popupText.setBackgroundResource(R.drawable.popup);
//			pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
//					8);	popupText.setText("我的位置");
//		
  			return true;
  		}	
  	}
  	 @Override
     protected void onPause() {
         mMapView.onPause();
         super.onPause();
     }
     
     @Override
     protected void onResume() {
         mMapView.onResume();
         super.onResume();
     }
     
     @Override
     protected void onDestroy() {
     	//退出时销毁定位
         mMapView.destroy();
         super.onDestroy();
     }
     
     @Override
     protected void onSaveInstanceState(Bundle outState) {
     	super.onSaveInstanceState(outState);
     	mMapView.onSaveInstanceState(outState);
     	
     }
     
     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
     	super.onRestoreInstanceState(savedInstanceState);
     	mMapView.onRestoreInstanceState(savedInstanceState);
     }
}
  	/**
  	 * 继承MapView重写onTouchEvent实现泡泡处理操作
  	 * @author hejin
  	 *
  	 */
  	class MyLocationMapView extends MapView{
  		static PopupOverlay   pop  = null;//弹出泡泡图层，点击图标使用
  		public MyLocationMapView(Context context) {
  			super(context);
  			// TODO Auto-generated constructor stub
  		}
  		public MyLocationMapView(Context context, AttributeSet attrs){
  			super(context,attrs);
  		}
  		public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
  			super(context, attrs, defStyle);
  		}
  		@Override
  	    public boolean onTouchEvent(MotionEvent event){
  			if (!super.onTouchEvent(event)){
  				//消隐泡泡
  				if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
  					pop.hidePop();
  			}
  			return true;
  		}

  	}

