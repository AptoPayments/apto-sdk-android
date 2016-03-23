package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.utils.loanapplication.LoanApplicationActionId;
import me.ledge.link.api.utils.loanapplication.LoanApplicationStatus;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;
import me.ledge.link.sdk.ui.models.loanapplication.ErrorLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingDocumentsModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingLenderActionModel;
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

    /**
     * Creates a new {@link IntermediateLoanApplicationPresenter} instance.
     * @param activity Activity.
     */
    public IntermediateLoanApplicationPresenter(AppCompatActivity activity) {
        super(activity);
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

        switch (loanApplication.required_actions.data[0].action) {
            case LoanApplicationActionId.UPLOAD_BANK_STATEMENT:
            case LoanApplicationActionId.UPLOAD_PHOTO_ID:
                model = new PendingDocumentsModel(loanApplication);
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
                case LoanApplicationStatus.LENDER_REJECTED:
                    model = new RejectedLoanApplicationModel(loanApplication);
                    break;
                case LoanApplicationStatus.PENDING_LENDER_ACTION:
                    model = new PendingLenderActionModel(loanApplication);
                    break;
                case LoanApplicationStatus.PENDING_BORROWER_ACTION:
                    model = handleBorrowerAction(loanApplication);
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

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void startPreviousActivity() {
        LoanStorage.getInstance().setCurrentLoanApplication(null);
        super.startPreviousActivity();
    }

    /** {@inheritDoc} */
    @Override
    public void offersClickHandler() {
        startPreviousActivity();
    }

    /** {@inheritDoc} */
    @Override
    public void infoClickHandler() {
        // TODO Pending API update.
    }

    /** {@inheritDoc} */
    @Override
    public void bigButtonClickHandler(int action) {
        switch (action) {
            case BigButtonModel.Action.RETRY_LOAN_APPLICATION:
                // TODO
                break;
            default:
                // Do nothing.
                break;
        }
    }
}
