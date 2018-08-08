package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the manage account screen.
 *
 * @author Adrian
 */
public class ManageAccountView
        extends CoordinatorLayout implements View.OnClickListener, ViewWithToolbar {

    private Toolbar mToolbar;
    private ViewListener mListener;
    private TextView mSignOutButton;
    private ImageView mSignOutIcon;
    private TextView mContactSupportButton;
    private ImageView mContactSupportIcon;

    public ManageAccountView(Context context) {
        this(context, null);
    }

    public ManageAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.tv_sign_out || id == R.id.iv_sign_out_icon) {
            mListener.signOut();
        } else if (id == R.id.tv_contact_support || id == R.id.iv_help_center_icon) {
            mListener.contactSupport();
        } else if (id == R.id.toolbar) {
            mListener.onBack();
        }
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColors();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {

        void signOut();
        void contactSupport();
        void onBack();
    }

    private void findAllViews() {
        mSignOutButton = findViewById(R.id.tv_sign_out);
        mContactSupportButton = findViewById(R.id.tv_contact_support);
        mToolbar = findViewById(R.id.toolbar);
        mContactSupportIcon = findViewById(R.id.iv_help_center_icon);
        mSignOutIcon = findViewById(R.id.iv_sign_out_icon);
    }

    private void setUpListeners() {
        mSignOutButton.setOnClickListener(this);
        mContactSupportButton.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(this);
        mContactSupportIcon.setOnClickListener(this);
        mSignOutIcon.setOnClickListener(this);
    }

    private void setColors() {
        Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        backArrow.setColorFilter(UIStorage.getInstance().getIconPrimaryColor(), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
        mContactSupportIcon.setColorFilter(UIStorage.getInstance().getPrimaryColor());
    }
}
