package com.shift.link.sdk.ui.utils;

/**
 * Created by adrian on 05/03/2018.
 */

public interface FingerprintDelegate {
    void onUserAuthenticated();
    void onAuthenticationFailed(String error);
}
