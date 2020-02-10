package com.example.thinkpad.tipcalculator;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class TipApplication extends Application {

    private static Context context;
    private SharedPreferences preferences;
    private boolean preferenceIsChanged;

    @Override
    public void onCreate() {
        super.onCreate();
        TipApplication.context = getApplicationContext();
        preferences = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        preferenceIsChanged = false;
    }

    public static Context getAppContext() {
        return TipApplication.context;
    }


    public void setPreferenceChanged(boolean changed) {
        preferenceIsChanged = changed;
    }

    public void setDefaultTipPercentage(String newDefaultTipPercentage) {
        SharedPreferences.Editor edit = preferences.edit();
        if (preferences.contains("tip")) {
            edit.remove("tip");
        }
        edit.putString("tip", newDefaultTipPercentage);
        edit.apply();
        setPreferenceChanged(true);
    }

    public void setDefaultTaxPercentage(String newDefaultTaxPercentage) {
        SharedPreferences.Editor edit = preferences.edit();
        if (preferences.contains("tax")) {
            edit.remove("tax");
        }
        edit.putString("tax", newDefaultTaxPercentage);
        edit.apply();
        setPreferenceChanged(true);
    }

    public void setDefaultCurrency(String newDefaultCurrency) {
        SharedPreferences.Editor edit = preferences.edit();
        if (preferences.contains("currency")) {
            edit.remove("currency");
        }
        edit.putString("currency", newDefaultCurrency);
        edit.apply();
        setPreferenceChanged(true);
    }


    public boolean isPreferenceChanged () {
        return preferenceIsChanged;
    }

    public String getDefaultTipPercentage() {
        return preferences.getString("tip", "15");
    }

    public String getDefaultTaxPercentage() {
        return preferences.getString("tax", "13");
    }

    public String getDefaultCurrency() {
        return preferences.getString("currency", "$");
    }
}
