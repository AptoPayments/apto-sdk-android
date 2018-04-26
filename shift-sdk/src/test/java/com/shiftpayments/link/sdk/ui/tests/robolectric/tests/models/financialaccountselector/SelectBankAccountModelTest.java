package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.financialaccountselector;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.datapoints.BankAccount;
import com.shiftpayments.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.SelectBankAccountModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests the {@link SelectBankAccountModel} class.
 * @author Adrian
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class SelectBankAccountModelTest {

    private SelectBankAccountModel mModel;
    private BankAccount mBankAccount;
    private static final String EXPECTED_ACCOUNT_ID = "123";
    private static final String EXPECTED_BANK_NAME = "Fake Bank";
    private static final String EXPECTED_LAST_FOUR_DIGITS = "4444";

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new IsEmptyAnswer());

        mBankAccount = new BankAccount(EXPECTED_ACCOUNT_ID, EXPECTED_BANK_NAME, EXPECTED_LAST_FOUR_DIGITS, false);
        mModel = new SelectBankAccountModel(mBankAccount);
    }

    /**
     * Given an empty Model.<br />
     * When fetching the description.<br />
     * Then the correct description should be returned.
     */
    @Test
    public void hasCorrectDescriptionResource() {
        Assert.assertThat("Description should be populated.", mModel.getDescription(), not(nullValue()));
        Assert.assertThat("Description should contain bank name.", mModel.getDescription(), containsString(EXPECTED_BANK_NAME));
        Assert.assertThat("Description should contain last four digits.", mModel.getDescription(), containsString(EXPECTED_LAST_FOUR_DIGITS));
    }

    /**
     * Given an empty Model.<br />
     * When fetching the financial account.<br />
     * Then the bank account should be returned.
     */
    @Test
    public void returnsCorrectFinancialAccount() {
        Assert.assertThat("Financial account should be stored.", mModel.getFinancialAccount(), not(nullValue()));
        Assert.assertThat("Bank account is not correct.", mModel.getFinancialAccount(), equalTo(mBankAccount));
    }
}
