package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountListModel;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.FinancialAccountSelectorModule;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListPresenter;
import com.shiftpayments.link.sdk.ui.tests.robolectric.LibraryProjectTestRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link SelectFinancialAccountListPresenter} class.
 * @author Adrian
 */
@RunWith(LibraryProjectTestRunner.class)
public class SelectFinancialAccountListPresenterTest {

    private SelectFinancialAccountListPresenter mPresenter;
    private AppCompatActivity mActivity;
    private FinancialAccountSelectorModule mModule;
    private String expectedAccountId;

    /**
     * Creates a new {@link SelectFinancialAccountListPresenter}.
     */
    private void createPresenter() {
        mPresenter = new SelectFinancialAccountListPresenter(mActivity, mModule);
    }

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        mModule = FinancialAccountSelectorModule.getInstance(mActivity, new SelectFundingAccountConfigurationVo(true, true, true));
        mModule.onFinish = selectedFinancialAccount -> expectedAccountId = selectedFinancialAccount;
        createPresenter();
    }

    /**
     * Verifies that the {@link SelectFinancialAccountListModel} has been created AND is of the correct type.
     */
    @Test
    public void selectFinancialAccountListModelIsCreated() {
        Assert.assertThat("Model should be created.", mPresenter.createModel(), not(nullValue()));
        Assert.assertThat("Incorrect type.", mPresenter.createModel(), instanceOf(SelectFinancialAccountListModel.class));
    }

    /**
     * Verifies that the selected account is passed on to the Module
     */
    @Test
    public void selectedFinancialAccountIsCorrectlyPassed() {
        FinancialAccountVo account = new FinancialAccountVo(null, null, false);
        SelectFinancialAccountModel financialAccountModel = new SelectFinancialAccountModel() {
            @Override
            public int getIconResourceId() {
                return 0;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public FinancialAccountVo getFinancialAccount() {
                return account;
            }
        };
        mPresenter.accountClickHandler(financialAccountModel);
        Assert.assertEquals("Selected account should be passed.", expectedAccountId, account.mAccountId);
    }
}
