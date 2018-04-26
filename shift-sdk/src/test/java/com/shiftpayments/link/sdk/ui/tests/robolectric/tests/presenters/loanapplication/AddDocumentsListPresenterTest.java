package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationActionId;
import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationStatus;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddBankStatementModel;
import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;
import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddOtherDocumentModel;
import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddPhotoIdModel;
import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddProofOfAddressModel;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.AddDocumentsListPresenter;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import com.shiftpayments.link.sdk.ui.storages.LoanStorage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link AddDocumentsListPresenter} class.
 * @author Wijnand
 */
@RunWith(RobolectricGradleTestRunner.class)
public class AddDocumentsListPresenterTest {

    private AppCompatActivity mActivity;
    private AddDocumentsListPresenter mPresenter;

    /**
     * Creates the {@link AddDocumentsListPresenter}.
     */
    private void createPresenter() {
        mPresenter = new AddDocumentsListPresenter(mActivity, LoanApplicationModule.getInstance(mActivity));
    }

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
     * Verifies that the {@link AddDocumentModel} has been created AND is of the correct type.
     * @param model The Model.
     * @param expectedType The expected type.
     */
    private void verifyModel(AddDocumentModel model, Class<? extends AddDocumentModel> expectedType) {
        Assert.assertThat("Model should be created.", model, not(nullValue()));
        Assert.assertThat("Incorrect type.", model, instanceOf(expectedType));
    }

    /**
     * @return List of required actions.
     */
    private LoanApplicationActionVo[] getActions() {
        return mPresenter.createModel().getRequiredActions();
    }

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        LoanStorage.getInstance().setCurrentLoanApplication(null);
    }

    /**
     * Given a loan application with no pending borrower actions.<br />
     * When creating the view data from the {@link AddDocumentsListPresenter}.<br />
     * Then no {@link AddDocumentModel} should be created.
     */
    @Test
    @Ignore
    public void noModelListIsCreated() {
        createPresenter();
        Assert.assertThat("Model list should be empty.", mPresenter.createViewData(null), is(nullValue()));
    }

    /**
     * Given the loan application requires the user to upload a bank statement.<br />
     * When creating the view data from the {@link AddDocumentsListPresenter}.<br />
     * Then the {@link AddBankStatementModel} should be created.
     */
    @Test
    @Ignore
    public void bankStatementModelCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_LENDER_ACTION, LoanApplicationActionId.UPLOAD_BANK_STATEMENT);

        createPresenter();
        verifyModel(mPresenter.createViewData(getActions())[0], AddBankStatementModel.class);
    }

    /**
     * Given the loan application requires the user to upload a photo ID.<br />
     * When creating the view data from the {@link AddDocumentsListPresenter}.<br />
     * Then the {@link AddPhotoIdModel} should be created.
     */
    @Test
    @Ignore
    public void photoIdModelCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_LENDER_ACTION, LoanApplicationActionId.UPLOAD_PHOTO_ID);

        createPresenter();
        verifyModel(mPresenter.createViewData(getActions())[0], AddPhotoIdModel.class);
    }

    /**
     * Given the loan application requires the user to upload proof of address.<br />
     * When creating the view data from the {@link AddDocumentsListPresenter}.<br />
     * Then the {@link AddProofOfAddressModel} should be created.
     */
    @Test
    @Ignore
    public void proofOfAddressModelCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_LENDER_ACTION, LoanApplicationActionId.UPLOAD_PROOF_OF_ADDRESS);

        createPresenter();
        verifyModel(mPresenter.createViewData(getActions())[0], AddProofOfAddressModel.class);
    }

    /**
     * Given the loan application requires the user to upload some other document.<br />
     * When creating the view data from the {@link AddDocumentsListPresenter}.<br />
     * Then the {@link AddOtherDocumentModel} should be created.
     */
    @Test
    @Ignore
    public void otherDocumentModelCreated() {
        setupCurrentLoanApplication(
                LoanApplicationStatus.PENDING_LENDER_ACTION, LoanApplicationActionId.UNKNOWN);

        createPresenter();
        verifyModel(mPresenter.createViewData(getActions())[0], AddOtherDocumentModel.class);
    }
}
