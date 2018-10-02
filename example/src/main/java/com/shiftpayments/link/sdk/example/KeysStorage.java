package com.shiftpayments.link.sdk.example;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Storage to handle storing and reading of API key.
 */

public class KeysStorage {
    private static final String PREF_KEYS_FILE = "PREF_KEYS_FILE";
    public static final String PREF_ENVIRONMENT = "PREF_ENVIRONMENT";
    public static final String PREF_API_KEY = "PREF_API_KEY";

    /**
     * @return True if project has changed.
     */
    public static boolean storeKey(Context context, String environment, String apiKey) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        String storedProject = settings.getString(PREF_API_KEY, "");
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ENVIRONMENT, environment);
        editor.putString(PREF_API_KEY, apiKey);
        editor.apply();
        return !apiKey.equals(storedProject);
    }

    public static void storeApiKey(Context context, String projectKey) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_API_KEY, projectKey);
        editor.apply();
    }

    public static String getEnvironment(Context context, String defaultEnvironment) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        return settings.getString(PREF_ENVIRONMENT, defaultEnvironment);
    }

    public static String getApiKey(Context context, String defaultApiKey) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        return settings.getString(PREF_API_KEY, defaultApiKey);
    }
}
