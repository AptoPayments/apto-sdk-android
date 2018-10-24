package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;


public class GetPinView extends RelativeLayout
        implements View.OnClickListener, ViewWithToolbar {

    private GetPinView.ViewListener mListener;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mGetPinButton;
    private Toolbar mToolbar;

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void getPinClickHandler();
    }
    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public GetPinView(Context context) {
        super(context);
    }

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs   See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public GetPinView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.tv_get_pin_bttn) {
            mListener.getPinClickHandler();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }


    private void findAllViews() {
        mTitle = findViewById(R.id.tv_get_pin_title);
        mSubtitle = findViewById(R.id.tv_get_pin_subtitle);
        mGetPinButton = findViewById(R.id.tv_get_pin_bttn);
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
    }

    private void setupListeners() {
        mGetPinButton.setOnClickListener(this);
    }

    private void setColors() {
        mTitle.setTextColor(UIStorage.getInstance().getTextPrimaryColor());
        mSubtitle.setTextColor(UIStorage.getInstance().getTextSecondaryColor());
        mGetPinButton.setBackgroundColor(UIStorage.getInstance().getUiPrimaryColor());
        if(mToolbar != null) {
            Drawable closeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_close);
            closeIcon.setColorFilter(UIStorage.getInstance().getIconTertiaryColor(), PorterDuff.Mode.SRC_ATOP);
            mToolbar.setNavigationIcon(closeIcon);
            mToolbar.setBackgroundDrawable(new ColorDrawable(UIStorage.getInstance().getUiPrimaryColor()));
        }
    }
}
