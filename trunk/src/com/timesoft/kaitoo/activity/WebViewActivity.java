package com.timesoft.kaitoo.activity;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.common.DialogAlertMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled") 
public class WebViewActivity extends Activity{
	
	private DialogAlertMessage dialog;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.html5_main);
		setComponent();
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismissDialog();
    }
	
	private void setComponent() {
		activity = this;
		dialog = new DialogAlertMessage(this);
		
		WebView webViewTest = (WebView) findViewById(R.id.webViewTest);
		
		WebSettings webSetting = webViewTest.getSettings();
		webSetting.setJavaScriptEnabled(true);
		
		webViewTest.loadUrl("file:///android_asset/iptv/iptv-test.htm");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		dialog.showConfirmMessage("Confirm to exit this application.", 
				"Press Yes for exit this application.", 
				android.R.drawable.ic_dialog_alert, 
				new ConfirmEventListener(), 
				new NoEventListener());
	}
	
	private class ConfirmEventListener implements DialogInterface.OnClickListener{
		
		@Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
        	Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	intent.putExtra("EXIT", true);
        	startActivity(intent);
        }
		
	}
	
	private class NoEventListener implements DialogInterface.OnClickListener{
		
		@Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
			activity.finish();
        }
		
	}
	
}
