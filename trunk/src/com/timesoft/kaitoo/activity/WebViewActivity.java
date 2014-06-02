package com.timesoft.kaitoo.activity;

import com.timesoft.kaitoo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled") 
public class WebViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.html5_main);
		setComponent();
	}

	private void setComponent() {
		WebView webViewTest = (WebView) findViewById(R.id.webViewTest);
		
		WebSettings webSetting = webViewTest.getSettings();
		webSetting.setJavaScriptEnabled(true);
		
		webViewTest.loadUrl("file:///android_asset/iptv/iptv-test.htm");
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}
	
}
