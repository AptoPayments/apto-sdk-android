package me.ledge.link.sdk.ui.tests.robolectric.tests.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.Employment;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.mocks.presenters.userdata.MockAnnualIncomePresenter;
import me.ledge.link.sdk.ui.mocks.presenters.userdata.MockUserDataCollectorModule;
import me.ledge.link.sdk.ui.mocks.views.userdata.MockAnnualIncomeView;
import me.ledge.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(RobolectricGradleTestRunner.class)
public class AnnualIncomePresenterTest {

    private static final long EXPECTED_INCOME = 90000;
    private static final int EXPECTED_EMPLOYMENT_STATUS = 1;
    private static final int EXPECTED_SALARY_FREQUENCY = 2;

    private AppCompatActivity mActivity;
    private AnnualIncomePresenter mPresenter;
    private AnnualIncomeView mView;

    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        mPresenter = new MockAnnualIncomePresenter(mActivity, new MockUserDataCollectorModule(mActivity));
        mView = new MockAnnualIncomeView(mActivity);
        LedgeLinkUi.setApiWrapper(new MockApiWrapper());
        LedgeLinkUi.setResponseHandler(new MockResponseHandler());
        mPresenter.attachView(mView);
    }

    @Test
    public void absoluteAnnualGrossIncomeStoredInModel() {
        int incomeMultiplier  = mActivity.getResources().getInteger(R.integer.income_increment);
        mView.setIncome(EXPECTED_INCOME / incomeMultiplier);

        mPresenter.nextClickHandler();

        DataPointList userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        Income income = (Income) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.Income, null);
        Assert.assertThat("Incorrect annual gross income.", income.annualGrossIncome, equalTo(EXPECTED_INCOME));
    }

    @Test
    public void employmentStatusIsStoredInModel() {
        mView.setEmploymentStatus(EXPECTED_EMPLOYMENT_STATUS);

        mPresenter.nextClickHandler();

        DataPointList userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        Employment employment = (Employment) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.Employment, null);
        Assert.assertThat("Incorrect employment status.", employment.employmentStatus.getKey(), equalTo(EXPECTED_EMPLOYMENT_STATUS));
    }

    @Test
    public void salaryFrequencyIsStoredInModel() {
        mView.setSalaryFrequency(EXPECTED_SALARY_FREQUENCY);

        mPresenter.nextClickHandler();

        DataPointList userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        Employment employment = (Employment) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.Employment, null);
        Assert.assertThat("Incorrect salary frequency.", employment.salaryFrequency.getKey(), equalTo(EXPECTED_SALARY_FREQUENCY));
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        mPresenter.detachView();
    }

}
