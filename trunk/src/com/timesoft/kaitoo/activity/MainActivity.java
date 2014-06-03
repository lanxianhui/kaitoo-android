package com.timesoft.kaitoo.activity;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.activity.signin.SigninActivity;
import com.timesoft.kaitoo.activity.signup.SignupActivity;
import com.timesoft.kaitoo.common.ActivityUtil;
import com.timesoft.kaitoo.common.MainAppActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends MainAppActivity{
	
	private Button btnSignin;
	private Button btnSignup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setComponent();
		setEventListener();
	}
	
	private void setComponent() {
		btnSignin = (Button) findViewById(R.id.btnSignin);
		btnSignup = (Button) findViewById(R.id.btnSignup);
	}
	
	private void setEventListener() {
		btnSignin.setOnClickListener(new BtnSigninEventListener());
		btnSignup.setOnClickListener(new BtnSignupEventListener());
	}
	
	private class BtnSigninEventListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ActivityUtil.startActivity(v.getContext(), SigninActivity.class);
		}
	}
	
	private class BtnSignupEventListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ActivityUtil.startActivity(v.getContext(), SignupActivity.class);
		}
	}

}
