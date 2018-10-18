package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;


public class CardWelcomeView extends LinearLayout implements View.OnClickListener {

    private CardWelcomeView.ViewListener mListener;
    private TextView mActivateCardButton;

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void activateCardClickHandler();
    }
    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public CardWelcomeView(Context context) {
        super(context);
    }

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs   See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public CardWelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.tv_activate_card_bttn) {
            mListener.activateCardClickHandler();
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

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    private void findAllViews() {
        mActivateCardButton = findViewById(R.id.tv_activate_card_bttn);
    }

    private void setupListeners() {
        mActivateCardButton.setOnClickListener(this);
    }

    private void setColors() {
        mActivateCardButton.setBackgroundColor(UIStorage.getInstance().getUiPrimaryColor());
    }
}
