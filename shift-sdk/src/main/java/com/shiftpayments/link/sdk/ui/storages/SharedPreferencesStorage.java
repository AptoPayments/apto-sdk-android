package com.shiftpayments.link.sdk.ui.storages;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by adrian on 09/02/2017.
 */

public class SharedPreferencesStorage {

    private static final String PREF_USER_TOKEN_FILE = "PREF_USER_TOKEN_FILE";
    private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private static final String PREF_PRIMARY_CREDENTIAL = "PREF_PRIMARY_CREDENTIAL";
    private static final String PREF_SECONDARY_CREDENTIAL = "PREF_SECONDARY_CREDENTIAL";
    private static boolean mIsPOSMode;

    public static void clearUserToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_USER_TOKEN_FILE, MODE_PRIVATE);
        settings.edit().remove(PREF_USER_TOKEN).apply();
        settings.edit().remove(PREF_PRIMARY_CREDENTIAL).apply();
        settings.edit().remove(PREF_SECONDARY_CREDENTIAL).apply();
    }

    public static void storeUserToken(Context context, String userToken){
        if(!mIsPOSMode) {
            SharedPreferences settings = context.getSharedPreferences(PREF_USER_TOKEN_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_USER_TOKEN, userToken);
            editor.apply();
        }
    }

    /**
     * @return The user token or null if we're in POS mode
     */
    public static String getUserToken(Context context, boolean isPOSMode) {
        mIsPOSMode = isPOSMode;
        SharedPreferences settings = context.getSharedPreferences(PREF_USER_TOKEN_FILE, MODE_PRIVATE);
        if(mIsPOSMode) {
            clearUserToken(context);
            return null;
        }
        else {
            String userToken = settings.getString(PREF_USER_TOKEN, null);
            UserStorage.getInstance().setBearerToken(userToken);
            return userToken;
        }
    }

    public static void storeCredentials(Context context, String primaryCredential, String secondaryCredential){
        SharedPreferences settings = context.getSharedPreferences(PREF_USER_TOKEN_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_PRIMARY_CREDENTIAL, primaryCredential);
        editor.putString(PREF_SECONDARY_CREDENTIAL, secondaryCredential);
        editor.apply();
    }

    public static String getPrimaryCredential(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_USER_TOKEN_FILE, MODE_PRIVATE);
        return settings.getString(PREF_PRIMARY_CREDENTIAL, null);
    }

    public static String getSecondaryCredential(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_USER_TOKEN_FILE, MODE_PRIVATE);
        return settings.getString(PREF_SECONDARY_CREDENTIAL, null);
    }
}
