package com.roadstar_serviceprovider.utils;

import android.app.Application;

import com.google.gson.Gson;
import com.roadstar_serviceprovider.app.AppController;

public class SessionManager {
    private static SessionManager sInstance;
    private PreferenceUtil pref;


    private SessionManager(Application application) {
        pref = new PreferenceUtil(application);
    }

    public static void init(Application application) {
        if (sInstance == null) {
            sInstance = new SessionManager(application);
        }
    }

    public static SessionManager get() {
        init(AppController.getInstance());
        return sInstance;
    }


    public String getNumber() {
        return pref.getStringData(PrefConstant.NUMBER);
    }

    public void setNumber(String number) {
        pref.setStringData(PrefConstant.NUMBER, number);
    }

    public String getCountryMobileCode() {
        return pref.getStringData(PrefConstant.COUNTRY_MOBILE_CODE);
    }

    public void setCountryMobileCode(String countryMobileCode) {
        pref.setStringData(PrefConstant.COUNTRY_MOBILE_CODE, countryMobileCode);
    }

    public String getCountryCode() {
        return pref.getStringData(PrefConstant.COUNTRY_CODE);
    }

    public void setCountryCode(String countryCode) {
        pref.setStringData(PrefConstant.COUNTRY_CODE, countryCode);
    }

    public void clear() {
        pref.clear();
    }
}

