package me.ledge.link.sdk.example;

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
    public static final String PREFS_TEAM_KEY = "TEAM_KEY";

    public static void storeKeys(Context context, String environment, String projectKey, String teamKey) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_ENVIRONMENT, environment);
        editor.putString(PREFS_PROJECT_KEY, projectKey);
        editor.putString(PREFS_TEAM_KEY, teamKey);
        editor.apply();
    }

    public static String getEnvironment(Context context, String defaultEnvironment) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_ENVIRONMENT, defaultEnvironment);
    }

    public static String getDeveloperKey(Context context, String defaultDeveloperKey ) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_TEAM_KEY, defaultDeveloperKey);
    }

    public static String getProjectToken(Context context, String defaultProjectToken) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_PROJECT_KEY, defaultProjectToken);
    }
}