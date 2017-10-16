package com.bakon.base_lib.baseutil;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.provider.Settings.Secure;

import com.bakon.base_lib.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SystemInfoUtil {
	/**
	 * 获取设备ID
	 * @return
	 */
	public static String getDeviceId() {
		String mDeviceID = null;
		try {
			mDeviceID = Secure.getString(BaseApplication.getInstance().getContentResolver(), Secure.ANDROID_ID);
		} catch (Exception e) {
			mDeviceID = null;
			e.printStackTrace();
		}
		if (mDeviceID == null) {
			mDeviceID = UUID.randomUUID().toString();
		}
		return mDeviceID;
	}

	/**
	 * 获取版本名称
	 * @return
	 */
	public static String getVersionName() {
		// 获取packagemanager的实例
		PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String versionName = packInfo.versionName;
		//debug 状态(1.0-debug)
		if (versionName.contains("-debug")) {
			int index = versionName.indexOf("-debug");
			return versionName.substring(0, index);
		}
		return versionName;
	}

	/**
	 * 获取版本号
	 * @return
	 */
	public static int getVersionCode() {
		// 获取packagemanager的实例
		PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		int versionCode = packInfo.versionCode;
		return versionCode;
    }
	/**
	 * 获取应用名称
	 * @return
	 */
	public static String getApplicationName() {
		// 获取packagemanager的实例
		PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		String applicationName = "";
		try {
			packInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
			ApplicationInfo mApplicationInfo = packInfo.applicationInfo;
			applicationName  = (String) packageManager.getApplicationLabel(mApplicationInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return applicationName;
	}

	/**
	 * 获得属于桌面的应用的应用包名称
	 * @return 返回包含所有包名的字符串列表
	 */
	public static List<String> getHomes() {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo ri : resolveInfo) {
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}

	/**
	 * 判断当前界面是否是桌面
	 * @return
	 */
	public static boolean isHome() {
		ActivityManager mActivityManager = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return getHomes().contains(rti.get(0).topActivity.getPackageName());
	}
	/**
	 * 判断当前是否在本应用内
	 * @return
	 */
	public static boolean isMyApp() {
		ActivityManager mActivityManager = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return rti.get(0).topActivity.getPackageName().contains(BaseApplication.getInstance().getMyPackageName());
	}

}
