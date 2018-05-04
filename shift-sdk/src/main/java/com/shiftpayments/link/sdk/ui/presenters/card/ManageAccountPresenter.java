package com.shiftpayments.link.sdk.ui.presenters.card;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shiftpayments.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shiftpayments.link.sdk.ui.models.card.FundingSourceModel;
import com.shiftpayments.link.sdk.ui.models.card.ManageAccountModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.SendEmailUtil;
import com.shiftpayments.link.sdk.ui.views.card.FundingSourceView;
import com.shiftpayments.link.sdk.ui.views.card.ManageAccountView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Concrete {@link Presenter} for the manage account screen.
 * @author Adrian
 */
public class ManageAccountPresenter
        extends BasePresenter<ManageAccountModel, ManageAccountView>
        implements Presenter<ManageAccountModel, ManageAccountView>, ManageAccountView.ViewListener,
        FundingSourceView.ViewListener {

    private FundingSourcesListRecyclerAdapter mAdapter;
    private ManageAccountActivity mActivity;
    private ManageAccountDelegate mDelegate;

    public ManageAccountPresenter(ManageAccountActivity activity, ManageAccountDelegate delegate) {
        mActivity = activity;
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ManageAccountView view) {
        super.attachView(view);
        view.setViewListener(this);
        mActivity.setSupportActionBar(mView.getToolbar());
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAdapter = new FundingSourcesListRecyclerAdapter();
        mAdapter.setViewListener(this);
        view.setAdapter(mAdapter);
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getUserFundingSources();
    }

    @Override
    public ManageAccountModel createModel() {
        return new ManageAccountModel();
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
    public void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(mActivity.getString(R.string.account_management_dialog_message))
                .setTitle(mActivity.getString(R.string.account_management_dialog_title));
        builder.setPositiveButton("YES", (dialog, id) -> {
            ShiftLinkSdk.getResponseHandler().unsubscribe(this);
            mActivity.finish();
            mDelegate.onSignOut();
        });
        builder.setNegativeButton("NO", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void addFundingSource() {
        mDelegate.addFundingSource(()->Toast.makeText(mActivity, R.string.account_management_funding_source_added, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void contactSupport() {
        new SendEmailUtil(UIStorage.getInstance().getContextConfig().supportEmailAddress).execute(mActivity);
    }

    @Override
    public void onBack() {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mActivity.onBackPressed();
    }

    @Subscribe
    public void handleResponse(FundingSourceListVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mView.showFundingSourceLabel(true);
        mModel.addFundingSources(mActivity.getResources(), response.data);
        mAdapter.updateList(mModel.getFundingSources());
    }

    @Subscribe
    public void handleResponse(FundingSourceVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
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
            Toast.makeText(mActivity, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
        mDelegate.onSessionExpired(error);
    }
}
