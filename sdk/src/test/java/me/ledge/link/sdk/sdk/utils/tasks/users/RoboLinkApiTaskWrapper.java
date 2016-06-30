package me.ledge.link.sdk.sdk.utils.tasks.users;

import junit.framework.Assert;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Generic {@link LedgeLinkApiTask} wrapper that helps with fake execution of an API Task.
 * @author Wijnand
 */
public class RoboLinkApiTaskWrapper<Result, Request> {

    private LedgeLinkApiTask<Void, Void, Result, Request> mTarget;

    /**
     * Creates a new {@link RoboLinkApiTaskWrapper} instance.
     * @param target The task to "execute".
     */
    public RoboLinkApiTaskWrapper(LedgeLinkApiTask<Void, Void, Result, Request> target) {
        mTarget = target;
    }

    /**
     * @return The result of executing the wrapped Task.
     */
    public Result execute() {
        Result result = null;
        Class apiTaskClass = mTarget.getClass().getSuperclass();

        try {
            Method backgroundMethod = apiTaskClass.getDeclaredMethod("doInBackground", Object[].class);
            backgroundMethod.setAccessible(true);
            result = (Result) backgroundMethod.invoke(mTarget, new Object[]{ new Void[0] });
        } catch (NoSuchMethodException nsme) {
            Assert.fail("Method 'doInBackground' not found.");
        } catch (IllegalAccessException|InvocationTargetException ie) {
            Assert.fail("Couldn't invoke 'doInBackground' method.");
        }

        return result;
    }
}
