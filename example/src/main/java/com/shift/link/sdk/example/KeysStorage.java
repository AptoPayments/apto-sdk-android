package com.shift.link.sdk.example;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Storage to handle storing and reading of keys.
 */

public class KeysStorage {
    private static final String PREF_KEYS_FILE = "PREF_KEYS_FILE";
    public static final String PREF_ENVIRONMENT = "PREF_ENVIRONMENT";
    public static final String PREF_PROJECT_KEY = "PREF_PROJECT_KEY";
    public static final String PREF_TEAM_KEY = "PREF_TEAM_KEY";

    /**
     * @return True if project has changed.
     */
    public static boolean storeKeys(Context context, String environment, String projectKey, String teamKey) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        String storedProject = settings.getString(PREF_PROJECT_KEY, "");
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ENVIRONMENT, environment);
        editor.putString(PREF_PROJECT_KEY, projectKey);
        editor.putString(PREF_TEAM_KEY, teamKey);
        editor.apply();
        return !projectKey.equals(storedProject);
    }

    public static void storeProjectKey(Context context, String projectKey) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_PROJECT_KEY, projectKey);
        editor.apply();
    }

    public static void storeTeamKey(Context context, String teamKey) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_TEAM_KEY, teamKey);
        editor.apply();
    }

    public static String getEnvironment(Context context, String defaultEnvironment) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        return settings.getString(PREF_ENVIRONMENT, defaultEnvironment);
    }

    public static String getDeveloperKey(Context context, String defaultDeveloperKey ) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        return settings.getString(PREF_TEAM_KEY, defaultDeveloperKey);
    }

    public static String getProjectToken(Context context, String defaultProjectToken) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEYS_FILE, MODE_PRIVATE);
        return settings.getString(PREF_PROJECT_KEY, defaultProjectToken);
    }
}
