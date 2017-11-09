package me.ledge.link.sdk.ui.tests.robolectric.tests.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import java.lang.ref.WeakReference;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.mocks.presenters.userdata.MockMonthlyIncomePresenter;
import me.ledge.link.sdk.ui.mocks.presenters.userdata.MockUserDataCollectorModule;
import me.ledge.link.sdk.ui.mocks.views.userdata.MockMonthlyIncomeView;
import me.ledge.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.storages.UserStorage;

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
