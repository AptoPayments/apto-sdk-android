package me.ledge.link.sdk.ui;

import android.app.Activity;

import java.util.ArrayList;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.presenters.link.LinkModule;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;

/**
 * Ledge Line UI helper is an extension of {@link LedgeLinkSdk} to help set up the Ledge Line UI.<br />
 * <br />
 * Make sure to call {@link #setHandlerConfiguration} before calling {@link #startProcess}!
 * @author Wijnand
 */
public class LedgeLinkUi extends LedgeLinkSdk {

    private static GenericImageLoader mImageLoader;
    private static HandlerConfigurator mHandlerConfiguration;
    public static boolean trustSelfSigned;

    /**
     * @return Handler configuration.
     */
    public static HandlerConfigurator getHandlerConfiguration() {
        if (mHandlerConfiguration == null) {
            throw new NullPointerException("Make sure to call 'setHandlerConfiguration(HandlerConfigurator)' before " +
                    "trying to perform any other action!");
        }

        return mHandlerConfiguration;
    }

    /**
     * Stores a new handler configuration.
     * @param configuration New configuration.
     */
    public static void setHandlerConfiguration(HandlerConfigurator configuration) {
        mHandlerConfiguration = configuration;
        setResponseHandler(configuration.getResponseHandler());
    }

    /**
     * @return The {@link GenericImageLoader} to use to load images.
     */
    public static GenericImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * Stores a new {@link GenericImageLoader} to use to load images.
     * @param loader Image loader.
     */
    public static void setImageLoader(GenericImageLoader loader) {
        mImageLoader = loader;
    }

    /**
     * @return Order in which the Ledge Line Activities should be shown.
     */
    public static ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
        return getHandlerConfiguration().getProcessOrder();
    }

    /**
     * Starts the Ledge Line loan offers process.
     * @param activity The {@link Activity} to launch the first Ledge Line screen from.
     * @param data Pre-fill user data. Use {@code null} if not needed.
     */
    public static void startProcess(Activity activity, DataPointList data) {
        UserStorage.getInstance().setUserData(data);
        LinkModule linkModule = new LinkModule(activity);
        linkModule.initialModuleSetup();
    }
}
