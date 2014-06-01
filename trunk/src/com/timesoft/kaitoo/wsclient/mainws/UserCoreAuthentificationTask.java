package com.timesoft.kaitoo.wsclient.mainws;

import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.NullSoapObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.timesoft.kaitoo.common.DialogAlertMessage;
import com.timesoft.kaitoo.wsclient.AbstractProgressableAsyncTask;
import com.timesoft.kaitoo.wsclient.mainws.bo.UserBO;

public class UserCoreAuthentificationTask extends AbstractProgressableAsyncTask<SoapObject, UserBO> {
	
	private static final String TAG = "UserCoreAuthentificationTask";
	
	private static final String WSDL_URL = "http://192.168.0.21:8080/web-service/ws-services/MainWebService?wsdl";
    private static final String WS_NAMESPACE = "http://ws.kaitoo.timesoft.com/";
    private static final String WS_METHOD_NAME = "userCoreAuthentification";

    private Context context;
    
	public UserCoreAuthentificationTask(Context context) {
		this.context = context;
    }

	public static SoapObject createRequest(String channel,
			String email,
			String password) {
		
        SoapObject request = new SoapObject(WS_NAMESPACE, WS_METHOD_NAME);
       
        request.addProperty("channel", channel);
        request.addProperty("email", email);
        request.addProperty("password", password);
        
        return request;
    }

    @Override
    protected UserBO performTaskInBackground(SoapObject parameter) throws Exception {
        // 1. Create SOAP Envelope using the request
    	try {
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        
	        envelope.dotNet = false; 
	        envelope.xsd = SoapSerializationEnvelope.XSD; 
	        envelope.enc = SoapSerializationEnvelope.ENC; 
	        
	        envelope.setOutputSoapObject(parameter);
	
	        // 2. Create a HTTP Transport object to send the web service request
	        HttpTransportSE httpTransport = new HttpTransportSE(WSDL_URL, 3000);
	        httpTransport.debug = true; // allows capture of raw request/respose in Logcat
	
	        // 3. Make the web service invocation
	        httpTransport.call(WS_NAMESPACE + WS_METHOD_NAME, envelope);
	
	        Log.d(TAG, "HTTP REQUEST:\n" + httpTransport.requestDump);
	        Log.d(TAG, "HTTP RESPONSE:\n" + httpTransport.responseDump);
	
	        UserBO result = null;
	        if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
	            SoapObject soapObject = (SoapObject) envelope.bodyIn;
	            result = parseSOAPResponse(soapObject);
	        } else if (envelope.bodyIn instanceof SoapFault) { // SoapFault = FAILURE
	            SoapFault soapFault = (SoapFault) envelope.bodyIn;
	            throw new Exception(soapFault.getMessage());
	        }
	        return result;
    	} catch(SocketTimeoutException e) {
            //When timeout occurs handles this....
    		return null;
        }
        
    }

    private UserBO parseSOAPResponse(SoapObject response) {
    	
    	UserBO userForecastResult = null;
    	
    	if(response != null && !(response.getPropertySafely("return") instanceof NullSoapObject)) {
	        SoapObject userForecastNode = (SoapObject) response.getPropertySafely("return");
	        Log.d(TAG, "::::::::::::::::::::::::::"+userForecastNode);
	        // see the wsdl for the definition of "ForecastReturn" (which can be null/empty)
	        // i.e. <s:element name="GetCityForecastByZIPResponse"> element 
	        if (userForecastNode != null) {
	            // see <s:complexType name="ForecastReturn"> element for definition of "ForecastReturn"
	                    
	            Long id =  Long.parseLong(userForecastNode.getPrimitivePropertySafelyAsString("id").trim());
	            String email =  userForecastNode.getPrimitivePropertySafelyAsString("email").trim();
	            String password =  userForecastNode.getPrimitivePropertySafelyAsString("password").trim();
	            userForecastResult = new UserBO(id, email, password);
	            
	
	            Log.i(TAG, " --> id: "+id+", email: "+email+", password: "+password+".");
	            
	            return userForecastResult;
	        }
    	}
    	
    	DialogAlertMessage dialog = new DialogAlertMessage(context);
    	dialog.showAlertMessage("Authentification", "Email or Password is invalid.", android.R.drawable.ic_dialog_alert);
    	return null;
        
    }
}
