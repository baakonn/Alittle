package com.bakon.base_lib.baseutil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bakon.base_lib.base.BaseApplication;


public class NetUtil {

	/**
	 * 判断当前网络是否可用
	 */
	public static boolean isNetWorkAviliable() {
		ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();
			}
		}
		return false;
	}
}
