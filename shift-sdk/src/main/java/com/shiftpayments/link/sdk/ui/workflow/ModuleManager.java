package com.shiftpayments.link.sdk.ui.workflow;

import java.lang.ref.SoftReference;

/**
 * Created by adrian on 29/12/2016.
 */

public class ModuleManager {
    private static ModuleManager mInstance;
    private SoftReference<ShiftBaseModule> mModule;

    /**
     * Creates a new {@link ModuleManager} instance.
     */
    private ModuleManager() { }

    public synchronized ShiftBaseModule getCurrentModule() {
        return mModule.get();
    }

    public synchronized void setModule(SoftReference<ShiftBaseModule> module) {
        mModule = module;
    }

    public static synchronized ModuleManager getInstance() {
        if (mInstance == null) {
            mInstance = new ModuleManager();
        }
        return mInstance;
    }
}
