package com.shiftpayments.link.sdk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.shiftpayments.link.imageloaders.volley.VolleyImageLoader;
import com.shiftpayments.link.sdk.api.utils.NetworkCallback;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.eventbus.utils.EventBusHandlerConfigurator;
import com.shiftpayments.link.sdk.ui.images.GenericImageLoader;
import com.shiftpayments.link.sdk.ui.presenters.card.CardModule;
import com.shiftpayments.link.sdk.ui.presenters.link.LinkModule;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.LinkStorage;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.utils.HandlerConfigurator;
import com.shiftpayments.link.sdk.ui.utils.NetworkBroadcast;
import com.shiftpayments.link.sdk.ui.vos.LoanDataVo;
import com.shiftpayments.link.sdk.ui.vos.ShiftSdkOptions;
import com.shiftpayments.link.sdk.wrappers.retrofit.RetrofitTwoShiftApiWrapper;

import java.util.ArrayList;

import me.ledge.common.utils.android.AndroidUtils;

/**
 * ShiftPlatform is an extension of {@link ShiftLinkSdk} to help set up the SDK.<br />
 * <br />
 * Make sure to call {@link #initialize} before calling {@link #startLinkFlow} or {@link #startCardFlow}!
 * @author Wijnand
 */
public class ShiftPlatform extends ShiftLinkSdk {

    private static GenericImageLoader mImageLoader;
    private static HandlerConfigurator mHandlerConfiguration;
    public static boolean trustSelfSigned;
    private static Environment mEnvironment;

    private enum Environment {
        local_emulator, local_device, dev, stg, sbx, prd
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
     * @return Order in which the Shift Line Activities should be shown.
     */
    public static ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
        return getHandlerConfiguration().getProcessOrder();
    }

    public static void clearUserToken(Context context) {
        SharedPreferencesStorage.clearUserToken(context);
        UserStorage.getInstance().setBearerToken(null);
    }

    private static String getApiEndPoint() {
        switch(mEnvironment) {
            case local_emulator:
                return "http://10.0.2.2:5001";
            case local_device:
                // Don't forget to set port forwarding in chrome 5000->localhost:5001
                /*return "http://localhost:5000";*/
                return "http://local.ledge.me:5001";
            case dev:
                return "https://dev.ledge.me";
            case stg:
                return "https://stg.ledge.me";
            case sbx:
            default:
                return "https://sbx.ledge.me";
            case prd:
                return "https://api.ux.8583.io";
        }
    }

    public static String getVGSEndPoint() {
        switch(mEnvironment) {
            case local_device:
            case local_emulator:
                return getApiEndPoint();
            case dev:
                return "https://vault.dev.ledge.me";
            case stg:
                return "https://vault.stg.ledge.me";
            case sbx:
            default:
                return "https://vault.sbx.ledge.me";
            case prd:
                return "https://vault.ux.8583.io";
        }
    }

    public static void setFirebaseToken(String firebaseToken) {
        UserStorage.getInstance().setFirebaseToken(firebaseToken);
    }

    public static void initialize(Context context, String developerKey, String projectToken) {
        ShiftSdkOptions options = new ShiftSdkOptions();
        initialize(context, developerKey, projectToken, true, true, "sbx", null, options);
    }

    /**
     * Sets up the Shift Link SDK.
     */
    public static void initialize(Context context, String developerKey, String projectToken, boolean certificatePinning, boolean trustSelfSignedCertificates, String environment, NetworkCallback onNoInternetConnection, ShiftSdkOptions options) {
        mEnvironment = Environment.valueOf(environment.toLowerCase());
        AndroidUtils utils = new AndroidUtils();
        HandlerConfigurator configurator = new EventBusHandlerConfigurator();

        ShiftApiWrapper apiWrapper = new RetrofitTwoShiftApiWrapper();
        apiWrapper.setVgsEndPoint(getVGSEndPoint());
        apiWrapper.setApiEndPoint(getApiEndPoint(), certificatePinning, trustSelfSignedCertificates);
        apiWrapper.setBaseRequestData(developerKey, utils.getDeviceSummary(), certificatePinning, trustSelfSignedCertificates);
        apiWrapper.setProjectToken(projectToken);
        apiWrapper.setOnNoInternetConnectionCallback(onNoInternetConnection);

        Context applicationContext = context.getApplicationContext();
        // Register receiver for apps targeting Android 7.0+
        // https://developer.android.com/training/monitoring-device-state/connectivity-monitoring#MonitorChanges
        applicationContext.registerReceiver(new NetworkBroadcast(getNetworkDelegate()), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        setApiWrapper(apiWrapper);
        setImageLoader(new VolleyImageLoader(context));
        setHandlerConfiguration(configurator);
        trustSelfSigned = trustSelfSignedCertificates;
        UIStorage.getInstance().setSdkOptions(options);
    }

    public static void startLinkFlow(Activity activity) {
        startLinkFlow(activity, null, null);
    }

    /**
     * Starts the Shift Line loan offers process.
     * @param activity The {@link Activity} to launch the first Shift Line screen from.
     * @param userData Pre-fill user data. Use {@code null} if not needed.
     * @param loanData Pre-fill loan data. Use {@code null} if not needed.
     */
    public static void startLinkFlow(Activity activity, DataPointList userData, LoanDataVo loanData) {
        UserStorage.getInstance().setUserData(userData);
        LinkStorage.getInstance().setLoanData(loanData);
        validateToken(activity);
        try {
            ProviderInstaller.installIfNeeded(activity.getApplicationContext());
            LinkModule linkModule = new LinkModule(activity, activity::finish, activity::onBackPressed);
            linkModule.initialModuleSetup();
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GoogleApiAvailability.getInstance().getErrorDialog(activity, e.getConnectionStatusCode(), 0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Google Play services is not available entirely.
            GoogleApiAvailability.getInstance().getErrorDialog(activity, e.errorCode, 0).show();
        }
    }

    public static void startCardFlow(Activity activity) {
        startCardFlow(activity, null, null);
    }

    /**
     * Starts the Shift Line loan offers process.
     * @param activity The {@link Activity} to launch the first Shift Line screen from.
     * @param userData Pre-fill user data. Use {@code null} if not needed.
     * @param card Pre-fill card data. Use {@code null} if not needed.
     */
    public static void startCardFlow(Activity activity, DataPointList userData, Card card) {
        UserStorage.getInstance().setUserData(userData);
        CardStorage.getInstance().setCard(card);
        validateToken(activity);
        try {
            ProviderInstaller.installIfNeeded(activity.getApplicationContext());
            new CardModule(activity, activity::finish, activity::onBackPressed).initialModuleSetup();
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GoogleApiAvailability.getInstance().getErrorDialog(activity, e.getConnectionStatusCode(), 0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Google Play services is not available entirely.
            GoogleApiAvailability.getInstance().getErrorDialog(activity, e.errorCode, 0).show();
        }
    }

    private static void validateToken(Context context) {
        String storedPrimaryCredential = SharedPreferencesStorage.getPrimaryCredential(context);
        String storedSecondaryCredential = SharedPreferencesStorage.getSecondaryCredential(context);
        ConfigResponseVo contextConfig = UIStorage.getInstance().getContextConfig();
        if(!contextConfig.primaryAuthCredential.equalsIgnoreCase(storedPrimaryCredential) ||
                !contextConfig.secondaryAuthCredential.equalsIgnoreCase(storedSecondaryCredential)) {
            clearUserToken(context);
        }
    }
}
