package com.hjq.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hjq.adapter.CommentAdapter;
import com.hjq.grid.StaggeredGridView;
import com.hjq.model.Comment;
import com.hjq.util.HttpUtil;
import com.hjq.util.ThreadPoolManager;
import com.hjq.week.R;
import com.hjq.xlistview.XListView;
import com.hjq.xlistview.XListView.IXListViewListener;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity implements OnClickListener,IXListViewListener,AbsListView.OnScrollListener{
	private Spinner mSpinner;
	private EditText keyword;
	private TextView mSearch;
	private XListView mListView;
	private StaggeredGridView mGridView;
	private Runnable musicRun, movieRun, bookRun,initmovieRun;
	private CommentAdapter commentAdapter;
	private int curindex=0;
	private String keywordstr="";
	private String lasttime="未知";
	private int startpos=0;
	private int countpos=0;
	private int totalpos=0;
	private boolean isloading=false;
	private ArrayList<Comment> initArray = new ArrayList<Comment>();
	private ArrayList<Comment> musicArray = new ArrayList<Comment>();
	private ArrayList<Comment> movieArray = new ArrayList<Comment>();
	private ArrayList<Comment> bookArray = new ArrayList<Comment>();
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String result = (String) msg.obj;
			isloading=false;
			switch (msg.what) {
			case 0:
				Log.i("hjq", result);
				try {
					JSONObject json = new JSONObject(result);
					JSONArray array = json.getJSONArray("subjects");
					if (array != null && array.length() > 0) {
						initArray.clear();
						initArray=getInitMovieArray(array);
						commentAdapter = new CommentAdapter(
								CommentActivity.this, initArray,false);
						mGridView.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.GONE);
						for (int i = 0; i < initArray.size(); i++) {
							Log.e("hjq", initArray.get(i).getName());
						}
						mGridView.setAdapter(commentAdapter);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				Toast.makeText(CommentActivity.this, "北美票房榜", Toast.LENGTH_LONG).show();
				break;
			case 1:
				Log.i("hjq", result);
				try {
					JSONObject json = new JSONObject(result);
					startpos=json.getInt("start");
					countpos=json.getInt("count");
					totalpos=json.getInt("total");
					Log.e("hjq", "startpos:"+startpos+",countpos:"+countpos+",totalpos:"+totalpos);
					JSONArray array = json.getJSONArray("subjects");
					if (array != null && array.length() > 0) {
						if (startpos>0&&curindex==1) {
							movieArray.addAll(getMovieArray(array));
							commentAdapter.notifyDataSetChanged();
							onLoad();
						}else {
							movieArray.clear();
							movieArray=getMovieArray(array);
							commentAdapter = new CommentAdapter(
									CommentActivity.this, movieArray,false);
							mGridView.setVisibility(View.VISIBLE);
							mListView.setVisibility(View.GONE);
							mGridView.setAdapter(commentAdapter);
						}
					
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(CommentActivity.this, "搜索完成", Toast.LENGTH_LONG).show();
			
				break;
			case 2:
				Log.i("hjq", result);
				try {
					JSONObject json = new JSONObject(result);
					startpos=json.getInt("start");
					countpos=json.getInt("count");
					totalpos=json.getInt("total");
					Log.e("hjq", "startpos:"+startpos+",countpos:"+countpos+",totalpos:"+totalpos);
					JSONArray array = json.getJSONArray("musics");			
					if (array != null && array.length() > 0) {
						if (startpos>0&&curindex==2) {
							musicArray.addAll(getMusicArray(array));
							commentAdapter.notifyDataSetChanged();
							onLoad();
						}else {
							musicArray.clear();
							musicArray=getMusicArray(array);
							commentAdapter = new CommentAdapter(
									CommentActivity.this, musicArray,true);
							mGridView.setVisibility(View.GONE);
							mListView.setVisibility(View.VISIBLE);
							mListView.setAdapter(commentAdapter);
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(CommentActivity.this, "搜索完成", Toast.LENGTH_LONG).show();
				break;
				
			case 3:
				Log.i("hjq", result);
				try {
					JSONObject json = new JSONObject(result);
					startpos=json.getInt("start");
					countpos=json.getInt("count");
					totalpos=json.getInt("total");
					Log.e("hjq", "startpos:"+startpos+",countpos:"+countpos+",totalpos:"+totalpos);
					JSONArray array = json.getJSONArray("books");
					if (array != null && array.length() > 0) {
						if (startpos>0&&curindex==3) {
							bookArray.addAll(getBookArray(array));
							commentAdapter.notifyDataSetChanged();
							onLoad();
						}else {
							bookArray.clear();
							bookArray=getBookArray(array);
							commentAdapter = new CommentAdapter(
									CommentActivity.this, bookArray,true);
							mGridView.setVisibility(View.GONE);
							mListView.setVisibility(View.VISIBLE);
							mListView.setAdapter(commentAdapter);
						}
					
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(CommentActivity.this, "搜索完成", Toast.LENGTH_LONG).show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		mSpinner = (Spinner) findViewById(R.id.spinner);
		keyword = (EditText) findViewById(R.id.keyword);
		mSearch = (TextView) findViewById(R.id.searchbtn);
		mListView = (XListView) findViewById(R.id.listview);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
		mGridView = (StaggeredGridView) findViewById(R.id.gridview);
		mGridView.setOnScrollListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		mSearch.setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText(R.string.main_title2);
		initRun();
	}

	protected void startRun(int index) {
		// TODO Auto-generated method stub
		Log.e("hjq", "startRun:index="+index);
		switch (index) {
		case 1:		
			ThreadPoolManager.getInstance().addTask(movieRun);
			break;
		case 2:		
			ThreadPoolManager.getInstance().addTask(musicRun);
			break;
		case 3:		
			ThreadPoolManager.getInstance().addTask(bookRun);
			break;
		}
		isloading=true;
	}

	protected ArrayList<Comment> getMusicArray(JSONArray array)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Comment> arr=new ArrayList<Comment>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = (JSONObject) array.get(i);
			Log.i("hjq", i + "=" + json.toString());
			String name = json.getString("title");
			String average = json.getJSONObject("rating").getString("average");
			String image = json.getString("image");
			String link = json.getString("mobile_link");
			String author;
			if (json.getJSONObject("attrs").has("singer")) {
				author = json.getJSONObject("attrs").getJSONArray("singer")
						.get(0).toString();
			} else {
				author = "未知";
			}
			arr.add(new Comment(name, average, image, link, author));
		}
		return arr;
	}
	protected ArrayList<Comment> getInitMovieArray(JSONArray array)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Comment> arr=new ArrayList<Comment>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = ((JSONObject) array.get(i)).getJSONObject("subject");
			Log.i("hjq", i + "=" + json.toString());
			String name = json.getString("title");
			String average = json.getJSONObject("rating").getString("average");
			String image = json.getJSONObject("images").getString("large");
			String link = json.getString("alt");
			String author="";
			JSONArray dirArray=json.getJSONArray("directors");
			if (dirArray.length()>0) {
				for (int j = 0; j < dirArray.length(); j++) {
					author=author+dirArray.getJSONObject(0).getString("name");
				}
				if (author.equals("")) {
					author = "未知";
				}
			} else {
				author = "未知";
			}
			arr.add(new Comment(name, average, image, link, author));
		}
		
		return arr;
	}
	protected ArrayList<Comment> getMovieArray(JSONArray array)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Comment> arr=new ArrayList<Comment>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = (JSONObject) array.get(i);
			Log.i("hjq", i + "=" + json.toString());
			String name = json.getString("title");
			String average = json.getJSONObject("rating").getString("average");
			String image = json.getJSONObject("images").getString("large");
			String link = json.getString("alt");
			String author="";
			JSONArray dirArray=json.getJSONArray("directors");
			if (dirArray.length()>0) {
				for (int j = 0; j < dirArray.length(); j++) {
					author=author+dirArray.getJSONObject(0).getString("name");
				}
				if (author.equals("")) {
					author = "未知";
				}
			} else {
				author = "未知";
			}
			arr.add(new Comment(name, average, image, link, author));
		}
		return arr;
	}
	protected ArrayList<Comment> getBookArray(JSONArray array)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Comment> arr=new ArrayList<Comment>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject json = (JSONObject) array.get(i);
			Log.i("hjq", i + "=" + json.toString());
			String name = json.getString("title");
			String average = json.getJSONObject("rating").getString("average");
			String image = json.getJSONObject("images").getString("large");
			String link = json.getString("alt");
			String author="";
			JSONArray dirArray=json.getJSONArray("author");
			if (dirArray.length()>0) {
				for (int j = 0; j < dirArray.length(); j++) {
					author=author+dirArray.get(j);
				}
				if (author.equals("")) {
					author = "未知";
				}
			} else {
				author = "未知";
			}
			arr.add(new Comment(name, average, image, link, author));
		}
		return arr;
	}

	private void initRun() {
		// TODO Auto-generated method stub
		initmovieRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil
						.conn("https://api.douban.com/v2/movie/us_box?start=12");
				Message msg = new Message();
				msg.obj = result;
				msg.what = 0;
				mHandler.sendMessage(msg);
			}
		};
		movieRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil
						.conn("https://api.douban.com/v2/movie/search?q="+keywordstr+"&start="+startpos);
				Message msg = new Message();
				msg.obj = result;
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
		};
		musicRun = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil
						.conn("https://api.douban.com/v2/music/search?q="+keywordstr+"&start="+startpos);
				Message msg = new Message();
				msg.obj = result;
				msg.what = 2;
				mHandler.sendMessage(msg);
			}
		};

		bookRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil
						.conn("https://api.douban.com/v2/book/search?q="+keywordstr+"&start="+startpos);
				Message msg = new Message();
				msg.obj = result;
				msg.what = 3;
				mHandler.sendMessage(msg);
			}
		};
		ThreadPoolManager.getInstance().addTask(initmovieRun);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.searchbtn:
			if (curindex!=(mSpinner.getSelectedItemPosition()+1)) {
				startpos=0;
				countpos=0;
				totalpos=0;
			}
			curindex=mSpinner.getSelectedItemPosition()+1;
			if (!keyword.getText().toString().trim().equals("")) {
				keywordstr=keyword.getText().toString().trim();
				try {
					keywordstr=URLEncoder.encode(keywordstr,"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startRun(curindex);
			}
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		Log.e("hjq", "onLoadMore");
		if (!isloading) {
			if (startpos>0&&startpos>totalpos) {
				Toast.makeText(CommentActivity.this, "已经到底了", 500).show();
			}else {
				startpos=startpos+countpos;
				startRun(curindex);
			}
		}
		
	}
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("1233456689");
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		  if (!isloading) {
	            int lastInScreen = firstVisibleItem + visibleItemCount;
	            if (lastInScreen >= totalItemCount) {
	               onLoadMore();
	            }
	        }
	}
}
