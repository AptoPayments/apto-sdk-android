package com.shiftpayments.link.sdk.ui.presenters.card;

import android.util.Log;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.CardSettingsActivity;
import com.shiftpayments.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shiftpayments.link.sdk.ui.models.card.CardSettingsModel;
import com.shiftpayments.link.sdk.ui.models.card.FundingSourceModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.views.card.CardSettingsView;
import com.shiftpayments.link.sdk.ui.views.card.FundingSourceView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Concrete {@link Presenter} for the card settings screen.
 * @author Adrian
 */
public class CardSettingsPresenter
        extends BasePresenter<CardSettingsModel, CardSettingsView>
        implements Presenter<CardSettingsModel, CardSettingsView>,FundingSourceView.ViewListener {

    private CardSettingsActivity mActivity;
    private FundingSourcesListRecyclerAdapter mAdapter;

    public CardSettingsPresenter(CardSettingsActivity activity) {
        mActivity = activity;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(CardSettingsView view) {
        super.attachView(view);
        mActivity.setSupportActionBar(mView.getToolbar());
        mActivity.getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.card_settings_title));
        mAdapter = new FundingSourcesListRecyclerAdapter();
        mAdapter.setViewListener(this);
        view.setAdapter(mAdapter);
        mView.showAddFundingSourceButton(UIStorage.getInstance().showAddFundingSourceButton());
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getUserFundingSources();
    }

    @Override
    public CardSettingsModel createModel() {
        return new CardSettingsModel();
    }

    @Override
    public void fundingSourceClickHandler(FundingSourceModel fundingSource) {
        Log.d("ADRIAN", "fundingSourceClickHandler: ");
    }

    @Subscribe
    public void handleResponse(FundingSourceListVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mModel.addFundingSources(response.data);
        mView.showFundingSourceLabel(true);
        mAdapter.updateList(mModel.getFundingSources());
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if(error.statusCode==404) {
            // User has no funding source
            mView.showFundingSourceLabel(false);
            mAdapter.updateList(mModel.getFundingSources());
        }
        else {
            ApiErrorUtil.showErrorMessage(error, mActivity);
        }
    }
}
