package me.ledge.link.sdk.ui.workflow;

import java.lang.ref.WeakReference;

/**
 * Created by adrian on 29/12/2016.
 */

public class ModuleManager {
    private static ModuleManager mInstance;
    private WeakReference<LedgeBaseModule> mModule;

    public synchronized LedgeBaseModule getCurrentModule() {
        return mModule.get();
    }

    public synchronized void setModule(WeakReference<LedgeBaseModule> mModule) {
        this.mModule = mModule;
    }

    public static synchronized ModuleManager getInstance() {
        if (mInstance == null) {
            mInstance = new ModuleManager();
        }
        return mInstance;
    }
}
