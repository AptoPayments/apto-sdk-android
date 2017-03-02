package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.api.utils.loanapplication.LoanApplicationActionId;
import me.ledge.link.api.utils.loanapplication.LoanApplicationStatus;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.models.loanapplication.ApprovedLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;
import me.ledge.link.sdk.ui.models.loanapplication.ErrorLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.FinishExternalLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingDocumentsModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingLenderActionModel;
import me.ledge.link.sdk.ui.models.loanapplication.PreApprovedLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.RejectedLoanApplicationModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.views.loanapplication.IntermediateLoanApplicationView;
import me.ledge.link.sdk.ui.views.offers.LoanOfferErrorView;

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
        if (loanApplication == null || loanApplication.required_actions == null
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
        }

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
        mDelegate.showPrevious(mModel);
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
        mDelegate.showPrevious(mModel);
    }

    /** {@inheritDoc} */
    @Override
    public void bigButtonClickHandler(int action) {
        switch (action) {
            case BigButtonModel.Action.RETRY_LOAN_APPLICATION:
                // TODO
                break;
            case BigButtonModel.Action.CONFIRM_LOAN:
            case BigButtonModel.Action.UPLOAD_DOCUMENTS:
                mDelegate.showNext(mModel);
                break;
            default:
                // Do nothing.
                break;
        }
    }
}
