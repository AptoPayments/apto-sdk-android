package me.ledge.link.sdk.sdk.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import me.ledge.link.sdk.sdk.views.ViewWithToolbar;

/**
 * Displays the terms and conditions screen.
 * @author Wijnand
 */
public class TermsView extends UserDataView<NextButtonListener> implements ViewWithToolbar {

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public TermsView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public TermsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
