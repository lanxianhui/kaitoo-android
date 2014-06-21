package com.timesoft.kaitoo.common;

import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public abstract class MainAppActivity extends Activity{
	
	protected static final String WS_CHANNEL_NAME = "android";
	
	public MainAppActivity() {
		
	}
	
	protected void setResource(Activity activity) {
		Resources res = activity.getResources();
	    // Change locale settings in the app.
	    DisplayMetrics dm = res.getDisplayMetrics();
	    Configuration conf = res.getConfiguration();
	    conf.locale = Locale.US;
	    res.updateConfiguration(conf, dm);
	}
	
}
