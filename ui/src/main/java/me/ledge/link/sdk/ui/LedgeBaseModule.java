package me.ledge.link.sdk.ui;

/**
 * Created by adrian on 29/12/2016.
 */

public abstract class LedgeBaseModule implements Router {

    public abstract void initialModuleSetup();

    public abstract void onClose();

    public abstract void onBack();

    public abstract void onFinish(int result);

    public void startModule(LedgeBaseModule module) {
        module.initialModuleSetup();
    }
}
