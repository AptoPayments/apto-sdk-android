package com.shiftpayments.link.sdk.ui.presenters.card;

import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.CardSettingsActivity;
import com.shiftpayments.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shiftpayments.link.sdk.ui.models.card.CardSettingsModel;
import com.shiftpayments.link.sdk.ui.models.card.FundingSourceModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.views.card.CardSettingsView;
import com.shiftpayments.link.sdk.ui.views.card.FundingSourceView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Concrete {@link Presenter} for the card settings screen.
 * @author Adrian
 */
public class CardSettingsPresenter
        extends BasePresenter<CardSettingsModel, CardSettingsView>
        implements Presenter<CardSettingsModel, CardSettingsView>,FundingSourceView.ViewListener, CardSettingsView.ViewListener {

    private CardSettingsActivity mActivity;
    private FundingSourcesListRecyclerAdapter mAdapter;

    public CardSettingsPresenter(CardSettingsActivity activity) {
        mActivity = activity;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(CardSettingsView view) {
        super.attachView(view);
        view.setViewListener(this);
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
    public void fundingSourceClickHandler(FundingSourceModel selectedFundingSource) {
        List<FundingSourceModel> fundingSources = mModel.getFundingSources().getList();
        for(FundingSourceModel fundingSource : fundingSources) {
            fundingSource.setIsSelected(fundingSource.equals(selectedFundingSource));
        }
        mAdapter.updateList(mModel.getFundingSources());
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.setAccountFundingSource(CardStorage.getInstance().getCard().mAccountId, selectedFundingSource.getFundingSourceId());
    }


    @Override
    public void addFundingSource() {
        /*mDelegate.addFundingSource(()->Toast.makeText(mActivity, R.string.account_management_funding_source_added, Toast.LENGTH_SHORT).show());*/
    }

    @Override
    public void onClose() {
        mActivity.finish();
    }

    @Subscribe
    public void handleResponse(FundingSourceListVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mModel.addFundingSources(response.data);
        mView.showFundingSourceLabel(true);
        mAdapter.updateList(mModel.getFundingSources());
    }

    @Subscribe
    public void handleResponse(FundingSourceVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mModel.setSelectedFundingSource(response.id);
        mAdapter.updateList(mModel.getFundingSources());
        Toast.makeText(mActivity, R.string.account_management_funding_source_changed, Toast.LENGTH_SHORT).show();
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

    /**
     * Called when a session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mActivity.finish();
        /*mDelegate.onSessionExpired(error);*/
    }
}
