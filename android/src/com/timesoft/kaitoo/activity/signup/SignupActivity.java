package com.timesoft.kaitoo.activity.signup;

import com.timesoft.kaitoo.common.DialogAlertMessage;
import com.timesoft.kaitoo.common.MainAppActivity;
import com.timesoft.kaitoo.common.ResponseCommon;
import com.timesoft.kaitoo.common.SecurityMD5;
import com.timesoft.kaitoo.common.thead.AsyncTaskManager;
import com.timesoft.kaitoo.common.thead.OnAsyncTaskCompleteListener;
import com.timesoft.kaitoo.common.validator.EmailValidator;
import com.timesoft.kaitoo.common.validator.PasswordValidator;
import com.timesoft.kaitoo.wsclient.main.UserCoreSaveTask;
import com.timesoft.kaitoo.R;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SignupActivity extends MainAppActivity {

	private static final String TAG = "SignupActivity";

	private RelativeLayout relativeLayoutMain;
	private LinearLayout linearLayoutEmail;
	private EditText editTextPassword;
	private EditText editTextRePassword;
	private EditText editTextEmail;
	private Button btnSignin;

	private AsyncTaskManager taskManager;
	private Handler customHandler = new Handler();
	private DialogAlertMessage dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_main);
		setComponent();
		setEventListener();

		customHandler.postDelayed(slideUpThread, 500);
	}

	private void setComponent() {
		taskManager = new AsyncTaskManager(this);
		dialog = new DialogAlertMessage(this);

		relativeLayoutMain = (RelativeLayout) findViewById(R.id.relativeLayoutMain);
		linearLayoutEmail = (LinearLayout) findViewById(R.id.linearLayoutEmail);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		editTextRePassword = (EditText) findViewById(R.id.editTextRePassword);
		btnSignin = (Button) findViewById(R.id.btnSignin);
	}

	private void setEventListener() {
		btnSignin.setOnClickListener(new BtnSigninEventener());
	}

	private Boolean validate() {
		EmailValidator email = new EmailValidator(editTextEmail.getText()
				.toString());
		PasswordValidator password = new PasswordValidator(editTextPassword
				.getText().toString(), editTextRePassword.getText().toString());

		if (email.isEmpty()) {
			dialog.showAlertMessage(getString(R.string.signup_validate_title),
					getString(R.string.signup_validate_email_empty),
					android.R.drawable.ic_dialog_alert);
			return Boolean.FALSE;
		}

		if (password.isEmpty()) {
			dialog.showAlertMessage(getString(R.string.signup_validate_title),
					getString(R.string.signup_validate_password_empty),
					android.R.drawable.ic_dialog_alert);
			return Boolean.FALSE;
		}

		if (!email.validate()) {
			dialog.showAlertMessage(getString(R.string.signup_validate_title),
					getString(R.string.signup_validate_email),
					android.R.drawable.ic_dialog_alert);
			return Boolean.FALSE;
		}

		if (!password.easyValidate()) {
			dialog.showAlertMessage(getString(R.string.signup_validate_title),
					getString(R.string.signup_validate_password_easy),
					android.R.drawable.ic_dialog_alert);
			return Boolean.FALSE;
		}

		if (!password.comparePassword()) {
			dialog.showAlertMessage(getString(R.string.signup_validate_title),
					getString(R.string.signup_validate_password_compare),
					android.R.drawable.ic_dialog_alert);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	private Runnable slideUpThread = new Runnable() {

		public void run() {
			if (linearLayoutEmail.getTop() <= 100) {
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayoutEmail
						.getLayoutParams();
				params.setMargins(0, 100, 0, 0);

				linearLayoutEmail.getTop();

				relativeLayoutMain.setGravity(Gravity.TOP);
				linearLayoutEmail.setLayoutParams(params);

				customHandler.removeCallbacks(slideUpThread);
				return;
			} else {
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayoutEmail
						.getLayoutParams();
				params.setMargins(0, linearLayoutEmail.getTop() - 25, 0, 0);

				relativeLayoutMain.setGravity(Gravity.TOP);
				linearLayoutEmail.setLayoutParams(params);
			}
			customHandler.postDelayed(this, 0);
		}

	};

	private void saveUser(String email, String password) {
		Log.d(TAG, "email = " + email);
		Log.d(TAG, "password = " + password);
		UserCoreSaveTask task = new UserCoreSaveTask();
		taskManager.executeTask(task, UserCoreSaveTask.createRequest(
				WS_CHANNEL_NAME, email, password), getString(R.string.waiting),
				new OnAsyncTaskCompleteListener<ResponseCommon>() {

					@Override
					public void onTaskCompleteSuccess(ResponseCommon result) {
						// TODO Auto-generated method stub
						if (result.getFlag()
								&& result.getResult() != null
								&& ((Boolean) result.getResult())
										.equals(Boolean.TRUE)
								&& !result.getInformation().equals("duplicate")) {
							dialog.showAlertMessage(
									getString(R.string.signup_title),
									"Register successfully.",
									android.R.drawable.ic_dialog_info);
						} else {
							if (result.getResult() != null
									&& result.getInformation().equals(
											"duplicate")) {
								dialog.showAlertMessage(
										getString(R.string.signup_title),
										getString(R.string.signup_validate_email_duplicate),
										android.R.drawable.ic_dialog_alert);
							} else {
								dialog.showAlertMessage("Service error",
										"Service is down.",
										android.R.drawable.ic_dialog_alert);
							}
						}
					}

					@Override
					public void onTaskFailed(Exception cause) {
						// TODO Auto-generated method stub
						if (cause != null) {
							Log.e(TAG, cause.getMessage(), cause);
							Log.e(TAG,
									getString(R.string.UserCoreAuthentificationTask_failed_to_invoke_ws));
							dialog.showAlertMessage("Service Error",
									cause.getMessage(),
									android.R.drawable.ic_dialog_alert);
						}
					}
				});
	}

	private class BtnSigninEventener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (validate()) {
				SecurityMD5 md5 = new SecurityMD5(editTextPassword.getText()
						.toString());
				saveUser(editTextEmail.getText().toString(), md5.encoding());
			}
		}
	}

}
