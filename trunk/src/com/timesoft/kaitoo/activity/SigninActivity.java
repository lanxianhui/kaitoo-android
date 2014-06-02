package com.timesoft.kaitoo.activity;

import java.net.SocketTimeoutException;

import org.ksoap2.serialization.NullSoapObject;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.common.ActivityUtill;
import com.timesoft.kaitoo.common.DialogAlertMessage;
import com.timesoft.kaitoo.common.ResponseCommon;
import com.timesoft.kaitoo.common.thead.AsyncTaskManager;
import com.timesoft.kaitoo.common.thead.OnAsyncTaskCompleteListener;
import com.timesoft.kaitoo.wsclient.mainws.UserCoreAuthentificationTask;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SigninActivity extends Activity{
	
	private static final String TAG = "SigninActivity";
	
	private Handler customHandler = new Handler();

	private RelativeLayout relativeLayoutMain;
	private LinearLayout linearLayoutEmail;
	private EditText editTextEmail;
	private EditText editTextPassword;
	private Button btnSignin;
	
	private AsyncTaskManager taskManager;
	private DialogAlertMessage dialog;

	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_main);
		setComponent();
		setEventListener();

		customHandler.postDelayed(updateTimerThread, 500);
		
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
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.relativeLayoutMain);
		linearLayoutEmail = (LinearLayout) findViewById(R.id.linearLayoutEmail);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		btnSignin = (Button) findViewById(R.id.btnSignin);
		activity = this;
	}
	
	private void setEventListener() {
		btnSignin.setOnClickListener(new BtnSigninEventListener());
		editTextEmail.setOnTouchListener(new EditTextEmailOnTouchEventListener());
	}
	
	private class BtnSigninEventListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			//startActivity(WebViewActivity.class);
			getUser("android", editTextEmail.getText().toString(), editTextPassword.getText().toString());
		}
	}
	
	private class EditTextEmailOnTouchEventListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			customHandler.postDelayed(updateTimerThread, 0);
			return false;
		}
	}
	
	private Runnable updateTimerThread = new Runnable() {

		public void run() {
			if(linearLayoutEmail.getTop() <= 100) {
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)linearLayoutEmail.getLayoutParams();
				params.setMargins(0, 100, 0, 0); 
				
				linearLayoutEmail.getTop();
				
				relativeLayoutMain.setGravity(Gravity.TOP);
				linearLayoutEmail.setLayoutParams(params);
				
				customHandler.removeCallbacks(updateTimerThread);
				return;
			} else {
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)linearLayoutEmail.getLayoutParams();
				params.setMargins(0, linearLayoutEmail.getTop()-25, 0, 0); 
				
				linearLayoutEmail.getTop();
				
				relativeLayoutMain.setGravity(Gravity.TOP);
				linearLayoutEmail.setLayoutParams(params);
			}
			customHandler.postDelayed(this, 0);
		}

	};
	
	private void getUser(String channel, String email, String password) {

        if (channel == null || channel.equals("")
        		|| email == null || email.equals("")
        		|| password == null || password.equals("")) {
            dialog.showAlertMessage("Missing input parameter", "Please specify the email, password and try again.", android.R.drawable.ic_dialog_alert);
        } else {
            UserCoreAuthentificationTask task = new UserCoreAuthentificationTask();
            taskManager.executeTask(task, UserCoreAuthentificationTask.createRequest(channel, email, password), getString(R.string.UserCoreAuthentificationTask_ws_in_progress),
				new OnAsyncTaskCompleteListener<ResponseCommon>() {
				
				    @Override
				    public void onTaskCompleteSuccess(ResponseCommon result) {
				    	if(result.getFlag()
				    			&& result.getResult() != null) {
				    		ActivityUtill.startActivity(activity, CustomizedListViewActivity.class);
				    	} else {
				    		if(result.getExceptoin() instanceof NullSoapObject) {
					    		dialog.showAlertMessage("Authentification", 
									"Email or Password is invalid.", 
									android.R.drawable.ic_dialog_alert);
				    		} else if(result.getExceptoin() instanceof SocketTimeoutException) {
				    			dialog.showConfirmMessage("Service error", 
									"Service is down. Press Yes for pass.", 
									android.R.drawable.ic_dialog_alert, 
									new ConfirmEvent(), null);
				    		}
				    	}
					}
				
					@Override
					public void onTaskFailed(Exception cause) {
						if(cause != null) {
							Log.e(TAG, cause.getMessage(), cause);
							Log.e(TAG, getString(R.string.UserCoreAuthentificationTask_failed_to_invoke_ws));
							dialog.showConfirmMessage("Service Error", 
									cause.getMessage(), 
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
			ActivityUtill.startActivity(activity, CustomizedListViewActivity.class);
		}
	}

}
