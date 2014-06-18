package com.timesoft.kaitoo.customized.listview;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.util.Log;

import com.timesoft.kaitoo.activity.listchannel.ChannelGroupListActivity;
import com.timesoft.kaitoo.common.ResponseCommon;
import com.timesoft.kaitoo.common.thead.AbstractProgressableAsyncTask;
import com.timesoft.kaitoo.xml.XMLParser;

public class ChannelGroupListTask extends
		AbstractProgressableAsyncTask<String, ResponseCommon> {
	private Activity activity;

	public ChannelGroupListTask(Activity activity) {
		this.activity = activity;
	}

	public static String createRequest(String url) {
		return url;
	}

	@Override
	protected ResponseCommon performTaskInBackground(String parameter)
			throws Exception {
		// TODO Auto-generated method stub

		ArrayList<HashMap<String, String>> groupChannelList = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser(activity);
		String xml = parser.getXmlFromUrl(parameter); // getting XML from URL
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(ChannelGroupListActivity.KEY_SONG);

		Log.d("TEST", nl.toString());
		// looping through all song nodes <song>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(ChannelGroupListActivity.KEY_ID,
					parser.getValue(e, ChannelGroupListActivity.KEY_ID));
			map.put(ChannelGroupListActivity.KEY_TITLE,
					parser.getValue(e, ChannelGroupListActivity.KEY_TITLE));
			map.put(ChannelGroupListActivity.KEY_THUMB_URL,
					parser.getValue(e, ChannelGroupListActivity.KEY_THUMB_URL));

			// adding HashList to ArrayList
			groupChannelList.add(map);
		}

		ResponseCommon result = new ResponseCommon(Boolean.TRUE);
		result.setResult(groupChannelList);
		return result;
	}
}
