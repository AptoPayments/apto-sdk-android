package me.ledge.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the plaid webview.
 * @author Adrian
 */
public class AddBankAccountView
        extends RelativeLayout
        implements ViewWithToolbar {

    private Toolbar mToolbar;

    /**
     * @see AddBankAccountView#AddBankAccountView
     * @param context See {@link AddBankAccountView#AddBankAccountView}.
     */
    public AddBankAccountView(Context context) {
        this(context, null);
    }

    /**
     * @see AddBankAccountView#AddBankAccountView
     * @param context See {@link AddBankAccountView#AddBankAccountView}.
     * @param attrs See {@link AddBankAccountView#AddBankAccountView}.
     */
    public AddBankAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    protected void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
    }
}
