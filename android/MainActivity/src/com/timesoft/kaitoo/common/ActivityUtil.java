package com.timesoft.kaitoo.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

	public static void startActivity(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
	
}
