package com.timesoft.kaitoo.activity.listchannel;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.activity.signin.SigninActivity;
import com.timesoft.kaitoo.adapter.ChannelGroupListAdapter;
import com.timesoft.kaitoo.common.ActivityUtil;
import com.timesoft.kaitoo.common.DialogAlertMessage;
import com.timesoft.kaitoo.common.MainAppActivity;
import com.timesoft.kaitoo.common.ResponseCommon;
import com.timesoft.kaitoo.common.ToastMessageUtil;
import com.timesoft.kaitoo.common.thead.AsyncTaskManager;
import com.timesoft.kaitoo.common.thead.OnAsyncTaskCompleteListener;
import com.timesoft.kaitoo.customized.listview.ChannelGroupListTask;

public class ChannelGroupListActivity extends MainAppActivity {

	// All static variables
	public static final String URL = "http://api.androidhive.info/music/music.xml";

	// XML node keys
	public static final String KEY_SONG = "song"; // parent node
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_THUMB_URL = "thumb_url";

	private AsyncTaskManager taskManager;
	private DialogAlertMessage dialog;

	private GridView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_list_group_main);

		dialog = new DialogAlertMessage(this);

		if (taskManager == null) {
			taskManager = new AsyncTaskManager(this);
		}

		list = (GridView) findViewById(R.id.channelGroupList);
		final Activity activity = this;

		ChannelGroupListTask task = new ChannelGroupListTask(activity);
		taskManager.executeTask(task, ChannelGroupListTask.createRequest(URL),
				getString(R.string.waiting),
				new OnAsyncTaskCompleteListener<ResponseCommon>() {

					@Override
					public void onTaskCompleteSuccess(ResponseCommon result) {
						// TODO Auto-generated method stub
						if (result.getFlag() && result.getResult() != null) {
							ChannelGroupListAdapter adapter;

							// Getting adapter by passing xml data ArrayList
							ArrayList<HashMap<String, String>> dataList = (ArrayList<HashMap<String, String>>) result.getResult();
							adapter = new ChannelGroupListAdapter(activity,
									dataList);
							list.setAdapter(adapter);

							// Click event for single list row
							list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									if (position == 10) {
										ActivityUtil.startActivity(activity,
												ChannelListActivity.class);
									} else {
										ToastMessageUtil.showToastMessage(
												activity, "position : "
														+ position + ", id = "
														+ id);
									}
								}
							});
						}
					}

					@Override
					public void onTaskFailed(Exception cause) {
						// TODO Auto-generated method stub

					}
				});
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		dialog.showConfirmMessage("Confirm to logout.",
				"Press Yes for logout this application.",
				android.R.drawable.ic_dialog_alert, new ConfirmEventListener(),
				null);
	}

	private class ConfirmEventListener implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(),
					SigninActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	}

}
