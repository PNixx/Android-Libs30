package pnixx.libs3.core;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * User: P.Nixx
 * Date: 09.10.12
 * Time: 11:47
 */
public class Ajax {

	public AsyncHttpClient client;
	private RequestParams params;
	private AjaxHandler handler;
	private String url;
	private Context context;

	//Обычный ajax запрос
	public Ajax() {

		//Создаём асинхронный запрос
		client = new AsyncHttpClient();

		//Добавляем заголовок ajax
		client.addHeader("X-Requested-With", "XMLHttpRequest");
	}

	//Ajax запрос с поддержкой куков
	public Ajax(Context context) {
		this.context = context;

		//Создаём асинхронный запрос
		client = new AsyncHttpClient();

		//Добавляем заголовок ajax
		client.addHeader("X-Requested-With", "XMLHttpRequest");

		//Инициализируем работу с куками
		PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		client.setCookieStore(cookieStore);
	}

	public Ajax setTypeJSON() {
		client.addHeader("Accept", "application/json");
		return this;
	}

	//JSON
	public void get(String url, RequestParams params, AsyncHttpResponseHandler handler) {
		Log.d("Ajax get: " + url + "?" + params.toString());
		client.get(url, params, handler);
	}
	public void get(String url, AsyncHttpResponseHandler handler) {
		Log.d("Ajax get: " + url);
		client.get(url, handler);
	}
	public void post(String url, RequestParams params, AsyncHttpResponseHandler handler) {
		Log.d("Ajax post: " + url + "?" + params.toString());
		client.post(url, params, handler);
	}
	public void post(String url, AsyncHttpResponseHandler handler) {
		Log.d("Ajax post: " + url);
		client.post(url, handler);
	}
	public void cancel() {
		Log.w("Ajax cancel requests");
		client.cancelRequests(context, true);
	}
}
