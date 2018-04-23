package com.shift.link.sdk.ui.presenters.card;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shift.link.sdk.sdk.ShiftLinkSdk;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.ShiftPlatform;
import com.shift.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shift.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shift.link.sdk.ui.models.card.FundingSourceModel;
import com.shift.link.sdk.ui.models.card.ManageAccountModel;
import com.shift.link.sdk.ui.presenters.BasePresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.views.card.FundingSourceView;
import com.shift.link.sdk.ui.views.card.ManageAccountView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Concrete {@link Presenter} for the manage account screen.
 * @author Adrian
 */
public class ManageAccountPresenter
        extends BasePresenter<ManageAccountModel, ManageAccountView>
        implements Presenter<ManageAccountModel, ManageAccountView>, ManageAccountView.ViewListener, FundingSourceView.ViewListener {

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

    @Subscribe
    public void handleResponse(FundingSourceListVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mModel.addFundingSources(mActivity.getResources(), response.data);
        mAdapter.updateList(mModel.getFundingSources());
    }

    @Override
    public void fundingSourceClickHandler(FundingSourceModel selectedFundingSource) {
        List<FundingSourceModel> fundingSources = mModel.getFundingSources().getList();
        for(FundingSourceModel fundingSource : fundingSources) {
            fundingSource.setIsSelected(fundingSource.equals(selectedFundingSource));
        }
        mAdapter.updateList(mModel.getFundingSources());
        Toast.makeText(mActivity, R.string.account_management_funding_source_changed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(mActivity.getString(R.string.account_management_dialog_message))
                .setTitle(mActivity.getString(R.string.account_management_dialog_title));
        builder.setPositiveButton("YES", (dialog, id) -> {
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
    public void onBack() {
        mActivity.onBackPressed();
    }
}
