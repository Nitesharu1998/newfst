package com.example.fst_t0763;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    SharedPreferences preference;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_FILE="session";
    private static final String IS_FIRST_TIME = "Y";
    private static final int MODE = 0;

    public UserSession(Context context) {
        this.context = context;
        preference = context.getSharedPreferences(PREF_FILE,MODE);
        editor = preference.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preference.getBoolean(IS_FIRST_TIME, true);
    }



}
