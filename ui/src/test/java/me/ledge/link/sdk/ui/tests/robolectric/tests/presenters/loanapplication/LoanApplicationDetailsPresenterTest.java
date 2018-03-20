package me.ledge.link.sdk.ui.tests.robolectric.tests.presenters.loanapplication;

import android.content.res.Resources;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowContentProvider;

import me.ledge.link.sdk.api.utils.loanapplication.LoanApplicationStatus;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.ui.models.loanapplication.details.ApprovedLoanApplicationDetailsModel;
import me.ledge.link.sdk.ui.models.loanapplication.details.FinishExternalLoanApplicationDetailsModel;
import me.ledge.link.sdk.ui.models.loanapplication.details.LoanApplicationDetailsModel;
import me.ledge.link.sdk.ui.models.loanapplication.details.PendingLenderActionLoanApplicationDetailsModel;
import me.ledge.link.sdk.ui.models.loanapplication.details.UploadDocsLoanApplicationDetailsModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationDetailsPresenter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link LoanApplicationDetailsPresenter} class.
 * @author Wijnand
 */
@RunWith(RobolectricGradleTestRunner.class)
public class LoanApplicationDetailsPresenterTest {

    private LoanApplicationDetailsPresenter mPresenter;
    private Resources mResources;
    private LoanApplicationDetailsResponseVo mLoanApplication;

    /**
     * Creates a new {@link LoanApplicationDetailsPresenter}.
     */
    private void createPresenter() {
        mPresenter = new LoanApplicationDetailsPresenter(mResources, mLoanApplication);
    }

    /**
     * Verifies that the {@link LoanApplicationDetailsModel} has been created AND is of the correct type.
     * @param model The Model.
     * @param expectedType The expected type.
     */
    private void verifyModel(LoanApplicationDetailsModel model,
            Class<? extends LoanApplicationDetailsModel> expectedType) {

        Assert.assertThat("Model should be created.", model, not(nullValue()));
        Assert.assertThat("Incorrect type.", model, instanceOf(expectedType));
    }

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mResources = new ShadowContentProvider().getContext().getResources();
        mLoanApplication = new MockApiWrapper().createLoanApplication("");
    }

    /**
     * Given no loan application is available.<br />
     * When creating a Model from the Presenter.<br />
     * Then no model should be created.
     */
    @Test
    public void noLoanApplicationNoModel() {
        createPresenter();
        Assert.assertThat("No model should be created.", mPresenter.createModel(), is(nullValue()));
    }

    /**
     * Given a loan application with the {@code PENDING_BORROWER_ACTION}
     *  AND an {@code AGREE_TERMS} action.<br />
     * When creating a Model from the Presenter<br />
     * Then a Model of the {@link ApprovedLoanApplicationDetailsModel} type should be created.
     */
    @Test
    @Ignore
    public void agreeTermsModelIsCreated() {
        mLoanApplication.status = LoanApplicationStatus.PENDING_BORROWER_ACTION;

        createPresenter();
        verifyModel(mPresenter.createModel(), ApprovedLoanApplicationDetailsModel.class);
    }

    /**
     * Given a loan application with the {@code PENDING_BORROWER_ACTION}
     *  AND an {@code UPLOAD_PHOTO_ID} action.<br />
     * When creating a Model from the Presenter<br />
     * Then a Model of the {@link UploadDocsLoanApplicationDetailsModel} type should be created.
     */
    @Test
    @Ignore
    public void uploadDocumentsModelIsCreated() {
        mLoanApplication.status = LoanApplicationStatus.PENDING_BORROWER_ACTION;

        createPresenter();
        verifyModel(mPresenter.createModel(), UploadDocsLoanApplicationDetailsModel.class);
    }

    /**
     * Given a loan application with the {@code PENDING_BORROWER_ACTION}
     *  AND an {@code FINISH_APPLICATION_EXTERNAL} action.<br />
     * When creating a Model from the Presenter<br />
     * Then a Model of the {@link FinishExternalLoanApplicationDetailsModel} type should be created.
     */
    @Test
    @Ignore
    public void finishExternalModelIsCreated() {
        mLoanApplication.status = LoanApplicationStatus.PENDING_BORROWER_ACTION;
        createPresenter();
        verifyModel(mPresenter.createModel(), FinishExternalLoanApplicationDetailsModel.class);
    }

    /**
     * Given a loan application with the {@code PENDING_LENDER_ACTION}.<br />
     * When creating a Model from the Presenter<br />
     * Then a Model of the {@link PendingLenderActionLoanApplicationDetailsModel} type should be created.
     */
    @Test
    @Ignore
    public void pendingLenderActionModelIsCreated() {
        mLoanApplication.status = LoanApplicationStatus.PENDING_LENDER_ACTION;

        createPresenter();
        verifyModel(mPresenter.createModel(), PendingLenderActionLoanApplicationDetailsModel.class);
    }

    /**
     * Given a loan application with the {@code APPLICATION_RECEIVED}.<br />
     * When creating a Model from the Presenter<br />
     * Then a Model of the {@link PendingLenderActionLoanApplicationDetailsModel} type should be created.
     */
    @Test
    @Ignore
    public void applicationReceivedModelIsCreated() {
        mLoanApplication.status = LoanApplicationStatus.APPLICATION_RECEIVED;

        createPresenter();
        verifyModel(mPresenter.createModel(), PendingLenderActionLoanApplicationDetailsModel.class);
    }

}
