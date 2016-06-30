package me.ledge.link.sdk.sdk.tests.robolectric.tests;

import android.os.AsyncTask;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.mocks.util.concurrent.MockExecutor;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link LedgeLinkSdk} class.
 * @author Wijnand
 */
public class LedgeLinkSdkTest {

    @Before
    public void setUp() {
        LedgeLinkSdk.setApiWrapper(new MockApiWrapper());
        LedgeLinkSdk.setExecutor(new MockExecutor());
        LedgeLinkSdk.setResponseHandler(new MockResponseHandler());
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        clearMocks();
    }

    private void clearMocks() {
        LedgeLinkSdk.setApiWrapper(null);
        LedgeLinkSdk.setExecutor(null);
        LedgeLinkSdk.setResponseHandler(null);
    }

    /**
     * Given no Task {@link Executor} has been set yet.<br />
     * When trying to get a reference to the {@link Executor}.<br />
     * Then the default {@link Executor} should be returned.
     */
    @Test
    public void defaultExecutorIsUsed() {
        clearMocks();
        Assert.assertThat("Incorrect Executor.", LedgeLinkSdk.getExecutor(), equalTo(AsyncTask.THREAD_POOL_EXECUTOR));
    }

    /**
     * Given a custom {@link Executor} has been set.<br />
     * When fetching the {@link Executor}.<br />
     * Then the custom {@link Executor} should be returned.
     */
    @Test
    public void customExecutorIsUsed() {
        clearMocks();

        MockExecutor executor = new MockExecutor();
        LedgeLinkSdk.setExecutor(executor);

        Assert.assertThat("Incorrect Executor.", LedgeLinkSdk.getExecutor(), equalTo((Executor) executor));
    }

    /**
     * Given no API Wrapper has been set.<br />
     * When trying to make an API call.<br />
     * Then a {@link NullPointerException} should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void noApiWrapperThrowsError() {
        clearMocks();
        LedgeLinkSdk.createUser(null);
    }

    /**
     * Given no response handler has been set.<br />
     * When trying to make an API call.<br />
     * Then a {@link NullPointerException} should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void noResponseHandlerThrowsError() {
        clearMocks();
        LedgeLinkSdk.setApiWrapper(new MockApiWrapper());
        LedgeLinkSdk.createUser(null);
    }

    @Test
    public void linkDisclaimerTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getLinkDisclaimer(),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to get the loan purpose list.<br />
     * Then the resulting {@link LedgeLinkApiTask} should be returned.
     */
    @Test
    public void loanPurposeTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getLoanPurposesList(),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    @Test
    public void housingTypeListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getHousingTypeList(),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    @Test
    public void employmentStatusesListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getEmploymentStatusesList(),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    @Test
    public void salaryFrequenciesListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getSalaryFrequenciesList(),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    @Test
    public void partnerDisclaimersListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getPartnerDisclaimersList(),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to create a new user.<br />
     * Then the resulting {@link LedgeLinkApiTask} should be returned.
     */
    @Test
    public void createUserTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.createUser(new CreateUserRequestVo()),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    @Test
    public void updateUserTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.updateUser(new CreateUserRequestVo()),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to fetch the initial list of loan offers.<br />
     * Then the resulting {@link LedgeLinkApiTask} should be returned.
     */
    @Test
    public void initialOffersTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getInitialOffers(new InitialOffersRequestVo()),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to create a new loan application.<br />
     * Then the resulting {@link LedgeLinkApiTask} should be returned.
     */
    @Test
    public void createLoanApplicationTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.createLoanApplication(1189998819991197253L),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }

    @Test
    public void loanApplicationsListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                LedgeLinkSdk.getLoanApplicationsList(new ListRequestVo()),
                CoreMatchers.<LedgeLinkApiTask>notNullValue());
    }
}
