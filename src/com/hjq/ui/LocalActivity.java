package com.hjq.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
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
import com.hjq.adapter.DealAdapter;
import com.hjq.app.MyApplication;
import com.hjq.model.Deal;
import com.hjq.model.PoiItem;
import com.hjq.util.DianPingApiTool;
import com.hjq.util.ThreadPoolManager;
import com.hjq.week.R;
import com.hjq.widget.ExpandTabView;
import com.hjq.widget.ViewMiddle;
import com.hjq.widget.ViewRight;

public class LocalActivity extends Activity {

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewMiddle viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private LocationClient mLocClient;
	private MyLocationListenner myListener = new MyLocationListenner();
	private BDLocation locData = null;
	private boolean isOpenLoc=true;
	MKSearch mSearch;
	String SearchWord="团购";
	int sort=1;
	int pagerindex=1;
	String CityName="";
	String AreaName="";
	String category="";
	ArrayList<String> district_name=new ArrayList<String>();
    SparseArray<LinkedList<String>> neighborhoods=new SparseArray<LinkedList<String>>();
    ArrayList<String> category_name=new ArrayList<String>();
    SparseArray<LinkedList<String>> subcategories=new SparseArray<LinkedList<String>>();
    private ArrayList<Deal> dealitems=new ArrayList<Deal>();
	private DealAdapter mAdapter; 
	private ListView mListView;
	Handler netHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			String result=(String)msg.obj;
			switch (msg.what) {
			case 0://left
//				Toast.makeText(DealListActivity.this, result, Toast.LENGTH_LONG).show();
				Log.i("hjq", result);
				try {
					getAreaMsg(result);
					viewLeft.setData(district_name, neighborhoods);
					expandTabView.setTitle(CityName, 0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
             case 1://middle
//            	 Toast.makeText(DealListActivity.this, result, Toast.LENGTH_LONG).show();
 				Log.i("hjq", result);
 				try {
 					getCategoryMsg(result);
 					viewMiddle.setData(category_name, subcategories);
 				} catch (JSONException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
				break;
				case 2://find_buiness
					Log.i("hjq", "find_deals="+result);
				try {
					getitemMsg(result);
					mAdapter=new DealAdapter(LocalActivity.this, dealitems);
					mListView.setAdapter(mAdapter);					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deallist);
		initView();
		initTab();
		initListener();
		initLoction();
		requestLocClick();
		
	}

	private void initView() {
		
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		viewLeft = new ViewMiddle(this);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);
		mListView=(ListView)findViewById(R.id.pointinfoList);
	}

	private void initTab() {
		
		mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("位置");
		mTextArray.add("分类");
		mTextArray.add("来源");
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle("位置", 0);
		expandTabView.setTitle("分类", 1);
		expandTabView.setTitle(viewRight.getShowText(), 2);
		
	}
	private void initLoction(){
	    //定位初始化
		MyApplication app = (MyApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            app.mBMapManager.init(new MyApplication.MyGeneralListener());
        }
        mLocClient = new LocationClient( this );
        locData = new BDLocation();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    	mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {		
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetAddrResult(MKAddrInfo res, int error) {
				// TODO Auto-generated method stub
				if (error != 0) {
					String str = String.format("错误号：%d", error);
					Toast.makeText(LocalActivity.this, str, Toast.LENGTH_LONG).show();
					return;
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE){
					//反地理编码：通过坐标点检索详细地址及周边poi
					final String strInfo = res.addressComponents.city;
					Toast.makeText(LocalActivity.this, strInfo, Toast.LENGTH_LONG).show();
					final Map<String, String> paramMap = new HashMap<String, String>();
					CityName=strInfo.replace("市", "");
					paramMap.put("city",CityName);
					ThreadPoolManager.getInstance().addTask(new Runnable() {	
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String result=DianPingApiTool.requestApi(DianPingApiTool.get_regions_with_businesses,
									getResources().getString(R.string.app_key), 
									getResources().getString(R.string.app_secret), 
									paramMap);
							Message ss=new Message();
							ss.what=0;
							ss.obj=result;
							netHandler.sendMessage(ss);	
						}
					});
					ThreadPoolManager.getInstance().addTask(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String result=DianPingApiTool.requestApi(DianPingApiTool.get_categories_with_businesses,
									getResources().getString(R.string.app_key), 
									getResources().getString(R.string.app_secret), 
									paramMap);
							Message ss=new Message();
							ss.what=1;
							ss.obj=result;
							netHandler.sendMessage(ss);		
						}
					});
					final Map<String, String> dealparamMap = new HashMap<String, String>();
					dealparamMap.put("city",CityName);
					dealparamMap.put("latitude",String.valueOf((float)locData.getLatitude()));
					dealparamMap.put("longitude",String.valueOf((float)locData.getLongitude()));
					dealparamMap.put("page",String.valueOf(pagerindex));
					dealparamMap.put("sort", String.valueOf(sort));
	        	ThreadPoolManager.getInstance().addTask(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String result=DianPingApiTool.requestApi(DianPingApiTool.find_deals,
									getResources().getString(R.string.app_key), 
									getResources().getString(R.string.app_secret), 
									dealparamMap);
							Message ss=new Message();
							ss.what=2;
							ss.obj=result;
							netHandler.sendMessage(ss);		
						}
					});
					
				}
			}
		});
	}
	  public void requestLocClick(){
//	    	isRequest = true;
	        mLocClient.requestLocation();
	        Toast.makeText(LocalActivity.this, "正在定位……", Toast.LENGTH_SHORT).show();
	    }
	private void initListener() {
		
		viewLeft.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String showText) {
				AreaName=showText;
				onRefresh(viewLeft, showText);
			}
		});
		
		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
			
			@Override
			public void getValue(String showText) {
				category=showText;
				onRefresh(viewMiddle,showText);
				
			}
		});
		
		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				sort=Integer.parseInt(showText);
				onRefresh(viewRight, showText);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
					String title= dealitems.get(position).getTitle();
					String url=dealitems.get(position).getDeal_h5_url();
					Intent mIntent=new Intent(LocalActivity.this, WebActivity.class);
			        mIntent.putExtra("title", title);
			        mIntent.putExtra("url", url);
					startActivity(mIntent);
			}
			
		});
	}
	
	private void onRefresh(View view, String showText) {
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(LocalActivity.this, showText, Toast.LENGTH_SHORT).show();
		SearchWord=showText;
		Log.e("hjq", "showText="+showText);
//		find_businesByloc(locData, category, SearchWord, 1, 1);
//		mSearch.poiSearchNearBy(SearchWord, 
//         		new GeoPoint((int)(locData.getLatitude()*1e6), (int)(locData.getLongitude()*1e6)),5000);
	   
	}
//	private void find_businesByloc(final BDLocation locData,final String category,final String keyword,final int sort,final int page){
//		ThreadPoolManager.getInstance().addTask(new Runnable() {	
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				final Map<String, String> paramMap = new HashMap<String, String>();
//				paramMap.put("latitude", String.valueOf(locData.getLatitude()));
//				paramMap.put("longitude", String.valueOf(locData.getLongitude()));
//				paramMap.put("category", category);
//				paramMap.put("keyword", keyword);
//				paramMap.put("platform", "2");
//				paramMap.put("has_deal", "1");
//				paramMap.put("sort", String.valueOf(sort));
//				paramMap.put("page",String.valueOf(page));
//				String result=DianPingApiTool.requestApi(DianPingApiTool.find_businesses,
//						getResources().getString(R.string.key), 
//						getResources().getString(R.string.value), 
//						paramMap);
//				Message ss=new Message();
//				ss.what=2;
//				ss.obj=result;
//				netHandler.sendMessage(ss);	
//			}
//		});	   
//	}
	 public static Deal JsonToDeal(JSONObject json) throws JSONException{
		   String deal_id=json.getString("deal_id");
		    String	title=json.getString("title");// 团购标题
		    String description=json.getString("description");	//团购描述
		    String city=json.getString("city");	 	// 城市名称，city为＂全国＂表示全国单，其他为本地单，城市范围见相关API返回结果
		    float list_price=(float)json.getDouble("list_price");	 //	 团购包含商品原价值
		    float current_price=(float)json.getDouble("current_price");	// 	 团购价格
		    String regions=json.getString("regions");	 // 团购适用商户所在商区
			String categories=json.getString("categories");	 	// 团购所属分类
			int purchase_count=json.getInt("purchase_count");	 	 //团购当前已购买数
			String publish_date=json.getString("publish_date");	 	// 团购发布上线日期
			String details="";	 	// 团购详情
			String purchase_deadline=json.getString("purchase_deadline");	 	// 团购单的截止购买日期
			String image_url=json.getString("image_url");	 	// 团购图片链接，最大图片尺寸450×280
			String s_image_url=json.getString("s_image_url");	 	// 小尺寸团购图片链接，最大图片尺寸160×100
			String more_image_urls="";	 	 //更多大尺寸图片
			String more_s_image_urls="";	 	// 更多小尺寸图片
			int is_popular=0;	 	 //是否为热门团购，0：不是，1：是
		    String notice="";	 	 //重要通知(一般为团购信息的临时变更)
		    String deal_url=json.getString("deal_url");	 	// 团购Web页面链接，适用于网页应用
		    String deal_h5_url=json.getString("deal_h5_url");	 	// 团购HTML5页面链接，适用于移动应用和联网车载应用
		    float commission_ratio=(float)json.getDouble("commission_ratio");	 	 //当前团单的佣金比例
		    int bus_num=json.getJSONArray("businesses").length();
		    String businesses_name="";	 	// 商户名
		    int businesses_id=0; 	// 商户ID
		    String businesses_address="";	 	 //商户地址
		    float businesses_latitude=0;	 	 //商户纬度
		    float businesses_longitude=0;	 	 //商户经度
			String businesses_url="";	
		    if (bus_num>0) {
		    	JSONObject businesses=json.getJSONArray("businesses").getJSONObject(0);	 	// 团购所适用的商户列表
		  	     businesses_name=businesses.getString("name");	 	// 商户名
		  	     businesses_id=businesses.getInt("id");	 	// 商户ID
		  	     businesses_address="";	 	 //商户地址
		  	     businesses_latitude=0;	 	 //商户纬度
		  	     businesses_longitude=0;	 	 //商户经度
		  		 businesses_url=businesses.getString("url");	
		   }
			return new Deal(deal_id, title, description, city, list_price,
					current_price, regions, categories, purchase_count,
					publish_date, details, purchase_deadline, image_url,
					s_image_url, more_image_urls, more_s_image_urls,
					is_popular, notice, deal_url, deal_h5_url, commission_ratio, 
					bus_num,
					businesses_name, businesses_id, businesses_address,
					businesses_latitude, businesses_longitude, businesses_url);
	   }
	private void getitemMsg(String jsonstr) throws JSONException{
		dealitems.clear();
		JSONObject json=new JSONObject(jsonstr);
		if (json.getString("status").equals("OK")) {
			JSONArray array=json.getJSONArray("deals");
			if (array!=null&&array.length()>0) {
				dealitems.clear();
				for (int i = 0; i < array.length(); i++) {
					dealitems.add(JsonToDeal((JSONObject)array.get(i)));
				}
//				dealitems=Deal.JsonArrayToList(array);
			}
		}
	}
	private void  getAreaMsg(String jsonstr) throws JSONException {
		district_name.clear();
		neighborhoods.clear();
		JSONObject json=new JSONObject(jsonstr);
		if (json.getString("status").equals("OK")) {
			CityName=((JSONObject)json.getJSONArray("cities").get(0)).getString("city_name");
			JSONArray array=((JSONObject)json.getJSONArray("cities").get(0)).getJSONArray("districts");
			for (int i = 0; i < array.length(); i++) {
				district_name.add(((JSONObject)array.get(i)).getString("district_name"));
				JSONArray array2=((JSONObject)array.get(i)).getJSONArray("neighborhoods");
				Log.e("hjq", array2.toString());
				LinkedList<String> aList=new LinkedList<String>();
				for (int j = 0; j < array2.length(); j++) {
					aList.add(array2.get(j).toString());
				}
				neighborhoods.put(i, aList);
			}
		}
		
	}
	private void  getCategoryMsg(String jsonstr) throws JSONException {
		category_name.clear();
		subcategories.clear();
		JSONObject json=new JSONObject(jsonstr);
		if (json.getString("status").equals("OK")) {
//			CityName=((JSONObject)json.getJSONArray("categories").get(0)).getString("category_name");
			JSONArray array=json.getJSONArray("categories");
			for (int i = 0; i < array.length(); i++) {
				category_name.add(((JSONObject)array.get(i)).getString("category_name"));
				JSONArray array2=((JSONObject)array.get(i)).getJSONArray("subcategories");
				Log.e("hjq", array2.toString());
				LinkedList<String> aList=new LinkedList<String>();
				for (int j = 0; j < array2.length(); j++) {
					aList.add(((JSONObject)array2.get(j)).getString("category_name"));
				}
				subcategories.put(i, aList);
			}
		}
		
	}
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void onBackPressed() {
		if (!expandTabView.onPressBack()) {
			finish();
		}
		
	}
	  @Override
	     protected void onDestroy() {
	     	//退出时销毁定位
	         if (mLocClient != null)
	             mLocClient.stop();
	         super.onDestroy();
	     }
	  /**
	     * 定位SDK监听函数
	     */
	    public class MyLocationListenner implements BDLocationListener {
	    	
	        @Override
	        public void onReceiveLocation(BDLocation location) {
	            if (location == null){
	            	Log.e("hjq","location == null");
	                return ;
	            }
	            if (isOpenLoc) {
	            	Log.e("hjq","location ="+locData.getAddrStr());
	 	            locData = location;
	                isOpenLoc=!isOpenLoc;
	                GeoPoint mGeoPoint=new GeoPoint((int)(locData.getLatitude()*1e6), (int)(locData.getLongitude()*1e6));
	                mSearch.poiSearchNearBy(SearchWord, 
	                		mGeoPoint,5000);
	    			//反Geo搜索
	    			mSearch.reverseGeocode(mGeoPoint);
	            }

	        }
	        
	        public void onReceivePoi(BDLocation poiLocation) {
	            if (poiLocation == null){
	                return ;
	            }
	        }
	    }

}
