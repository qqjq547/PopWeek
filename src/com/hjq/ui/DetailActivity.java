package com.hjq.ui;

import com.hjq.week.R;

import android.app.Activity;
import android.webkit.WebView;

public class DetailActivity extends Activity {
	private WebView mWebView;
	private String url;
  protected void onCreate(android.os.Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_web);
	  mWebView=(WebView)findViewById(R.id.webView);
	  url=getIntent().getStringExtra("url");
	  mWebView.loadUrl(url);
	  
  };
}
