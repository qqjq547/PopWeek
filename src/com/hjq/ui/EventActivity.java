package com.hjq.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hjq.adapter.CommentAdapter;
import com.hjq.adapter.EventAdapter;
import com.hjq.model.Event;
import com.hjq.util.HttpUtil;
import com.hjq.util.PreferenceUtil;
import com.hjq.util.ThreadPoolManager;
import com.hjq.week.R;
import com.hjq.xlistview.XListView;
import com.hjq.xlistview.XListView.IXListViewListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity implements OnClickListener,IXListViewListener{
	private XListView mListView;
	private TextView searchbtn;
	private EditText keyword;
	private Runnable eventRun;
	private String locid="108296";
	private String cityname="";
	private String cityid="";
	private String keywordstr="";
	private String lasttime="未知";
	private int startpos=0;
	private int countpos=0;
	private int totalpos=0;
	private boolean isloading=false;
	private EventAdapter mAdapter;
	private ArrayList<Event> eventArray=new ArrayList<Event>();
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			String result = (String) msg.obj;
			switch (msg.what) {
			case 0:
				Log.i("hjq", result);
					
				break;
            case 1:
            	isloading=false;
            	Log.i("hjq", result);
				try {
					JSONObject json = new JSONObject(result);
					startpos=json.getInt("start");
					countpos=json.getInt("count");
					totalpos=json.getInt("total");
					Log.e("hjq", "startpos:"+startpos+",countpos:"+countpos+",totalpos:"+totalpos);
					JSONArray array = json.getJSONArray("events");
					Log.e("hjq", "array.size:"+array.length());
					if (array != null && array.length() > 0) {
						if (startpos>0) {
							eventArray.addAll(getEventArray(array));
							mAdapter.notifyDataSetChanged();
							onLoad();
						}else {
						eventArray.clear();
						eventArray=getEventArray(array);
						mAdapter = new EventAdapter(
								EventActivity.this, eventArray);
						mListView.setAdapter(mAdapter);
					}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				Toast.makeText(EventActivity.this, "北美票房榜", Toast.LENGTH_LONG).show();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		initView();
		initData();
		initRun();

	}



	protected ArrayList<Event> getEventArray(JSONArray array) throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Event> arr=new ArrayList<Event>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = (JSONObject) array.get(i);
			Log.i("hjq", i + "=" + json.toString());
            String name=json. getString("title");
            String count=String.valueOf(json.getInt("wisher_count"));
            String image=json.getString("image_hlarge");
            String link=json.getString("adapt_url");
            String author=json.getJSONObject("owner").getString("name");
            String loc_name=json.getString("address");
            String begin_time=json.getString("begin_time");
            String category_name=json.getString("category_name");
            String geo=json.getString("geo"); 
            Event event=new Event(name, count, image, link, author, loc_name, begin_time, category_name, geo);
            arr.add(event);
		}
		return arr;
	}

	private void initView() {
		// TODO Auto-generated method stub
		searchbtn=(TextView)findViewById(R.id.searchbtn);
		searchbtn.setOnClickListener(this);
		keyword=(EditText)findViewById(R.id.keyword);
		mListView=(XListView)findViewById(R.id.listview);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		findViewById(R.id.back).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText(R.string.main_title3);
		cityname=PreferenceUtil.getInstance(this).getCity();
		if (cityname.equals("")) {
			Toast.makeText(this, "没有发现城市", Toast.LENGTH_LONG).show();
		}else {
			Toast.makeText(this, "城市:"+cityname, Toast.LENGTH_LONG).show();
		}
		Log.e("hjq", "cityname:"+cityname);
		try {
	    	String cityidStr=readTextFile(getAssets().open("cityid.json"));
	    	Log.e("hjq", "cityidStr:"+cityidStr);
		    JSONObject json=new JSONObject(cityidStr);		    
			JSONArray array = json.getJSONArray("locs");
			Log.e("hjq", "array.length:"+array.length());
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonobj=array.getJSONObject(i);
				if (jsonobj.getString("name").equals(cityname)) {
					cityid=jsonobj.getString("id");
					Log.e("hjq", "cityid:"+cityid);
					Toast.makeText(EventActivity.this, "cityid:"+cityid, Toast.LENGTH_LONG).show();
				}
			}
			if (cityid.equals("")) {
				Toast.makeText(EventActivity.this, "not found cityid:", Toast.LENGTH_LONG).show();
			}	
		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private void initRun() {
		// TODO Auto-generated method stub
	 eventRun=new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url="https://api.douban.com/v2/event/list?day_type=future&type=all&loc="+cityid+"&start="+startpos;
				if (!keywordstr.equals("")||keywordstr.length()>0) {
					url=url+"q="+keywordstr;
				}
					String result=HttpUtil.conn(url);
					Message msg = new Message();
					msg.obj = result;
					msg.what = 1;
					mHandler.sendMessage(msg);
			}
		};
		if (!cityid.equals("")){
			ThreadPoolManager.getInstance().addTask(eventRun);
		}
		
	}
	protected void startRun(){
		ThreadPoolManager.getInstance().addTask(eventRun);
		isloading=true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.searchbtn:
			if (locid.equals("")) {
				Toast.makeText(this, "请选择城市", Toast.LENGTH_LONG).show();
			}else {
				if (!keyword.getText().toString().trim().equals("")) {
					keywordstr=keyword.getText().toString().trim();
					try {
						keywordstr=URLEncoder.encode(keywordstr,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					startRun();
				}
			}
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	private String readTextFile(InputStream inputStream) { 

		  ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 

		  byte buf[] = new byte[1024]; 

		  int len; 

		  try { 

		   while ((len = inputStream.read(buf)) != -1) { 

		    outputStream.write(buf, 0, len); 

		   } 

		   outputStream.close(); 

		   inputStream.close(); 

		  } catch (IOException e) { 

		  } 

		  return outputStream.toString(); 

		}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	protected void onLoad() {
		// TODO Auto-generated method stub
			mListView.stopRefresh();
			mListView.stopLoadMore();
			mListView.setRefreshTime("1233456689");
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (!isloading) {
			if (startpos>0&&startpos>totalpos) {
				Toast.makeText(EventActivity.this, "已经到底了", 500).show();
			}else {
				startpos=startpos+countpos;
				startRun();
			}
		}
	} 
}
