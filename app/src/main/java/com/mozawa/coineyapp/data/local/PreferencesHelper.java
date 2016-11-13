package com.mozawa.coineyapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.mozawa.coineyapp.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "currency_converter_pref_file_name";
    private static final String PREF_BASE_CURRENCY = "pref_base_currency";

    private final SharedPreferences sharedPrefs;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        sharedPrefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        sharedPrefs.edit().clear().apply();
    }

    public void setBaseCurrency(String baseCurrency) {
        sharedPrefs.edit().putString(PREF_BASE_CURRENCY, baseCurrency).apply();
    }

    public String getBaseCurrency() {
        return sharedPrefs.getString(PREF_BASE_CURRENCY, null);
    }
}

