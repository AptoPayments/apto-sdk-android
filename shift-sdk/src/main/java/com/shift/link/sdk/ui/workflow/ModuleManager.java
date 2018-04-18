package com.shift.link.sdk.ui.workflow;

import java.lang.ref.WeakReference;

/**
 * Created by adrian on 29/12/2016.
 */

public class ModuleManager {
    private static ModuleManager mInstance;
    private WeakReference<ShiftBaseModule> mModule;

    public synchronized ShiftBaseModule getCurrentModule() {
        return mModule.get();
    }

    public synchronized void setModule(WeakReference<ShiftBaseModule> mModule) {
        this.mModule = mModule;
    }

    public static synchronized ModuleManager getInstance() {
        if (mInstance == null) {
            mInstance = new ModuleManager();
        }
        return mInstance;
    }
}
