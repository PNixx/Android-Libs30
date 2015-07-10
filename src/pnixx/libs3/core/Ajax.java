package pnixx.libs3.core;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: P.Nixx
 * Date: 09.10.12
 * Time: 11:47
 */
public class Ajax {

	public AsyncHttpClient client;
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
		client.setTimeout(600000);

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
	public void post(String url, Ajax.Params params, AsyncHttpResponseHandler handler) {
		try {
			Log.d("Ajax post: " + url + "?" + URLDecoder.decode(EntityUtils.toString(params.getEntity()), "UTF-8"));
		} catch( IOException e ) {
			e.printStackTrace();
		}
		client.post(context, url, params.getEntity(), "text/html", handler);
	}
	public void post(String url, AsyncHttpResponseHandler handler) {
		Log.d("Ajax post: " + url);
		client.post(url, handler);
	}
	public void put(String url, Ajax.Params params, AsyncHttpResponseHandler handler) {
		try {
			Log.d("Ajax put: " + url + "?" + URLDecoder.decode(EntityUtils.toString(params.getEntity()), "UTF-8"));
		} catch( IOException e ) {
			e.printStackTrace();
		}
		client.put(context, url, params.getEntity(), "text/html", handler);
	}
	public void put(String url, AsyncHttpResponseHandler handler) {
		Log.d("Ajax put: " + url);
		client.put(url, handler);
	}
	public void cancel() {
		Log.w("Ajax cancel requests");
		client.cancelRequests(context, true);
	}

	//Параметры запроса без сортировки Hash
	public static class Params {
		private List<NameValuePair> params = new ArrayList<>();

		//Добавление
		public void put(String key, String value) {
			params.add(new BasicNameValuePair(key, value));
		}

		//Получение данных для запроса
		public HttpEntity getEntity() {
			UrlEncodedFormEntity entity = null;
			try {
				entity = new UrlEncodedFormEntity(params, "UTF-8");
			} catch( UnsupportedEncodingException e ) {
				e.printStackTrace();
			}
			return entity;
		}
	}
}
