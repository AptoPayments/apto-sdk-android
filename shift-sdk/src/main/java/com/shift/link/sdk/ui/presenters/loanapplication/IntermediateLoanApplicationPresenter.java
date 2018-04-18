package com.shift.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.api.utils.loanapplication.LoanApplicationStatus;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.models.loanapplication.ApprovedLoanApplicationModel;
import com.shift.link.sdk.ui.models.loanapplication.BigButtonModel;
import com.shift.link.sdk.ui.models.loanapplication.ErrorLoanApplicationModel;
import com.shift.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import com.shift.link.sdk.ui.models.loanapplication.PendingLenderActionModel;
import com.shift.link.sdk.ui.models.loanapplication.RejectedLoanApplicationModel;
import com.shift.link.sdk.ui.presenters.ActivityPresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.storages.LoanStorage;
import com.shift.link.sdk.ui.views.loanapplication.IntermediateLoanApplicationView;
import com.shift.link.sdk.ui.views.offers.LoanOfferErrorView;

/**
 * Concrete {@link Presenter} for a loan application in an intermediate state.
 * @author Wijnand
 */
public class IntermediateLoanApplicationPresenter
        extends ActivityPresenter<IntermediateLoanApplicationModel, IntermediateLoanApplicationView>
        implements Presenter<IntermediateLoanApplicationModel, IntermediateLoanApplicationView>,
        LoanOfferErrorView.ViewListener {

    private IntermediateLoanApplicationDelegate mDelegate;

    /**
     * Creates a new {@link IntermediateLoanApplicationPresenter} instance.
     * @param activity Activity.
     */
    public IntermediateLoanApplicationPresenter(AppCompatActivity activity, IntermediateLoanApplicationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /**
     * @param loanApplication Loan application data.
     * @return Default Model to use.
     */
    private IntermediateLoanApplicationModel getDefaultModel(LoanApplicationDetailsResponseVo loanApplication) {
        return new ErrorLoanApplicationModel(loanApplication);
    }

    /**
     * Determines what borrower action to handle.
     * @param loanApplication Loan application data.
     * @return A concrete {@link IntermediateLoanApplicationModel} instance.
     */
    private IntermediateLoanApplicationModel handleBorrowerAction(LoanApplicationDetailsResponseVo loanApplication) {
        IntermediateLoanApplicationModel model = getDefaultModel(loanApplication);
        // TODO: implement via workflow application actions
        /*if (loanApplication == null || loanApplication.required_actions == null
                || loanApplication.required_actions.data == null || loanApplication.required_actions.data.length <= 0
                || loanApplication.required_actions.data[0] == null) {

            return model;
        }

        switch (loanApplication.required_actions.data[0].action) {
            case LoanApplicationActionId.UPLOAD_BANK_STATEMENT:
            case LoanApplicationActionId.UPLOAD_PHOTO_ID:
            case LoanApplicationActionId.UPLOAD_PROOF_OF_ADDRESS:
            case LoanApplicationActionId.UNKNOWN:
                model = new PendingDocumentsModel(loanApplication);
                break;
            case LoanApplicationActionId.AGREE_TERMS:
                model = new PreApprovedLoanApplicationModel(loanApplication);
                break;
            case LoanApplicationActionId.FINISH_APPLICATION_EXTERNAL:
                model = new FinishExternalLoanApplicationModel(loanApplication);
                break;
            default:
                // Do nothing.
                break;
        }*/

        return model;
    }

    /** {@inheritDoc} */
    @Override
    public IntermediateLoanApplicationModel createModel() {
        LoanApplicationDetailsResponseVo loanApplication = LoanStorage.getInstance().getCurrentLoanApplication();
        IntermediateLoanApplicationModel model = getDefaultModel(loanApplication);

        if (loanApplication != null) {
            switch (loanApplication.status) {
                case LoanApplicationStatus.APPLICATION_REJECTED:
                    model = new RejectedLoanApplicationModel(loanApplication);
                    break;
                case LoanApplicationStatus.PENDING_LENDER_ACTION:
                    model = new PendingLenderActionModel(loanApplication);
                    break;
                case LoanApplicationStatus.PENDING_BORROWER_ACTION:
                    model = handleBorrowerAction(loanApplication);
                    break;
                case LoanApplicationStatus.LOAN_APPROVED:
                case LoanApplicationStatus.APPLICATION_RECEIVED:
                    model = new ApprovedLoanApplicationModel(loanApplication);
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        return model;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IntermediateLoanApplicationView view) {
        super.attachView(view);

        mView.setData(mModel);
        mView.setListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.intermediateLoanApplicationShowPrevious(mModel);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void offersClickHandler() {
        LoanStorage.getInstance().setCurrentLoanApplication(null);
        mDelegate.intermediateLoanApplicationShowPrevious(mModel);
    }

    /** {@inheritDoc} */
    @Override
    public void bigButtonClickHandler(int action) {
        switch (action) {
            case BigButtonModel.Action.RETRY_LOAN_APPLICATION:
                mDelegate.intermediateLoanApplicationShowPrevious(mModel);
                break;
            case BigButtonModel.Action.CONFIRM_LOAN:
            case BigButtonModel.Action.UPLOAD_DOCUMENTS:
                mDelegate.intermediateLoanApplicationShowNext(mModel);
                break;
            default:
                // Do nothing.
                break;
        }
    }
}