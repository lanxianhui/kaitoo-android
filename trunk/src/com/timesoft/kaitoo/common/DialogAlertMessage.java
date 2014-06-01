package com.timesoft.kaitoo.common;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogAlertMessage {
	
	private Dialog dialog;
	private Context context;
	
	public DialogAlertMessage(Context context) {
		dialog = new Dialog(context);
		this.context = context;
	}
	
	public void showAlertMessage(String title, String message, int drawable) {
		Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(drawable);

        builder.setPositiveButton("OK", null);
        
        displayNewDialog(builder);
	}
	
	public void showConfirmMessage(String title, 
			String message, 
			int drawable,
			DialogInterface.OnClickListener confirmEvent,
			DialogInterface.OnClickListener exitEvent) {
		Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(drawable);
        builder.setNegativeButton("No", exitEvent);
        builder.setPositiveButton("Yes", confirmEvent);
        
        displayNewDialog(builder);
	}
	
	private void displayNewDialog(Builder builder) {
        dismissDialog();
        dialog = builder.create();
        dialog.show();
    }
	
	public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
