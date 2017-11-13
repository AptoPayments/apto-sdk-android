package me.ledge.link.sdk.ui.tests.robolectric.tests.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import me.ledge.link.api.utils.loanapplication.LoanApplicationActionId;
import me.ledge.link.api.utils.loanapplication.LoanApplicationStatus;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.ui.models.loanapplication.ApprovedLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.ErrorLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.FinishExternalLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingDocumentsModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingLenderActionModel;
import me.ledge.link.sdk.ui.models.loanapplication.PreApprovedLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.RejectedLoanApplicationModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.IntermediateLoanApplicationPresenter;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.storages.LoanStorage;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link IntermediateLoanApplicationPresenter} class.
 * @author Wijnand
 */
@RunWith(RobolectricGradleTestRunner.class)
public class IntermediateLoanApplicationPresenterTest {

    private IntermediateLoanApplicationPresenter mPresenter;

    /**
     * Sets up the current loan application in the {@link LoanStorage}.
     * @param status Loan application status.
     * @param action Required action for the loan application.
     */
    private void setupCurrentLoanApplication(String status, String action) {
        LoanApplicationDetailsResponseVo application = new MockApiWrapper().createLoanApplication(null);
        application.status = status;

        LoanStorage.getInstance().setCurrentLoanApplication(application);
    }

    /**
     * Verifies that the {@link IntermediateLoanApplicationModel} has been created AND is of the correct type.
     * @param model The Model.
     * @param expectedType The expected type.
     */
    private void verifyModel(IntermediateLoanApplicationModel model,
            Class<? extends IntermediateLoanApplicationModel> expectedType) {

        Assert.assertThat("Model should be created.", model, not(nullValue()));
        Assert.assertThat("Incorrect type.", model, instanceOf(expectedType));
    }

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        AppCompatActivity activity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        mPresenter = new IntermediateLoanApplicationPresenter(activity, LoanApplicationModule.getInstance(activity));
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        LoanStorage.getInstance().setCurrentLoanApplication(null);
    }

    /**
     * Given no loan application result is available.<br />
     * When creating a Model from the Presenter.<br />
     * Then the default {@link ErrorLoanApplicationModel} should be created.
     */
    @Test
    @Ignore
    public void noLoanApplicationCreatesErrorModel() {
        verifyModel(mPresenter.createModel(), ErrorLoanApplicationModel.class);
    }

    /**
     * Given the loan application has been rejected.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link RejectedLoanApplicationModel} type should be created.
     */
    @Test
    @Ignore
    public void applicationRejectedModelIsCreated() {
        setupCurrentLoanApplication(LoanApplicationStatus.APPLICATION_REJECTED, "");
        verifyModel(mPresenter.createModel(), RejectedLoanApplicationModel.class);
    }

    /**
     * Given the loan application is pending some action from the lender.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link PendingLenderActionModel} type should be created.
     */
    @Test
    @Ignore
    public void pendingLenderActionModelIsCreated() {
        setupCurrentLoanApplication(LoanApplicationStatus.PENDING_LENDER_ACTION, "");
        verifyModel(mPresenter.createModel(), PendingLenderActionModel.class);
    }

    /**
     * Given the loan application requires the user to upload a bank statement.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link PendingDocumentsModel} type should be created.
     */
    @Test
    @Ignore
    public void uploadBankStatementModelIsCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_BORROWER_ACTION, LoanApplicationActionId.UPLOAD_BANK_STATEMENT);
        verifyModel(mPresenter.createModel(), PendingDocumentsModel.class);
    }

    /**
     * Given the loan application requires the user to upload a photo ID.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link PendingDocumentsModel} type should be created.
     */
    @Test
    @Ignore
    public void uploadIdStatementModelIsCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_BORROWER_ACTION, LoanApplicationActionId.UPLOAD_PHOTO_ID);
        verifyModel(mPresenter.createModel(), PendingDocumentsModel.class);
    }

    /**
     * Given the loan application is pre-approved.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link PreApprovedLoanApplicationModel} type should be created.
     */
    @Test
    @Ignore
    public void preApprovedModelIsCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_BORROWER_ACTION, LoanApplicationActionId.AGREE_TERMS);
        verifyModel(mPresenter.createModel(), PreApprovedLoanApplicationModel.class);
    }

    /**
     * Given the loan application needs to be finished externally.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link FinishExternalLoanApplicationModel} type should be created.
     */
    @Test
    @Ignore
    public void finishExternalModelIsCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_BORROWER_ACTION, LoanApplicationActionId.FINISH_APPLICATION_EXTERNAL);
        verifyModel(mPresenter.createModel(), FinishExternalLoanApplicationModel.class);
    }

    /**
     * Given the loan application has been approved.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link ApprovedLoanApplicationModel} type should be created.
     */
    @Test
    @Ignore
    public void approvedModelIsCreated() {
        setupCurrentLoanApplication(LoanApplicationStatus.LOAN_APPROVED, "");
        verifyModel(mPresenter.createModel(), ApprovedLoanApplicationModel.class);
    }

    /**
     * Given the loan application has been received by the lender.<br />
     * When creating a Model from the Presenter.<br />
     * Then a Model of the {@link ApprovedLoanApplicationModel} type should be created.
     */
    @Test
    @Ignore
    public void applicationReceivedModelIsCreated() {
        setupCurrentLoanApplication(LoanApplicationStatus.APPLICATION_RECEIVED, "");
        verifyModel(mPresenter.createModel(), ApprovedLoanApplicationModel.class);
    }
}
