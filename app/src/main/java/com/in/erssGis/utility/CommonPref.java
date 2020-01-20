package com.in.erssGis.utility;


import android.content.Context;
import android.content.SharedPreferences;
import com.in.erssGis.entity.UserDetails;


public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}


	public static void setUserDetails(Context context, UserDetails userInfo) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();

		editor.putString("UserId", userInfo.getMobile());
		editor.putString("UserName", userInfo.getName());
		editor.putString("UserPassword", userInfo.getPassword());
		//editor.putString("Role", userInfo.get_Role());
		editor.putString("DistrictId", userInfo.getDistrictCode());
		editor.putString("DistrictName", userInfo.getDistrictName());
		editor.putString("BlockId", userInfo.getBlockCode());
		editor.putString("BlockName", userInfo.getBlockName());
		editor.putString("PanchayatId", userInfo.getPanchayatCode());
		editor.putString("PanchayatName", userInfo.getPanchayatName());
		editor.putString("password", userInfo.getPassword());
		//editor.putString("PacsName", userInfo.get_PacsnName());



		editor.commit();

	}

	public static UserDetails getUserDetails(Context context) {

		String key = "_USER_DETAILS";
		UserDetails userInfo = new UserDetails();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		userInfo.setMobile(prefs.getString("UserId", ""));
		userInfo.setName(prefs.getString("UserName", ""));
		//userInfo.set_Role(prefs.getString("Role", ""));
		userInfo.setDistrictCode(prefs.getString("DistrictId", ""));
		userInfo.setDistrictName(prefs.getString("DistrictName", ""));
		userInfo.setBlockCode(prefs.getString("BlockId", ""));
		userInfo.setBlockName(prefs.getString("BlockName", ""));
		userInfo.setPanchayatCode(prefs.getString("PanchayatId", ""));
		userInfo.setPanchayatName(prefs.getString("PanchayatName", ""));
		userInfo.setPassword(prefs.getString("password", ""));
		//userInfo.set_PacsnName(prefs.getString("PacsName", userInfo.get_PacsnName()));

		//	userInfo.set_Role(prefs.getString("Role", ""));

		return userInfo;
	}

	public static void setCheckUpdate(Context context, long dateTime) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();

		dateTime = dateTime + 1 * 3600000;
		editor.putLong("LastVisitedDate", dateTime);

		editor.commit();

	}

	public static int getCheckUpdate(Context context) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);

		long a = prefs.getLong("LastVisitedDate", 0);

		if (System.currentTimeMillis() > a)
			return 1;
		else
			return 0;
	}

	public static void setFragmentData(Context context, String[] data) {

		String key = "_FragmentData";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();

		editor.putString("DistName", data[0]);
		editor.putString("DistCode", data[1]);

		editor.commit();

	}

	public static String[] getFragmentData(Context context) {

		String key = "_FragmentData";
		String[] data = new String[2];
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		data[0]= prefs.getString("DistName", "");
		data[1]= prefs.getString("DistCode", "");

		return data;
	}


}
