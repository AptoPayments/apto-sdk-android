package me.ledge.link.sdk.ui.tests.robolectric.tests.models.loanapplication.documents;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Tests the {@link AddDocumentsListModel} class.
 * @author Wijnand
 */
public class AddDocumentsListModelTest {

    private class TestLoanApplicationsListActivity extends LoanApplicationsListActivity { }

    private class TestHandlerConfigurator implements HandlerConfigurator {
        /** {@inheritDoc} */
        @Override
        public ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
            return null;
        }

        /** {@inheritDoc} */
        @Override
        public Class<? extends LoanApplicationsListActivity> getApplicationsListActivity() {
            return TestLoanApplicationsListActivity.class;
        }

        /** {@inheritDoc} */
        @Override
        public ApiResponseHandler getResponseHandler() {
            return null;
        }
    }

    private AddDocumentsListModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new AddDocumentsListModel(null);
    }

    /**
     * Given a {@link AddDocumentsListModel}.<br />
     * When fetching the Activity label.<br />
     * Then the correct label should be returned.
     */
    @Test
    public void hasCorrectTitle() {
        Assert.assertThat("Incorrect title resource.",
                mModel.getActivityTitleResource(),
                equalTo(R.string.add_documents_label));
    }

    /**
     * Given a {@link AddDocumentsListModel}.<br />
     * When the back button is pressed.<br />
     * Then the correct Activity should be started.
     */
    @Test
    public void hasCorrectPreviousActivity() {
        Assert.assertThat("Incorrect previous Activity.",
                (Class<IntermediateLoanApplicationActivity>) mModel.getPreviousActivity(null),
                equalTo(IntermediateLoanApplicationActivity.class));
    }

    /**
     * Given a {@link AddDocumentsListModel}.<br />
     * When the "done" button is pressed.<br />
     * Then the correct Activity should be started.
     */
    @Test
    public void hasCorrectNextActivity() {
        LedgeLinkUi.setHandlerConfiguration(new TestHandlerConfigurator());

        Assert.assertThat("Incorrect next Activity.",
                (Class<? extends LoanApplicationsListActivity>) mModel.getNextActivity(null),
                instanceOf(LoanApplicationsListActivity.class.getClass()));
    }

    /**
     * Given no loan application.<br />
     * When creating a new {@link AddDocumentsListModel}.<br />
     * Then no actions list should be available.
     */
    @Test
    public void noLoanApplicationNoActions() {
        Assert.assertThat("No actions should be available", mModel.getRequiredActions(), is(nullValue()));
    }

    /**
     * Given a loan application with no required actions.<br />
     * When creating a new {@link AddDocumentsListModel}.<br />
     * Then no actions list should be available.
     */
    @Test
    public void emptyActionsList() {
        LoanApplicationDetailsResponseVo application = new MockApiWrapper().createLoanApplication(0);
        application.required_actions = null;

        mModel = new AddDocumentsListModel(application);

        Assert.assertThat("No actions should be available", mModel.getRequiredActions(), is(nullValue()));
    }

    /**
     * Given a loan application with a list of required actions.<br />
     * When creating a new {@link AddDocumentsListModel}.<br />
     * Then an actions list should be available.
     */
    @Test
    public void populatedActionsList() {
        LoanApplicationDetailsResponseVo application = new MockApiWrapper().createLoanApplication(0);
        mModel = new AddDocumentsListModel(application);
        Assert.assertThat("Actions should be populated.", mModel.getRequiredActions(), not(nullValue()));
    }
}
