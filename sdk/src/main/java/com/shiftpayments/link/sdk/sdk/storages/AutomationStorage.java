package com.shiftpayments.link.sdk.sdk.storages;

/**
 * Stores data for automation tests.
 * @author Adrian
 */
public class AutomationStorage {

    private static AutomationStorage mInstance;
    public String verificationSecret;

    /**
     * Creates a new {@link AutomationStorage} instance.
     */
    private AutomationStorage() { }

    /**
     * @return The single instance of this class.
     */
    public static synchronized AutomationStorage getInstance() {
        if (mInstance == null) {
            mInstance = new AutomationStorage();
        }

        return mInstance;
    }

}
