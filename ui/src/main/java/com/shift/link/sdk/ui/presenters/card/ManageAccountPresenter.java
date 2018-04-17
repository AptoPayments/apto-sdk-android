package com.shift.link.sdk.ui.presenters.card;

/**
 * Created by adrian on 27/11/2017.
 */


import android.app.Activity;
import android.widget.Toast;

import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shift.link.sdk.sdk.ShiftLinkSdk;
import com.shift.link.sdk.ui.ShiftPlatform;
import com.shift.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shift.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shift.link.sdk.ui.models.card.FundingSourceModel;
import com.shift.link.sdk.ui.models.card.ManageAccountModel;
import com.shift.link.sdk.ui.presenters.BasePresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.views.card.FundingSourceView;
import com.shift.link.sdk.ui.views.card.ManageAccountView;
import com.shift.link.sdk.ui.workflow.ModuleManager;

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
    private Activity mActivity;

    public ManageAccountPresenter(Activity activity) {
        mActivity = activity;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ManageAccountView view) {
        super.attachView(view);
        view.setViewListener(this);
        ((ManageAccountActivity) mActivity).setSupportActionBar(mView.getToolbar());
        ((ManageAccountActivity) mActivity).getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        Toast.makeText(mActivity, "Funding source changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signOut() {
        CardModule currentModule = (CardModule) ModuleManager.getInstance().getCurrentModule();
        currentModule.showHomeActivity();
    }

    @Override
    public void addFundingSource() {
        CardModule currentModule = (CardModule) ModuleManager.getInstance().getCurrentModule();
        currentModule.startCustodianModule(currentModule::startManageCardScreen, ()->{
            Toast.makeText(mActivity, "Funding source added", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBack() {
        mActivity.onBackPressed();
    }
}
