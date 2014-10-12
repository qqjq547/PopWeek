package com.hjq.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bodong.dianjinweb.DianJinPlatform;
import com.bodong.dianjinweb.banner.DianJinBanner;
import com.bodong.dianjinweb.listener.AppActiveListener;
import com.bodong.dianjinweb.listener.ChannelListener;
import com.hjq.model.Deal;
import com.hjq.model.MainChild;
import com.hjq.model.MainGroup;
import com.hjq.util.DianPingApiTool;
import com.hjq.util.ImageLoaderUtil;
import com.hjq.util.PreferenceUtil;
import com.hjq.util.QuitPopAd;
import com.hjq.util.ThreadPoolManager;
import com.hjq.week.R;
import com.hjq.widget.PinnedHeaderExpandableListView;
import com.hjq.widget.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.hjq.widget.SlideWall;
import com.hjq.widget.StickyLayout;
import com.hjq.widget.StickyLayout.OnGiveUpTouchEventListener;

public class MainActivity extends Activity implements
        View.OnClickListener,
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        OnHeaderUpdateListener, OnGiveUpTouchEventListener {
    private PinnedHeaderExpandableListView expandableListView;
    private StickyLayout stickyLayout;
    private ImageButton dealLayout,dianpLayout,activityLayout,appLayout;
    private TextView cityname;
    private ArrayList<MainGroup> groupList=new ArrayList<MainGroup>();
    private ArrayList<List<MainChild>> childList=new ArrayList<List<MainChild>>();
    private ArrayList<String> deal_ids=new ArrayList<String>();
    private ArrayList<Deal> deals=new ArrayList<Deal>();
    private ArrayList<Deal> coupons=new ArrayList<Deal>();
    private ArrayList<Deal> reservations=new ArrayList<Deal>();
    private MyexpandableListAdapter adapter;
    private int[] menu_titleid=new int[]{
    		R.string.listgroup1,
    		R.string.listgroup2,
    		R.string.listgroup3};
    private Runnable getnewdeal_idRun,getnewdealRun,getcouponsRun,getreservationRun;
    public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	private MyLocationListener mMyLocationListener;
	private BDLocation mLocation;
	public String province;
	public String city;
	private View slidingDrawerView;
	private DianJinBanner banner ;
    Handler mHandler=new Handler(){
    	public void handleMessage(Message msg) {
    		String result=(String)msg.obj;
    		Log.i("hjq", result);
    		try {
			JSONObject json=new JSONObject(result);
    		switch (msg.what) {
			case 1:
				JSONArray array=(JSONArray)json.getJSONArray("id_list");
				if (array!=null&&array.length()>0) {
					deal_ids.clear();
					int length=array.length()>40?40:array.length();
					for (int i = 0; i <length ; i++) {
						deal_ids.add(array.get(i).toString());
					}
					
					getdailydeal();
				}
				break;
			case 2:
				JSONArray  dealarray=(JSONArray)json.getJSONArray("deals");
				if (dealarray!=null&&dealarray.length()>0) {
					deals.clear();
					deals=Deal.JsonArrayToDeal(dealarray);
					initExpandableList();
				}
				break;
    		case 3:
				JSONArray  couponarray=(JSONArray)json.getJSONArray("coupons");
				if (couponarray!=null&&couponarray.length()>0) {
					coupons.clear();
					coupons=Deal.JsonArrayToCoupon(couponarray);
					initExpandableList();
				}
				break;
    		case 4:
    			JSONArray  reservationarray=(JSONArray)json.getJSONArray("businesses");
				if (reservationarray!=null&&reservationarray.length()>0) {
					reservations.clear();
					reservations=Deal.JsonArrayToReservation(reservationarray);
					initExpandableList();
				}
				break;
			}
    		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	};
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    	DianJinPlatform.initialize(this, 59443,
				"c22d0712a36f4f1ac08f08fcbfe0f998", 1001);
    	DianJinPlatform.requestChannelEnable(getApplicationContext(),
				new ChannelListener() {

					@Override
					public void onSuccess(boolean enable) {
						Log.e("hjq", "在线参数，是否在该渠道显示广告=" + enable);
					}

					@Override
					public void onError(int errorCode, String errorMessage) {
						Log.e("hjq","在线参数获取失败，请检查网络！");
					}
				});

		DianJinPlatform.setAppActivedListener(new AppActiveListener() {

			@Override
			public void onSuccess(long reward) {
				Toast.makeText(MainActivity.this, "激活成功，奖励金额为：" + reward,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(int errorCode, String errorMessage) {
				switch (errorCode) {
				case DianJinPlatform.DIANJIN_NET_ERROR:// 网络不稳定
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;
				case DianJinPlatform.DIANJIN_DUPLICATE_ACTIVATION:// 重复激活
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;

				case DianJinPlatform.DIANJIN_ADVERTSING_EXPIRED:// 应用已下架
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;

				case DianJinPlatform.DIANJIN_ACTIVATION_FAILURE:// 激活失败
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
			}
		});
        AppConnect.getInstance("8ced31c260406b9dbd2b40fc1618e6c2", "waps", this);
    	AppConnect.getInstance(this).initAdInfo();
        slidingDrawerView = SlideWall.getInstance().getView(this);
    	if(slidingDrawerView != null){
    		this.addContentView(slidingDrawerView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    	}
         initView();
         initSDk();
    }
   
	private void initView() {
		// TODO Auto-generated method stub
		  expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
	      stickyLayout = (StickyLayout)findViewById(R.id.sticky_layout);
	      dealLayout=(ImageButton)findViewById(R.id.dealbtn);
	      dianpLayout=(ImageButton)findViewById(R.id.dianpbtn);
	      activityLayout=(ImageButton)findViewById(R.id.activitybtn);
	      appLayout=(ImageButton)findViewById(R.id.appbtn);
	      cityname=(TextView)findViewById(R.id.cityname);
	      dealLayout.setOnClickListener(this);
	      dianpLayout.setOnClickListener(this);
	      activityLayout.setOnClickListener(this);
	      appLayout.setOnClickListener(this); 
	      banner = (DianJinBanner) findViewById(R.id.dianJinBaaner);
	}

	private void initExpandableList() {
		initData();

        adapter = new MyexpandableListAdapter(this);
        expandableListView.setAdapter(adapter);

        // 展开所有group
        for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnHeaderUpdateListener(this);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        stickyLayout.setOnGiveUpTouchEventListener(this);
	}

    private void initRun() {
		// TODO Auto-generated method stub
    	getnewdeal_idRun=new Runnable() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("city", city);
				SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
				 String date=dateformat1.format(new Date());
				 Log.e("hjq", "date:"+date);
				paramMap.put("date", date);
				String result=DianPingApiTool.requestApi(DianPingApiTool.get_daily_new_id_list,
						getResources().getString(R.string.app_key), 
						getResources().getString(R.string.app_secret), 
						paramMap);
				Message ss=new Message();
				ss.what=1;
				ss.obj=result;
				mHandler.sendMessage(ss);	
			}
		};
		ThreadPoolManager.getInstance().addTask(getnewdeal_idRun);
	}

	/***
     * InitData
     */
    void initData() {
        groupList = new ArrayList<MainGroup>();
        MainGroup group = null;
        for (int i = 0; i < menu_titleid.length; i++) {
            group = new MainGroup();
            group.setTitle(getResources().getString(menu_titleid[i]));
            groupList.add(group);
        }

        childList = new ArrayList<List<MainChild>>();
        for (int i = 0; i < groupList.size(); i++) {
            ArrayList<MainChild> childTemp;
            if (i == 0) {
                childTemp = new ArrayList<MainChild>();
                for (int j = 0; j < deals.size(); j++) {
                	Deal mDeal=deals.get(j);
                	String title=mDeal.getTitle();
                	String des=mDeal.getDescription();
                	String url=mDeal.getImage_url();
                	String count="￥"+mDeal.getCurrent_price();
                    MainChild child = new MainChild(title,des,url,count);
                    childTemp.add(child);
                }
                childList.add(childTemp);
            } else if (i == 1) {
                childTemp = new ArrayList<MainChild>();
                for (int j = 0; j < coupons.size(); j++) {
                	Deal mDeal=coupons.get(j);
                	String title=mDeal.getTitle();
                	String des=mDeal.getDescription();
                	String url=mDeal.getImage_url();
                	String count="￥"+mDeal.getCurrent_price();
                    MainChild child = new MainChild(title,des,url,count);
                    childTemp.add(child);
                }
                childList.add(childTemp);
            } else if (i == 2) {
                childTemp = new ArrayList<MainChild>();
                for (int j = 0; j < reservations.size(); j++) {
                	Deal mDeal=reservations.get(j);
                	String title=mDeal.getTitle();
                	String des=mDeal.getDescription();
                	String url=mDeal.getImage_url();
                	String count="￥"+mDeal.getCurrent_price();
                    MainChild child = new MainChild(title,des,url,count);
                    childTemp.add(child);
                }
                childList.add(childTemp);
            }
        }
    }
   public void getdailydeal(){
	   getnewdealRun=new Runnable() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final Map<String, String> paramMap = new HashMap<String, String>();
				String deal_idstr="";
				for (int i = 0; i < deal_ids.size(); i++) {
					deal_idstr=deal_idstr+deal_ids.get(i)+",";
				}
				deal_idstr=deal_idstr.substring(0, deal_idstr.length()-1);
				paramMap.put("deal_ids", deal_idstr);		
				String result=DianPingApiTool.requestApi(DianPingApiTool.get_batch_deals_by_id,
						getResources().getString(R.string.app_key), 
						getResources().getString(R.string.app_secret), 
						paramMap);
				Message ss=new Message();
				ss.what=2;
				ss.obj=result;
				mHandler.sendMessage(ss);	
			}
		};
		ThreadPoolManager.getInstance().addTask(getnewdealRun);
   }
   public void getcoupons(){
	   getcouponsRun=new Runnable() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("city", city);		
				paramMap.put("limit", "40");
				String result=DianPingApiTool.requestApi(DianPingApiTool.find_coupons,
						getResources().getString(R.string.app_key), 
						getResources().getString(R.string.app_secret), 
						paramMap);
				Message ss=new Message();
				ss.what=3;
				ss.obj=result;
				mHandler.sendMessage(ss);	
			}
		};
		ThreadPoolManager.getInstance().addTask(getcouponsRun);
   }
   public void getreservations(){
	   getreservationRun=new Runnable() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final Map<String, String> paramMap = new HashMap<String, String>();
				SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
				String date=dateformat.format(new Date());
				paramMap.put("reservation_date", date);	
				SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
				String time=timeformat.format(new Date());
				paramMap.put("reservation_time", time);	
				paramMap.put("number_of_people", "1");	
				paramMap.put("city", city);		
				paramMap.put("limit", "40");
				String result=DianPingApiTool.requestApi(DianPingApiTool.find_businesses_with_reservations,
						getResources().getString(R.string.app_key), 
						getResources().getString(R.string.app_secret), 
						paramMap);
				Message ss=new Message();
				ss.what=4;
				ss.obj=result;
				mHandler.sendMessage(ss);	
			}
		};
		ThreadPoolManager.getInstance().addTask(getreservationRun);
   }
    /***
     * 数据源
     * 
     * @author Administrator
     * 
     */
    class MyexpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MyexpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        // 返回父列表个数
        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        // 返回子列表个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {

            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group, null);
                groupHolder.textView = (TextView) convertView
                        .findViewById(R.id.group);
                groupHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.textView.setText(((MainGroup) getGroup(groupPosition))
                    .getTitle());
            if (isExpanded)// ture is Expanded or false is not isExpanded
                groupHolder.imageView.setImageResource(R.drawable.expanded);
            else
                groupHolder.imageView.setImageResource(R.drawable.collapse);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.child, null);

                childHolder.textTitle = (TextView) convertView
                        .findViewById(R.id.title);
                childHolder.textDes = (TextView) convertView
                        .findViewById(R.id.des);
                childHolder.textCount = (TextView) convertView
                        .findViewById(R.id.count);
                childHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }

            childHolder.textTitle.setText(((MainChild) getChild(groupPosition,
                    childPosition)).getTitle());
            childHolder.textDes.setText(String.valueOf(((MainChild) getChild(
                    groupPosition, childPosition)).getDescription()));
            childHolder.textCount.setText(((MainChild) getChild(groupPosition,
                    childPosition)).getCount());
            ImageLoaderUtil.displayImage(((MainChild) getChild(groupPosition,
                    childPosition)).getImage_url(), childHolder.imageView, MainActivity.this);
//            childHolder.imageView.setText();
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v,
            int groupPosition, final long id) {

        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {
        Toast.makeText(MainActivity.this,
                childList.get(groupPosition).get(childPosition).getTitle(), 1)
                .show();
        if (groupPosition==0) {
        	String title= childList.get(groupPosition).get(childPosition).getTitle();
			String url=deals.get(childPosition).getDeal_h5_url();
			Intent mIntent=new Intent(MainActivity.this, WebActivity.class);
	        mIntent.putExtra("title", title);
	        mIntent.putExtra("url", url);
			startActivity(mIntent);
		}else if(groupPosition==1){
			String title= childList.get(groupPosition).get(childPosition).getTitle();
			String url=coupons.get(childPosition).getDeal_h5_url();
			Intent mIntent=new Intent(MainActivity.this, WebActivity.class);
	        mIntent.putExtra("title", title);
	        mIntent.putExtra("url", url);
			startActivity(mIntent);
		}else if(groupPosition==2){
			String title= childList.get(groupPosition).get(childPosition).getTitle();
			String url=reservations.get(childPosition).getDeal_h5_url();
			Intent mIntent=new Intent(MainActivity.this, WebActivity.class);
	        mIntent.putExtra("title", title);
	        mIntent.putExtra("url", url);
			startActivity(mIntent);
		}

        return false;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textTitle;
        TextView textDes;
        TextView textCount;
        ImageView imageView;
    }

    @Override
    public View getPinnedHeader() {
        View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.group, null);
        headerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        MainGroup firstVisibleGroup = (MainGroup) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup.getTitle());
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (expandableListView.getFirstVisiblePosition() == 0) {
            View view = expandableListView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dealbtn:
//			startActivity(new Intent(MainActivity.this, DealListActivity.class));
			Intent mIntent=new Intent(MainActivity.this, WebActivity.class);
	        mIntent.putExtra("title", "吃喝玩乐");
	        String url="http://lite.m.dianping.com/B+-L57yjXx?category=全部分类";
	        if (mLocation!=null) {
	        	url=url+"&longitude="+mLocation.getLongitude()+"&latitude="+mLocation.getLatitude();
			}
	        mIntent.putExtra("url",url );
			startActivity(mIntent);
			break;
		case R.id.dianpbtn:
			startActivity(new Intent(MainActivity.this, CommentActivity.class));
			break;
		case R.id.activitybtn:
			startActivity(new Intent(MainActivity.this, EventActivity.class));
			break;
		case R.id.appbtn:
			DianJinPlatform.showOfferWall(MainActivity.this);
			break;
		default:
			break;
		}
	}
	private void initSDk() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(this);

		try {
			LocationClientOption option = new LocationClientOption();
			option.setLocationMode(LocationMode.Hight_Accuracy);
			option.setCoorType("bd09ll");
			option.setScanSpan(0);
			option.setNeedDeviceDirect(true);
			option.setIsNeedAddress(true);
			mLocationClient.setLocOption(option);
			Log.e("hjq", "mLocationInit=" + true);
		} catch (Exception e) {
			Log.e("hjq", "mLocationInit=" + false);
			e.printStackTrace();
		}
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			String addr = location.getAddrStr();
			mLocation=location;
			if (addr != null) {
				
				String[] adds = getLocalMsg(addr);
				Log.i("hjq", "location.getAddrStr()=" + addr);
				Log.i("hjq", "localMsg=" + getLocalMsg(addr)[0]);
				Log.i("hjq", "localMsg=" + getLocalMsg(addr)[1]);
				province = adds[0];
				city = adds[1].replace("市", "");
				cityname.setText(city);
				PreferenceUtil.getInstance(MainActivity.this).setCity(city);
				initRun();
				getcoupons();
				getreservations();
			}else {
				province = "";
				city = "";
				Log.e("hjq", "没有找到当前位置！");
			}
			Toast.makeText(MainActivity.this,province+":"+city, 1).show();
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
		}
	}
	public String[] getLocalMsg(String address) {
		int first, second;
		String[] str = new String[2];
		first = address.indexOf(getResources().getString(R.string.province)) + 1;
		if (first == 0) {
			first = address.indexOf(getResources().getString(R.string.city)) + 1;
			str[0] = address.substring(0, first);
			str[1] = str[0];
		} else {
			second = address.indexOf(getResources().getString(R.string.city)) + 1;
			str[0] = address.substring(0, first);
			str[1] = address.substring(first, second);
		}
		return str;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(SlideWall.getInstance().slideWallDrawer != null
					&& SlideWall.getInstance().slideWallDrawer.isOpened()){				
				// 如果抽屉式应用墙展示中，则关闭抽屉
				SlideWall.getInstance().closeSlidingDrawer();
			}else{
				// 调用退屏广告
				QuitPopAd.getInstance().show(this);
			}
			
		}
		return true;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
}
