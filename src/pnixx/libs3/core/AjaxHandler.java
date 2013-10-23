package pnixx.libs3.core;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: P.Nixx
 * Date: 09.10.12
 * Time: 11:50
 */
public class AjaxHandler extends JsonHttpResponseHandler {

	private static String TAG = "AjaxHandler";

	public void onSuccess(JSONObject response) {
		Log.v(TAG + " onSuccess JSONObject: " + response);
	}
	public void onSuccess(JSONArray response) {
		Log.v(TAG + " onSuccess JSONArray: " + response);
	}
	public void onSuccess(String response) {
		Log.v(TAG + " onSuccess String: " + response);
	}

	public void onFailure(Throwable e, JSONObject errorResponse) {
		Log.e(TAG + " onFailure *" + e.getCause() + "* JSONObject: " + errorResponse);
		onError("" + errorResponse);
	}
	public void onFailure(Throwable e, JSONArray errorResponse) {
		Log.e(TAG + " onFailure *" + e.getCause() + "* JSONArray: " + errorResponse);
		onError("" + errorResponse);
	}
	public void onFailure(Throwable e, String errorResponse) {
		Log.e(TAG + " onFailure *" + e.getCause() + "* String: " + errorResponse);
		onError(errorResponse);
	}

	public void onError(String r) {

	}

	@Override
	protected Object parseResponse(String message) throws JSONException {
		if( message.contains("Error message") ) {
			Log.v(TAG + " parseResponse String:\n" + message);
		}
		return super.parseResponse(message);
	}


}
