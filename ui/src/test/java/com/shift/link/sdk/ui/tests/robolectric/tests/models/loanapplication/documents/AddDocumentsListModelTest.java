package com.shift.link.sdk.ui.tests.robolectric.tests.models.loanapplication.documents;

import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import com.shift.link.sdk.ui.utils.HandlerConfigurator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import com.shift.link.sdk.ui.utils.HandlerConfigurator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Tests the {@link AddDocumentsListModel} class.
 * @author Wijnand
 */
public class AddDocumentsListModelTest {

    private class TestLoanApplicationsListActivity { }

    private class TestHandlerConfigurator implements HandlerConfigurator {
        /** {@inheritDoc} */
        @Override
        public ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
            return null;
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
        LoanApplicationDetailsResponseVo application = new MockApiWrapper().createLoanApplication(null);

        mModel = new AddDocumentsListModel(application);

        Assert.assertThat("No actions should be available", mModel.getRequiredActions(), is(nullValue()));
    }

    /**
     * Given a loan application with a list of required actions.<br />
     * When creating a new {@link AddDocumentsListModel}.<br />
     * Then an actions list should be available.
     */
    @Test
    @Ignore
    public void populatedActionsList() {
        LoanApplicationDetailsResponseVo application = new MockApiWrapper().createLoanApplication(null);
        mModel = new AddDocumentsListModel(application);
        Assert.assertThat("Actions should be populated.", mModel.getRequiredActions(), not(nullValue()));
    }
}
