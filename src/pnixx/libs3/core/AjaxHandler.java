package pnixx.libs3.core;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User: P.Nixx
 * Date: 09.10.12
 * Time: 11:50
 */
public class AjaxHandler extends JsonHttpResponseHandler {

	private static String TAG = "AjaxHandler";

	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		onSuccess(response);
	}
	public void onSuccess(JSONObject response) {
		Log.v(TAG + " onSuccess JSONObject: " + response);
	}
	public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
		onSuccess(response);
	}
	public void onSuccess(JSONArray response) {
		Log.v(TAG + " onSuccess JSONArray: " + response);
	}
	public void onSuccess(int statusCode, Header[] headers, String responseString) {
		onSuccess(responseString);
	}
	public void onSuccess(String response) {
		Log.v(TAG + " onSuccess String: " + response);
	}

	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
		onFailure(throwable, responseString);
	}

	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
		onFailure(throwable, errorResponse);
	}
	public void onFailure(Throwable e, JSONObject errorResponse) {
		Log.e(TAG + " onFailure *" + e.getCause() + "* JSONObject: " + errorResponse);
		onError("" + errorResponse);
	}
	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
		onFailure(throwable, errorResponse);
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
}
