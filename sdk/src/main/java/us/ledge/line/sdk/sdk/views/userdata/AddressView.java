package us.ledge.line.sdk.sdk.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;

/**
 * Displays the address screen.
 * @author Wijnand
 */
public class AddressView
        extends UserDataView<NextButtonListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public AddressView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        // TODO
    }
}
