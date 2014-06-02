package com.timesoft.kaitoo.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.adapter.LazyAdapter;
import com.timesoft.kaitoo.common.ActivityUtill;
import com.timesoft.kaitoo.common.ToastMessageUtill;
import com.timesoft.kaitoo.common.thead.AsyncTaskManager;
import com.timesoft.kaitoo.common.thead.OnAsyncTaskCompleteListener;
import com.timesoft.kaitoo.customized.listview.CustomizedListViewTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
 
public class CustomizedListViewActivity extends Activity {
	
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
 
    private ListView list;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_main);
        
        if (taskManager == null) {
            taskManager = new AsyncTaskManager(this);
        }
        
        list = (ListView) findViewById(R.id.list);
        final Activity activity = this;
        
        CustomizedListViewTask task = new CustomizedListViewTask(this);
        taskManager.executeTask(task, CustomizedListViewTask.createRequest(URL), getString(R.string.CustomizedListViewTask_in_progress), 
        		new OnAsyncTaskCompleteListener<ArrayList<HashMap<String, String>>>() {

					@Override
					public void onTaskCompleteSuccess(
							ArrayList<HashMap<String, String>> result) {
						// TODO Auto-generated method stub
						LazyAdapter adapter;
				        
				        // Getting adapter by passing xml data ArrayList
				        adapter = new LazyAdapter(activity, result);
				        list.setAdapter(adapter);
				        
				        // Click event for single list row
				        list.setOnItemClickListener(new OnItemClickListener() {
				 
				            @Override
				            public void onItemClick(AdapterView<?> parent, View view,
				                    int position, long id) {
				            	if(position == 10) {
				            		ActivityUtill.startActivity(activity, WebViewActivity.class);
				            	} else {
				            		ToastMessageUtill.showToastMessage(activity, "position : " + position + ", id = " + id);
				            	}
				            }
				        });
					}

					@Override
					public void onTaskFailed(Exception cause) {
						// TODO Auto-generated method stub
						Log.e(TAG, cause.getMessage(), cause);
						Log.e(TAG, getString(R.string.CustomizedListViewTask_failed));
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
}