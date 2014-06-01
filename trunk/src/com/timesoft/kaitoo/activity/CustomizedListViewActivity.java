package com.timesoft.kaitoo.activity;

import java.util.ArrayList;
import java.util.HashMap;
 









import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.adapter.LazyAdapter;
import com.timesoft.kaitoo.adapter.XMLParser;
import com.timesoft.kaitoo.common.ActivityUtill;
import com.timesoft.kaitoo.common.ToastMessageUtill;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
 
public class CustomizedListViewActivity extends Activity {
    // All static variables
    public static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    public static final String KEY_SONG = "song"; // parent node
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_THUMB_URL = "thumb_url";
 
    private ListView list;
    private Context context;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_main);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        list = (ListView) findViewById(R.id.list);
        context = this;

        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    	
    	XMLParser parser = new XMLParser(this);
        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element
        
        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        
        Log.d("TEST", nl.toString());
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
 
            // adding HashList to ArrayList
            songsList.add(map);
        }

        LazyAdapter adapter;
        
        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, songsList);
        list.setAdapter(adapter);
        
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	if(position == 10) {
            		ActivityUtill.startActivity(context, WebViewActivity.class);
            	} else {
            		ToastMessageUtill.showToastMessage(context, "position : " + position + ", id = " + id);
            	}
            }
        });
    }
}