package com.shiftpayments.link.sdk.sdk.tests.robolectric.tests;

import android.os.AsyncTask;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.LoginRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shiftpayments.link.sdk.sdk.mocks.util.concurrent.MockExecutor;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link ShiftLinkSdk} class.
 * @author Wijnand
 */
public class ShiftLinkSdkTest {

    @Before
    public void setUp() {
        ShiftLinkSdk.setApiWrapper(new MockApiWrapper());
        ShiftLinkSdk.setExecutor(new MockExecutor());
        ShiftLinkSdk.setResponseHandler(new MockResponseHandler());
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        clearMocks();
    }

    private void clearMocks() {
        ShiftLinkSdk.setApiWrapper(null);
        ShiftLinkSdk.setExecutor(null);
        ShiftLinkSdk.setResponseHandler(null);
    }

    /**
     * Given no Task {@link Executor} has been set yet.<br />
     * When trying to get a reference to the {@link Executor}.<br />
     * Then the default {@link Executor} should be returned.
     */
    @Test
    public void defaultExecutorIsUsed() {
        clearMocks();
        Assert.assertThat("Incorrect Executor.", ShiftLinkSdk.getExecutor(), equalTo(AsyncTask.THREAD_POOL_EXECUTOR));
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
        ShiftLinkSdk.setExecutor(executor);

        Assert.assertThat("Incorrect Executor.", ShiftLinkSdk.getExecutor(), equalTo((Executor) executor));
    }

    /**
     * Given no API Wrapper has been set.<br />
     * When trying to make an API call.<br />
     * Then a {@link NullPointerException} should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void noApiWrapperThrowsError() {
        clearMocks();
        ShiftLinkSdk.createUser(null);
    }

    /**
     * Given no response handler has been set.<br />
     * When trying to make an API call.<br />
     * Then a {@link NullPointerException} should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void noResponseHandlerThrowsError() {
        clearMocks();
        ShiftLinkSdk.setApiWrapper(new MockApiWrapper());
        ShiftLinkSdk.createUser(null);
    }


    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to get the loan purpose list.<br />
     * Then the resulting {@link ShiftApiTask} should be returned.
     */
    @Test
    public void linkConfigTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getLinkConfig(),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void housingTypeListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getHousingTypeList(),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void incomeTypesListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getIncomeTypesList(),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void salaryFrequenciesListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getSalaryFrequenciesList(),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to create a new user.<br />
     * Then the resulting {@link ShiftApiTask} should be returned.
     */
    @Test
    public void createUserTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.createUser(new DataPointList()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void updateUserTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.updateUser(new DataPointList()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void getCurrentUserTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getCurrentUser(false),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void loginUserTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.loginUser(new LoginRequestVo(null)),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to verify the phone.<br />
     * Then the resulting {@link ShiftApiTask} should be returned.
     */
    @Test
    public void startPhoneVerificationTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.startVerification(new StartVerificationRequestVo()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void completePhoneVerificationTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.completeVerification(new VerificationRequestVo(), ""),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void getVerificationStatusTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getVerificationStatus(""),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to add a financial account.<br />
     * Then the resulting {@link ShiftApiTask} should be returned.
     */
    @Test
    public void addBankAccountTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.addBankAccount(new AddBankAccountRequestVo()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void addCardTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.addCard(new Card()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void issueVirtualCardTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.issueVirtualCard(new IssueVirtualCardRequestVo()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void getFinancialAccountsTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getFinancialAccounts(),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to fetch the initial list of loan offers.<br />
     * Then the resulting {@link ShiftApiTask} should be returned.
     */
    @Test
    public void initialOffersTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getInitialOffers(new InitialOffersRequestVo()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    /**
     * Given an API Wrapper AND a response handler have been set.<br />
     * When trying to create a new loan application.<br />
     * Then the resulting {@link ShiftApiTask} should be returned.
     */
    @Test
    public void createLoanApplicationTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.createLoanApplication("1189998819991197253L"),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }

    @Test
    public void loanApplicationsListTaskIsCreated() {
        Assert.assertThat("Task should have been created.",
                ShiftLinkSdk.getPendingLoanApplicationsList(new ListRequestVo()),
                CoreMatchers.<ShiftApiTask>notNullValue());
    }
}
