package com.shift.link.sdk.ui.geocoding.handlers;

/**
 * Callback to run when geocoding request has been completed with error.
 *
 * @author Adrian
 */
public interface GeocodingOnErrorCallback {

    void execute(Exception e);

}