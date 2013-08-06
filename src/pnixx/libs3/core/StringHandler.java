package pnixx.libs3.core;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * User: P.Nixx
 * Date: 09.10.12
 * Time: 11:50
 */
public class StringHandler extends AsyncHttpResponseHandler {

	private static String TAG = "StringHandler";

	public void onSuccess(String response) {
		Log.v(TAG + " onSuccess String: " + response);
	}

	public void onFailure(Throwable e, String errorResponse) {
		Log.e(TAG + " onFailure *" + e.getCause() + "* String: " + errorResponse);
	}

}
