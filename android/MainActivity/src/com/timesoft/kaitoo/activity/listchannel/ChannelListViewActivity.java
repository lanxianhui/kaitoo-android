package com.timesoft.kaitoo.activity.listchannel;

import java.util.ArrayList;
import java.util.HashMap;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.activity.iptvshow.WebViewActivity;
import com.timesoft.kaitoo.adapter.ChannelListViewAdapter;
import com.timesoft.kaitoo.common.ActivityUtil;
import com.timesoft.kaitoo.common.DialogAlertMessage;
import com.timesoft.kaitoo.common.MainAppActivity;
import com.timesoft.kaitoo.common.ResponseCommon;
import com.timesoft.kaitoo.common.ToastMessageUtil;
import com.timesoft.kaitoo.common.thead.AsyncTaskManager;
import com.timesoft.kaitoo.common.thead.OnAsyncTaskCompleteListener;
import com.timesoft.kaitoo.customized.listview.ChannelListViewTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
 
public class ChannelListViewActivity extends MainAppActivity {
	
	private static final String TAG = "CustomizedListViewActivity";
	
    // All static variables
    public static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    public static final String KEY_SONG = "song"; // parent node
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_THUMB_URL = "thumb_url";
    
    private AsyncTaskManager taskManager;
    private DialogAlertMessage dialog;
 
    private ListView list;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_main);
        
        dialog = new DialogAlertMessage(this);
        
        if (taskManager == null) {
            taskManager = new AsyncTaskManager(this);
        }
        
        list = (ListView) findViewById(R.id.list);
        final Activity activity = this;
        
        ChannelListViewTask task = new ChannelListViewTask(this);
        taskManager.executeTask(task, ChannelListViewTask.createRequest(URL), getString(R.string.waiting), 
        		new OnAsyncTaskCompleteListener<ResponseCommon>() {

					@Override
					public void onTaskCompleteSuccess(ResponseCommon result) {
						// TODO Auto-generated method stub
						if(result.getFlag()
								&& result.getResult() != null) {
							ChannelListViewAdapter adapter;
					        
					        // Getting adapter by passing xml data ArrayList
							ArrayList<HashMap<String, String>> dataList = (ArrayList<HashMap<String, String>>) result.getResult();
					        adapter = new ChannelListViewAdapter(activity, dataList);
					        list.setAdapter(adapter);
					        
					        // Click event for single list row
					        list.setOnItemClickListener(new OnItemClickListener() {
					 
					            @Override
					            public void onItemClick(AdapterView<?> parent, View view,
					                    int position, long id) {
					            	if(position == 10) {
					            		ActivityUtil.startActivity(activity, WebViewActivity.class);
					            	} else {
					            		ToastMessageUtil.showToastMessage(activity, "position : " + position + ", id = " + id);
					            	}
					            }
					        });
						}
					}

					@Override
					public void onTaskFailed(Exception cause) {
						// TODO Auto-generated method stub
						Log.e(TAG, cause.getMessage(), cause);
						Log.e(TAG, getString(R.string.CustomizedListViewTask_failed));
						ToastMessageUtil.showToastMessage(activity, cause.getMessage());
					}
				});
        
//        RelativeAbstractAsynTask task = new RelativeAbstractAsynTask(this, list);
//        task.execute(URL);
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

}