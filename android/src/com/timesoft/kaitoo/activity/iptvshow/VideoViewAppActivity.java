package com.timesoft.kaitoo.activity.iptvshow;

import com.timesoft.kaitoo.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.util.Log;

public class VideoViewAppActivity extends Activity {
	
	private static final String TAG = "com.timesoft.kaichon.WebAppActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view_main);
		
		final VideoView videoView = (VideoView) findViewById(R.id.videoViewTest);
		
		MediaController vidControl = new MediaController(this);
		vidControl.setAnchorView(videoView);
		videoView.setMediaController(vidControl);
		
		
		String vidAddress = "http://110.170.213.67:11000";
		//String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
		Uri vidUri = Uri.parse(vidAddress);
		
		videoView.setVideoURI(vidUri);
		
		videoView.setOnPreparedListener(new 
				MediaPlayer.OnPreparedListener()  {
            @Override
            public void onPrepared(MediaPlayer mp) {                         
            	Log.i(TAG, "Duration = " + videoView.getDuration());
            }
        });
		
		
		videoView.start();
	}
	
}
