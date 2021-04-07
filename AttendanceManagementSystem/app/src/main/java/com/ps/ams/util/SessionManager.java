package com.ps.ams.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String IS_LOGIN = "IsLogin";
    private static final String USER_ID = "user_id";
    public static final String PREF_NAME_GLOBEL = "USER_ANDROID_DB_GLOBEL";
    Context context;
    int PRIVATE_MODE = 0;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public SessionManager(Context mContext) {
        context = mContext;
        pref = context.getSharedPreferences(PREF_NAME_GLOBEL, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void logOut() {
        editor.clear();
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
        editor.apply();
    }

    public String  getUserId() {
        return pref.getString(USER_ID, "");
    }


    public void login() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }


}