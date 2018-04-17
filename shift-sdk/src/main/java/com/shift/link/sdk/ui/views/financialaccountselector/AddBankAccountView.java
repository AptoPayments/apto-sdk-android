package com.shift.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.storages.UIStorage;
import com.shift.link.sdk.ui.views.ViewWithToolbar;

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
        setColors();
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
    }

    protected void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
    }
}
