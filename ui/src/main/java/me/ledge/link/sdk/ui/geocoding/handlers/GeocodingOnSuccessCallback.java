package me.ledge.link.sdk.ui.geocoding.handlers;

import me.ledge.link.sdk.ui.geocoding.vos.GeocodingResultVo;

/**
 * Callback to run when geocoding request has been completed succesfully.
 *
 * @author Adrian
 */
public interface GeocodingOnSuccessCallback {

    void execute(GeocodingResultVo result);
}
