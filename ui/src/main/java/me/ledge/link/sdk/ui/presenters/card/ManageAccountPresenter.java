package me.ledge.link.sdk.ui.presenters.card;

/**
 * Created by adrian on 27/11/2017.
 */


import android.app.Activity;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import me.ledge.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.ShiftUi;
import me.ledge.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import me.ledge.link.sdk.ui.models.card.FundingSourceModel;
import me.ledge.link.sdk.ui.models.card.ManageAccountModel;
import me.ledge.link.sdk.ui.presenters.BasePresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.card.FundingSourceView;
import me.ledge.link.sdk.ui.views.card.ManageAccountView;
import me.ledge.link.sdk.ui.workflow.ModuleManager;

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
        mAdapter = new FundingSourcesListRecyclerAdapter();
        mAdapter.setViewListener(this);
        view.setAdapter(mAdapter);
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        ShiftUi.getUserFundingSources();
    }

    @Override
    public ManageAccountModel createModel() {
        return new ManageAccountModel();
    }

    @Subscribe
    public void handleResponse(FundingSourceListVo response) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
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
}
