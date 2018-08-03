package com.shiftpayments.link.sdk.ui.utils;

import com.shiftpayments.link.sdk.ui.storages.UIStorage;

import java.util.Locale;

/**
 * Language helper.
 * @author Adrian
 */
public class LanguageUtil {
    private static String mLanguage;

    public static String getLanguage() {
        if(isLanguageValid(mLanguage)) {
            return mLanguage;
        }

        if(isLanguageValid(UIStorage.getInstance().getContextConfig().language)) {
            mLanguage = UIStorage.getInstance().getContextConfig().language;
        }
        else {
            mLanguage = Locale.getDefault().getLanguage();
        }

        return mLanguage;
    }

    private static boolean isLanguageValid(String language) {
        return (language != null) && (language.length() == 2);
    }
}
