package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Income;
import com.shiftpayments.link.sdk.ui.mocks.presenters.userdata.MockMonthlyIncomePresenter;
import com.shiftpayments.link.sdk.ui.mocks.presenters.userdata.MockUserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.mocks.views.userdata.MockMonthlyIncomeView;
import com.shiftpayments.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.tests.robolectric.LibraryProjectTestRunner;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.lang.ref.SoftReference;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(LibraryProjectTestRunner.class)
public class MonthlyIncomePresenterTest {

    private static final double EXPECTED_INCOME = 900;
    private static final int INCOME_MULTIPLIER = 100;

    private AppCompatActivity mActivity;
    private MonthlyIncomePresenter mPresenter;
    private MockMonthlyIncomeView mView;

    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        mPresenter = new MockMonthlyIncomePresenter(mActivity, new MockUserDataCollectorModule(mActivity));
        mView = new MockMonthlyIncomeView(mActivity);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(mActivity, null, null);
        ModuleManager.getInstance().setModule(new SoftReference<>(userDataCollectorModule));
        mPresenter.attachView(mView);
    }

    @Test
    public void absoluteMonthlyNetIncomeStoredInModel() {
        ((MockMonthlyIncomePresenter) mPresenter).setMultiplier(INCOME_MULTIPLIER);

        mView.setIncome(EXPECTED_INCOME / INCOME_MULTIPLIER);
        mPresenter.nextClickHandler();

        DataPointList userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        Income income = (Income) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.Income, null);
        Assert.assertThat("Incorrect monthly net income.", income.monthlyNetIncome, equalTo(EXPECTED_INCOME));
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        mPresenter.detachView();
    }
}
