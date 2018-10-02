package com.shiftpayments.link.sdk.cardexample;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Storage to handle storing and reading of API key.
 */

public class KeysStorage {
    private static final String PREFS_FILE_NAME = "KeysFile";
    public static final String PREFS_ENVIRONMENT = "ENVIRONMENT";
    public static final String PREFS_API_KEY = "API_KEY";

    /**
     * @return True if project has changed.
     */
    public static boolean storeKey(Context context, String environment, String apiKey) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        String storedApiKey = settings.getString(PREFS_API_KEY, "");
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_ENVIRONMENT, environment);
        editor.putString(PREFS_API_KEY, apiKey);
        editor.apply();
        return !apiKey.equals(storedApiKey);
    }

    public static String getEnvironment(Context context, String defaultEnvironment) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_ENVIRONMENT, defaultEnvironment);
    }

    public static String getApiKey(Context context, String defaultApiKey) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_API_KEY, defaultApiKey);
    }
}
