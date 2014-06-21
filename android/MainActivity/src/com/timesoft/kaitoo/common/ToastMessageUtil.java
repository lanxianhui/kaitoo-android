package com.timesoft.kaitoo.common;

import android.content.Context;
import android.widget.Toast;

public class ToastMessageUtil {

	public static void showToastMessage(Context context, int messageId) {
	    Toast.makeText(context, messageId, Toast.LENGTH_LONG).show();
	}
	
	public static void showToastMessage(Context context, String message) {
	    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	    
}
