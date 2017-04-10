package me.ledge.link.sdk.ui;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import me.ledge.common.utils.android.AndroidUtils;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.api.wrappers.retrofit.two.RetrofitTwoLinkApiWrapper;
import me.ledge.link.imageloaders.volley.VolleyImageLoader;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.eventbus.utils.EventBusHandlerConfigurator;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.presenters.link.LinkModule;
import me.ledge.link.sdk.ui.storages.LinkStorage;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;
import me.ledge.link.sdk.ui.vos.LoanDataVo;

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

    private enum Environment {
        local, dev, stg, sbx, prd
    }

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

    public static void clearUserToken(Context context) {
        SharedPreferencesStorage.storeUserToken(context, null);
        UserStorage.getInstance().setBearerToken(null);
    }

    private static String getApiEndPoint(Environment env) {
        switch(env) {
            case local:
                return "http://10.0.2.2:5001";
            case dev:
                return "https://dev.platform.ledge.me/";
            case stg:
                return "https://stg.ledge.me/";
            case sbx:
                return "https://sbx.ledge.me";
            case prd:
                return "https://platform.ledge.me";
            default:
                return "https://sbx.ledge.me";
        }
    }

    public static void setupLedgeLink(Context context, String developerKey, String projectToken) {
        setupLedgeLink(context, developerKey, projectToken, true, true, "sbx");
    }

    /**
     * Sets up the Ledge Link SDK.
     */
    public static void setupLedgeLink(Context context, String developerKey, String projectToken, boolean certificatePinning, boolean trustSelfSignedCertificates, String environment) {
        AndroidUtils utils = new AndroidUtils();
        HandlerConfigurator configurator = new EventBusHandlerConfigurator();

        LinkApiWrapper apiWrapper = new RetrofitTwoLinkApiWrapper();
        apiWrapper.setApiEndPoint(getApiEndPoint(Environment.valueOf(environment)), certificatePinning, trustSelfSignedCertificates);
        apiWrapper.setBaseRequestData(developerKey, utils.getDeviceSummary(), certificatePinning, trustSelfSignedCertificates);
        apiWrapper.setProjectToken(projectToken);

        setApiWrapper(apiWrapper);
        setImageLoader(new VolleyImageLoader(context));
        setHandlerConfiguration(configurator);
        trustSelfSigned = trustSelfSignedCertificates;
    }

    /**
     * Starts the Ledge Line loan offers process.
     * @param activity The {@link Activity} to launch the first Ledge Line screen from.
     * @param userData Pre-fill user data. Use {@code null} if not needed.
     * @param loanData Pre-fill loan data. Use {@code null} if not needed.
     */
    public static void startProcess(Activity activity, DataPointList userData, LoanDataVo loanData) {
        UserStorage.getInstance().setUserData(userData);
        LinkStorage.getInstance().setLoanData(loanData);
        LinkModule linkModule = new LinkModule(activity);
        linkModule.initialModuleSetup();
    }
}
