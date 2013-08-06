package pnixx.libs3.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import pnixx.libs3.BuildConfig;

/**
 * User: P.Nixx
 * Date: 13.10.12
 * Time: 13:33
 */
public class Log {

	private boolean is_debug = BuildConfig.DEBUG;
	public static String TAG;
	private static Log instance;

	public static void init(boolean debug, Context context) {
		instance = new Log(debug, context);
	}

	private Log(boolean debug, Context context) {
		is_debug = debug;
		TAG = context.getApplicationContext().getPackageName();
	}

	public static void v(String s) {
		if( instance.is_debug ) {
			android.util.Log.v(TAG, s);
		}
	}

	public static void d(String s) {
		if( instance.is_debug ) {
			android.util.Log.d(TAG, s);
		}
	}

	public static void i(String i) {
		android.util.Log.i(TAG, i);
	}

	public static void e(String e) {
		android.util.Log.e(TAG, e);
	}

	public static void e(String s, Throwable e) {
		android.util.Log.e(TAG, s, e);
	}

	public static void w(String w) {
		android.util.Log.w(TAG, w);
	}

	public void getMemoryUsage(Context context) {
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		activityManager.getMemoryInfo(mi);
		i("memoryInfo.availMem " + (mi.availMem / 1024) + "\n");
		i("memoryInfo.lowMemory " + mi.lowMemory + "\n");
		i("memoryInfo.threshold " + mi.threshold + "\n");
	}
}
