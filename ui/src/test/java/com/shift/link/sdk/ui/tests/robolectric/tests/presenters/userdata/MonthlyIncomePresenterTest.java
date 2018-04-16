package com.shift.link.sdk.ui.tests.robolectric.tests.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import com.shift.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.workflow.ModuleManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import java.lang.ref.WeakReference;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.Income;
import com.shift.link.sdk.ui.workflow.ModuleManager;
import com.shift.link.sdk.ui.mocks.presenters.userdata.MockMonthlyIncomePresenter;
import com.shift.link.sdk.ui.mocks.presenters.userdata.MockUserDataCollectorModule;
import com.shift.link.sdk.ui.mocks.views.userdata.MockMonthlyIncomeView;
import com.shift.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import com.shift.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shift.link.sdk.ui.storages.UserStorage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(RobolectricGradleTestRunner.class)
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
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(mActivity);
        ModuleManager.getInstance().setModule(new WeakReference<>(userDataCollectorModule));
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
