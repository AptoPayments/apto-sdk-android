package me.ledge.link.sdk.ui;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.util.ArrayList;

import me.ledge.common.utils.android.AndroidUtils;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.imageloaders.volley.VolleyImageLoader;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.eventbus.utils.EventBusHandlerConfigurator;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.presenters.link.LinkModule;
import me.ledge.link.sdk.ui.storages.LinkStorage;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;
import me.ledge.link.sdk.ui.vos.LoanDataVo;
import me.ledge.link.sdk.wrappers.retrofit.RetrofitTwoLinkApiWrapper;

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
     * @return Order in which the Ledge Line Activities should be shown.
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
                return "http://localhost:5000";
            case dev:
                return "https://dev.ledge.me";
            case stg:
                return "https://stg.ledge.me";
            case sbx:
                return "https://sbx.ledge.me";
            case prd:
                return "https://api.ledge.me";
            default:
                return "https://sbx.ledge.me";
        }
    }

    public static String getVGSEndPoint() {
        switch(mEnvironment) {
            case local_device:
            case local_emulator:
                return getApiEndPoint();
            case dev:
                return "https://tnt9pyfmrib.SANDBOX.verygoodproxy.com";
            case stg:
            default:
                return "https://tntlgdzq6xb.SANDBOX.verygoodproxy.com";
            case sbx:
                return "https://tntt0vbjwpf.SANDBOX.verygoodproxy.com";
            case prd:
                return "https://tnt5ihr00de.LIVE.verygoodproxy.com";
        }
    }

    public static void setupLedgeLink(Context context, String developerKey, String projectToken) {
        setupLedgeLink(context, developerKey, projectToken, true, true, "sbx");
    }

    /**
     * Sets up the Ledge Link SDK.
     */
    public static void setupLedgeLink(Context context, String developerKey, String projectToken, boolean certificatePinning, boolean trustSelfSignedCertificates, String environment) {
        mEnvironment = Environment.valueOf(environment.toLowerCase());
        AndroidUtils utils = new AndroidUtils();
        HandlerConfigurator configurator = new EventBusHandlerConfigurator();

        LinkApiWrapper apiWrapper = new RetrofitTwoLinkApiWrapper();
        apiWrapper.setVgsEndPoint(getVGSEndPoint());
        apiWrapper.setApiEndPoint(getApiEndPoint(), certificatePinning, trustSelfSignedCertificates);
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
        validateToken(activity);
        try {
            ProviderInstaller.installIfNeeded(activity.getApplicationContext());
            LinkModule linkModule = new LinkModule(activity);
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

    private static void validateToken(Context context) {
        String storedPrimaryCredential = SharedPreferencesStorage.getPrimaryCredential(context);
        String storedSecondaryCredential = SharedPreferencesStorage.getSecondaryCredential(context);
        ConfigResponseVo contextConfig = UIStorage.getInstance().getContextConfig();
        if(!contextConfig.primaryAuthCredential.equals(storedPrimaryCredential) ||
                !contextConfig.secondaryAuthCredential.equals(storedSecondaryCredential)) {
            clearUserToken(context);
        }
    }
}
