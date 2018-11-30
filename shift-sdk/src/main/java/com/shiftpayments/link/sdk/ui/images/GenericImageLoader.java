package com.shiftpayments.link.sdk.ui.images;

import android.widget.ImageView;

/**
 * Helper to load an image from a URL into an {@link ImageView}.
 * @author wijnand
 */
public interface GenericImageLoader {

    interface ResponseObserver
    {
        void onSuccess();
        void onError();
    }

    void setResponseObserver(ResponseObserver observer);

    /**
     * Loads an image from a {@code url} into the {@code target}.
     * @param url The image URL.
     * @param target The {@link ImageView} that will display the downloaded image.
     */
    void load(String url, ImageView target);

    /**
     * Cancels the image loading.
     * @param url The image URL.
     * @param target The {@link ImageView} that will display the downloaded image.
     */
    void cancel(String url, ImageView target);

}
