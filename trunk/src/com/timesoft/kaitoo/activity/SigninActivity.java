package com.timesoft.kaitoo.activity;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.common.ActivityUtill;
import com.timesoft.kaitoo.common.DialogAlertMessage;
import com.timesoft.kaitoo.common.thead.AsyncTaskManager;
import com.timesoft.kaitoo.common.thead.OnAsyncTaskCompleteListener;
import com.timesoft.kaitoo.wsclient.mainws.UserCoreAuthentificationTask;
import com.timesoft.kaitoo.wsclient.mainws.bo.UserBO;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SigninActivity extends Activity {
	
	private static final String TAG = "SigninActivity";
	
	static final String INTENT_EXTRA_USER = "SigninActivity.INTENT_EXTRA_USER";
	
	private EditText editTextEmail;
	private EditText editTextPassword;
	private Button btnSignin;
	
	private AsyncTaskManager taskManager;
	private DialogAlertMessage dialog;

	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_main);
		setComponent();
		setEventListener();
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
            System.exit(0);
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        if (taskManager == null) {
            taskManager = new AsyncTaskManager(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismissDialog();
    }
	
	private void setComponent() {
		dialog = new DialogAlertMessage(this);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		btnSignin = (Button) findViewById(R.id.btnSignin);
		context = this;
	}
	
	private void setEventListener() {
		btnSignin.setOnClickListener(new BtnSigninEventListener());
	}
	
	private class BtnSigninEventListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			//startActivity(WebViewActivity.class);
			getUser("android", editTextEmail.getText().toString(), editTextPassword.getText().toString());
		}
	}
	
	private void getUser(String channel, String email, String password) {

        if (channel == null || channel.equals("")
        		|| email == null || email.equals("")
        		|| password == null || password.equals("")) {
            dialog.showAlertMessage("Missing input parameter", "Please specify the email, password and try again.", android.R.drawable.ic_dialog_alert);
        } else {
        	final Context context = this;
            UserCoreAuthentificationTask task = new UserCoreAuthentificationTask(context);
            taskManager.executeTask(task, UserCoreAuthentificationTask.createRequest(channel, email, password), getString(R.string.UserCoreAuthentificationTask_ws_in_progress),
				new OnAsyncTaskCompleteListener<UserBO>() {
				
				    @Override
				    public void onTaskCompleteSuccess(UserBO result) {
				    	ActivityUtill.startActivity(context, CustomizedListViewActivity.class);
					}
				
					@Override
					public void onTaskFailed(Exception cause) {
						if(cause != null) {
							Log.e(TAG, cause.getMessage(), cause);
							Log.e(TAG, getString(R.string.UserCoreAuthentificationTask_failed_to_invoke_ws));
							dialog.showConfirmMessage("Service", 
									"Service is down. Press Yes for pass.", 
									android.R.drawable.ic_dialog_alert, 
									new ConfirmEvent(), null);
						} else {
							dialog.showConfirmMessage("Service", 
									"Service is down. Press Yes for pass.", 
									android.R.drawable.ic_dialog_alert, 
									new ConfirmEvent(), null);
					    }
					}
            	}
			);
        }
    }
	
	private class ConfirmEvent implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			ActivityUtill.startActivity(context, CustomizedListViewActivity.class);
		}
	}

}
