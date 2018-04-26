package com.shiftpayments.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationSummaryResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import com.shiftpayments.link.sdk.ui.models.loanapplication.SelectLoanApplicationListModel;
import com.shiftpayments.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.loanapplication.SelectPendingApplicationListView;


/**
 * Concrete {@link Presenter} for the select loan application screen.
 * @author Adrian
 */
public class SelectLoanApplicationListPresenter
        extends ActivityPresenter<SelectLoanApplicationListModel, SelectPendingApplicationListView>
        implements Presenter<SelectLoanApplicationListModel, SelectPendingApplicationListView>, SelectPendingApplicationListView.ViewListener {

    private SelectLoanApplicationListDelegate mDelegate;

    /**
     * Creates a new {@link SelectLoanApplicationListPresenter} instance.
     * @param activity Activity.
     */
    public SelectLoanApplicationListPresenter(AppCompatActivity activity, SelectLoanApplicationListDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
    }

    /** {@inheritDoc} */
    @Override
    public SelectLoanApplicationListModel createModel() {
        return new SelectLoanApplicationListModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        initToolbar();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(SelectPendingApplicationListView view) {
        super.attachView(view);

        SelectLoanApplicationModel[] viewData = createViewData(mDelegate.getLoanApplicationsSummaryList());
        if(viewData != null) {
            mView.setData(viewData);
        }
        mView.setViewListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.selectLoanApplicationListOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        super.detachView();
    }

    private SelectLoanApplicationModel[] createViewData(LoanApplicationsSummaryListResponseVo applicationList) {
        final int applicationListSize = applicationList.total_count;
        if (applicationListSize == 0) {
            return null;
        }

        SelectLoanApplicationModel[] data = new SelectLoanApplicationModel[applicationListSize];

        for (int i = 0; i< applicationListSize; i++) {
            LoanApplicationSummaryResponseVo loanApplicationSummaryResponseVo = applicationList.data[i];
            data[i] = new SelectLoanApplicationModel() {
                @Override
                public String getProjectName() {
                    return loanApplicationSummaryResponseVo.projectName;
                }

                @Override
                public float getLoanAmount() {
                    return loanApplicationSummaryResponseVo.loanAmount;
                }

                @Override
                public String getTimestamp() {
                    return loanApplicationSummaryResponseVo.timestamp;
                }

                @Override
                public String getApplicationId() {
                    return loanApplicationSummaryResponseVo.id;
                }
            };
        }

        return data;
    }

    @Override
    public void applicationClickHandler(SelectLoanApplicationModel model) {
        mDelegate.applicationSelected(model);
    }

    @Override
    public void newApplicationClickHandler() {
        mDelegate.newApplicationPressed();
    }
}
