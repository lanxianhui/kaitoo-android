package com.timesoft.kaitoo.customized.listview;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.util.Log;

import com.timesoft.kaitoo.activity.listchannel.ChannelListActivity;
import com.timesoft.kaitoo.common.ResponseCommon;
import com.timesoft.kaitoo.common.thead.AbstractProgressableAsyncTask;
import com.timesoft.kaitoo.xml.XMLParser;

public class ChannelListTask extends AbstractProgressableAsyncTask<String, ResponseCommon> {

	private Activity activity;
	
	public ChannelListTask(Activity activity) {
		this.activity = activity;
	}
	
	public static String createRequest(String url) {
        return url;
    }
	
	@Override
	protected ResponseCommon performTaskInBackground(
			String parameter) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		
		XMLParser parser = new XMLParser(activity);
        String xml = parser.getXmlFromUrl(parameter); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element
        
        NodeList nl = doc.getElementsByTagName(ChannelListActivity.KEY_SONG);
        
        Log.d("TEST", nl.toString());
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(ChannelListActivity.KEY_ID, parser.getValue(e, ChannelListActivity.KEY_ID));
            map.put(ChannelListActivity.KEY_TITLE, parser.getValue(e, ChannelListActivity.KEY_TITLE));
            map.put(ChannelListActivity.KEY_ARTIST, parser.getValue(e, ChannelListActivity.KEY_ARTIST));
            map.put(ChannelListActivity.KEY_DURATION, parser.getValue(e, ChannelListActivity.KEY_DURATION));
            map.put(ChannelListActivity.KEY_THUMB_URL, parser.getValue(e, ChannelListActivity.KEY_THUMB_URL));
 
            // adding HashList to ArrayList
            songsList.add(map);
        }
        
        ResponseCommon result = new ResponseCommon(Boolean.TRUE);
        result.setResult(songsList);
		return result;
	}

}
