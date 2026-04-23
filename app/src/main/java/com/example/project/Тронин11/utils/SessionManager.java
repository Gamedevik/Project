package com.example.project.Тронин11.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {


    private static final String PREF_NAME = "UserSessionPrefs";


    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static SessionManager instance;


    private SessionManager(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }


    public void createLoginSession(int userId, String email) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_EMAIL, email);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }


    public int getUserId() {
        return preferences.getInt(KEY_USER_ID, -1);
    }


    public void logout() {
        editor.clear();
        editor.apply();
    }
}