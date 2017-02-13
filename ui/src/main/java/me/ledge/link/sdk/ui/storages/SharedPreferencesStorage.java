package me.ledge.link.sdk.ui.storages;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by adrian on 09/02/2017.
 */

public class SharedPreferencesStorage {

    private static final String PREFS_FILE_NAME = "UserTokenFile";
    private static final String USER_TOKEN_KEY = "userToken";

    public static void storeUserToken(Context context, String userToken){
        // TODO: only store when not in pos_mode
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_TOKEN_KEY, userToken);
        editor.apply();
    }

    public static String getUserToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(USER_TOKEN_KEY, null);
    }

}
