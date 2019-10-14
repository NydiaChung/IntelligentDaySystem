package com.newland.intelligentdaysystem.utils;

import android.util.Log;

public class LogUtil {

	public static final String LOG_TAG = "hyx";
	public static boolean DEBUG = true;

	public static void d(String tag, String log) {
		if (DEBUG) {
			Log.d(LOG_TAG + "-" + tag, log);
		}
	}

	public static void e(String tag, String log) {
		if (DEBUG) {
			Log.e(LOG_TAG + "-" + tag, log);
		}
	}

	public static void i(String tag, String log) {
		if (DEBUG) {
			Log.i(LOG_TAG + "-" + tag, log);
		}
	}

	public static void v(String tag, String log) {
		if (DEBUG) {
			Log.v(LOG_TAG + "-" + tag, log);
		}
	}

	public static void w(String tag, String log) {
		if (DEBUG) {
			Log.w(LOG_TAG + "-" + tag, log);
		}
	}
}
