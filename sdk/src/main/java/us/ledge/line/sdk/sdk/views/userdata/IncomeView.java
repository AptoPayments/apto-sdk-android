package us.ledge.line.sdk.sdk.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import us.ledge.line.sdk.sdk.models.Model;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;

/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class IncomeView
        extends UserDataView<NextButtonListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public IncomeView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public IncomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        // TODO
    }
}
