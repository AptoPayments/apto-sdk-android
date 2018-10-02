package com.shiftpayments.link.sdk.cardexample;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Storage to handle storing and reading of keys.
 */

public class KeysStorage {
    private static final String PREFS_FILE_NAME = "KeysFile";
    public static final String PREFS_ENVIRONMENT = "ENVIRONMENT";
    public static final String PREFS_PROJECT_KEY = "PROJECT_KEY";

    /**
     * @return True if project has changed.
     */
    public static boolean storeKeys(Context context, String environment, String projectKey) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        String storedProject = settings.getString(PREFS_PROJECT_KEY, "");
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_ENVIRONMENT, environment);
        editor.putString(PREFS_PROJECT_KEY, projectKey);
        editor.apply();
        return !projectKey.equals(storedProject);
    }

    public static void storeProjectKey(Context context, String projectKey) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_PROJECT_KEY, projectKey);
        editor.apply();
    }

    public static String getEnvironment(Context context, String defaultEnvironment) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_ENVIRONMENT, defaultEnvironment);
    }

    public static String getProjectToken(Context context, String defaultProjectToken) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_PROJECT_KEY, defaultProjectToken);
    }
}
