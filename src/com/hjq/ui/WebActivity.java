package com.hjq.ui;

import com.hjq.week.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class WebActivity extends Activity implements OnClickListener {
	private WebView mWebView;
	private String title;
	private String web_url;
	private TextView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.setBackgroundColor(0);
		mWebView.setBackgroundResource(R.color.white);
		mWebView.getSettings().setLoadsImagesAutomatically(true);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		findViewById(R.id.back).setOnClickListener(this);
		title = getIntent().getStringExtra("title");
		web_url = getIntent().getStringExtra("url");
		if (!title.equals("")) {
			findViewById(R.id.titlebar).setVisibility(View.VISIBLE);		
		}
		titleView=((TextView) findViewById(R.id.title));
		titleView.setText(title);
		mWebView.loadUrl(web_url);
		mWebView.setWebViewClient(new webViewClient());
		mWebView.addJavascriptInterface(new Object()
	    {
	        /**
	         *页面标题回调：展示到TextView中
	         */
	         @JavascriptInterface
	         public void setWebTitle(final String title) {

	            runOnUiThread(new Runnable() {
	                @Override
	                public void run(){
	                    titleView.setText(title);
	                }
	            });
	         }

	        /**
	         *订单数据回调：支付完成通过toast消息提示
	         */
	         @JavascriptInterface
	         public void setOrder(String orderId, String status, String amount, String quantity, String dealGroupId, String uid) {
	             String description = "订单号：" + orderId + " 状态：" + status + " 金额：" + amount + " 数量：" + quantity + " 团购ID：" + dealGroupId + " 第三方用户ID：" + uid;
	             Toast.makeText(WebActivity.this, description, Toast.LENGTH_LONG).show();
	         }
	    },"DPOpenJSBridge");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}

	class webViewClient extends WebViewClient {
		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			return true;
		}
	}
}
