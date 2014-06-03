package com.timesoft.kaitoo.wsclient.main;

import com.timesoft.kaitoo.common.thead.AbstractProgressableAsyncTask;

public abstract class CommonWebService<P, R> extends
		AbstractProgressableAsyncTask<P, R> {

	protected static final String WSDL_URL = "http://192.168.0.21:8080/web-service/ws-services/MainWebService?wsdl";
	protected static final String WS_NAMESPACE = "http://ws.kaitoo.timesoft.com/";
	protected static final Integer WS_TIMEOUT = 15000;

}
