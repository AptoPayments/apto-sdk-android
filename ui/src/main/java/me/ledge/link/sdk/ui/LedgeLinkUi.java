package me.ledge.link.sdk.ui;

import android.content.Context;
import android.content.Intent;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.vos.UserDataVo;

import java.util.ArrayList;

/**
 * Ledge Line UI helper is an extension of {@link LedgeLinkSdk} to help set up the Ledge Line UI.<br />
 * <br />
 * Make sure to call {@link #setProcessOrder} before calling {@link #startProcess}!
 * @author Wijnand
 */
public class LedgeLinkUi extends LedgeLinkSdk {

    private static GenericImageLoader mImageLoader;
    private static ArrayList<Class<? extends MvpActivity>> mProcessOrder;

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
        return mProcessOrder;
    }

    /**
     * Stores a new order in which the Ledge Line Activities should be shown.
     * @param processOrder List of Activity classes.
     */
    public static void setProcessOrder(ArrayList<Class<? extends MvpActivity>> processOrder) {
        mProcessOrder = processOrder;
    }

    /**
     * Starts the Ledge Line loan offers process.
     * @param context The {@link Context} to launch the first Ledge Line screen from.
     * @param data Pre-fill user data. Use {@code null} if not needed.
     */
    public static void startProcess(Context context, UserDataVo data) {
        if (getProcessOrder() == null) {
            throw new NullPointerException("Make sure to call 'setProcessOrder(ArrayList<Class<?>>)' before trying " +
                    "to start the loan offers process!");
        }

        UserStorage.getInstance().setUserData(data);
        context.startActivity(new Intent(context, getProcessOrder().get(0)));
    }
}
