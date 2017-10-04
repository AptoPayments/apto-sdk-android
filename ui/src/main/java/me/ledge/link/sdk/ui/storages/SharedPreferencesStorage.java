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
    private static boolean mIsPOSMode;

    public static void clearUserToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        settings.edit().remove(USER_TOKEN_KEY).apply();
    }

    public static void storeUserToken(Context context, String userToken){
        if(!mIsPOSMode) {
            SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(USER_TOKEN_KEY, userToken);
            editor.apply();
        }
    }

    /**
     * @return The user token or null if we're in POS mode
     */
    public static String getUserToken(Context context, boolean isPOSMode) {
        mIsPOSMode = isPOSMode;
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        if(mIsPOSMode) {
            clearUserToken(context);
            return null;
        }
        else {
            String userToken = settings.getString(USER_TOKEN_KEY, null);
            UserStorage.getInstance().setBearerToken(userToken);
            return userToken;
        }
    }

}
