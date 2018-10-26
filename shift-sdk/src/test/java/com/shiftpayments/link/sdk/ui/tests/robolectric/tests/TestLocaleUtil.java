package com.shiftpayments.link.sdk.ui.tests.robolectric.tests;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class TestLocaleUtil {

    public static void setLocale(Resources resources, String language, String country) {
        Locale locale = new Locale(language, country);
        Locale.setDefault(locale);
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

}
