package me.ledge.link.sdk.ui.tests.robolectric.tests.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.mocks.presenters.userdata.MockMonthlyIncomePresenter;
import me.ledge.link.sdk.ui.mocks.views.userdata.MockMonthlyIncomeView;
import me.ledge.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.userdata.MonthlyIncomeView;
import me.ledge.link.sdk.ui.vos.UserDataVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(RobolectricGradleTestRunner.class)
public class MonthlyIncomePresenterTest {

    private static final int EXPECTED_INCOME = 900;

    private AppCompatActivity mActivity;
    private MonthlyIncomePresenter mPresenter;
    private MonthlyIncomeView mView;

    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        mPresenter = new MockMonthlyIncomePresenter(mActivity);
        mView = new MockMonthlyIncomeView(mActivity);
    }

    @Test
    public void absoluteMonthlyNetIncomeStoredInModel() {
        int incomeMultiplier  = mActivity.getResources().getInteger(R.integer.monthly_income_increment);
        mPresenter.attachView(mView);

        mView.setIncome(EXPECTED_INCOME / incomeMultiplier);
        mPresenter.nextClickHandler();

        UserDataVo userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        Assert.assertThat("Incorrect monthly net income.", userData.monthlyNetIncome, equalTo(EXPECTED_INCOME));
    }

}
