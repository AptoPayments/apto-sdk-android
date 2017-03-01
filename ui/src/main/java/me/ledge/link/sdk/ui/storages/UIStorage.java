package me.ledge.link.sdk.ui.storages;

import android.graphics.Color;

import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;
import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.responses.config.ContextConfigResponseVo;

import static me.ledge.link.sdk.sdk.LedgeLinkSdk.getApiWrapper;

/**
 * Stores UI related data.
 * @author Adrian
 */
public class UIStorage {

    private Integer mPrimaryColor;
    private Integer mSecondaryColor;
    private ContextConfigResponseVo mConfig;

    private static UIStorage mInstance;

    /**
     * Creates a new {@link UIStorage} instance.
     */
    private UIStorage() {
        init();
    }

    /**
     * @return The single instance of this class.
     */
    public static synchronized UIStorage getInstance() {
        if (mInstance == null) {
            mInstance = new UIStorage();
        }

        return mInstance;
    }

    public void init() {
        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return getApiWrapper().getUserConfig(new UnauthorizedRequestVo());
                    } catch (ApiException e) {
                        return null;
                    }
                })
                .thenAccept(this::setConfig);
    }

    public void setConfig(ContextConfigResponseVo config) {
        mConfig = config;
        setColors();
    }

    public synchronized int getPrimaryColor() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if(mPrimaryColor != null) {
                return mPrimaryColor;
            }
            else {
                try {
                    mConfig = getApiWrapper().getUserConfig(new UnauthorizedRequestVo());
                    setColors();
                    return mPrimaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return getResultFromFuture(future);
    }

    public synchronized int getSecondaryColor() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if(mSecondaryColor != null) {
                return mSecondaryColor;
            }
            else {
                try {
                    mConfig = getApiWrapper().getUserConfig(new UnauthorizedRequestVo());
                    setColors();
                    return mSecondaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return getResultFromFuture(future);
    }

    public synchronized int getPrimaryContrastColor() {
        // As per http://stackoverflow.com/a/2241471
        int red = Color.red(mPrimaryColor);
        int green = Color.green(mPrimaryColor);
        int blue = Color.blue(mPrimaryColor);
        return getBrightness(red, green, blue) > 200 ? Color.BLACK : Color.WHITE;
    }

    private int getBrightness(int red, int green, int blue) {
        return (int)Math.sqrt(red * red * .299 +
                            green * green * .587 +
                            blue * blue * .114);
    }

    private void setColors() {
        String hexColor = mConfig.projectConfiguration.primaryColor;
        mPrimaryColor = convertHexToInt(hexColor);
        hexColor = mConfig.projectConfiguration.secondaryColor;
        mSecondaryColor = convertHexToInt(hexColor);
    }

    private int convertHexToInt(String hexColor) {
        return Color.parseColor("#"+hexColor);
    }

    private Integer getResultFromFuture(CompletableFuture future) {
        try {
            return (Integer) future.get();
        } catch (InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
            throw new CompletionException(e);
        }
    }

}
