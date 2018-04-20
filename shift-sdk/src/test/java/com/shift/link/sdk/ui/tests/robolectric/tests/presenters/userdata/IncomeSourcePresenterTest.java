package com.shift.link.sdk.ui.tests.robolectric.tests.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.IncomeSource;
import com.shift.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.IncomeTypeListResponseVo;
import com.shift.link.sdk.api.vos.responses.config.IncomeTypeVo;
import com.shift.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shift.link.sdk.api.vos.responses.config.SalaryFrequenciesListResponseVo;
import com.shift.link.sdk.api.vos.responses.config.SalaryFrequencyVo;
import com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shift.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shift.link.sdk.ui.BuildConfig;
import com.shift.link.sdk.ui.ShiftPlatform;
import com.shift.link.sdk.ui.mocks.presenters.userdata.MockAnnualIncomePresenter;
import com.shift.link.sdk.ui.mocks.presenters.userdata.MockUserDataCollectorModule;
import com.shift.link.sdk.ui.mocks.views.userdata.MockAnnualIncomeView;
import com.shift.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;
import com.shift.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shift.link.sdk.ui.storages.UIStorage;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.views.userdata.AnnualIncomeView;
import com.shift.link.sdk.ui.workflow.ModuleManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.ref.SoftReference;

import static com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper.EXPECTED_INCOME_TYPE;
import static com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper.EXPECTED_SALARY_FREQUENCY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class IncomeSourcePresenterTest {

    private AnnualIncomePresenter mPresenter;
    private AnnualIncomeView mView;

    @Before
    public void setUp() {
        AppCompatActivity mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(mActivity);
        userDataCollectorModule.mRequiredDataPointList.add(new RequiredDataPointVo(DataPointVo.DataPointType.IncomeSource));
        ModuleManager.getInstance().setModule(new SoftReference<>(userDataCollectorModule));
        mPresenter = new MockAnnualIncomePresenter(mActivity, new MockUserDataCollectorModule(mActivity));
        mView = new MockAnnualIncomeView(mActivity);
        ShiftPlatform.setApiWrapper(new MockApiWrapper());
        ShiftPlatform.setResponseHandler(new MockResponseHandler());
        mPresenter.attachView(mView);

        ConfigResponseVo configResponseVo = new ConfigResponseVo();
        configResponseVo.salaryFrequencyOpts = new SalaryFrequenciesListResponseVo();
        configResponseVo.salaryFrequencyOpts.data = new SalaryFrequencyVo[1];
        SalaryFrequencyVo salaryFrequencyVo = new SalaryFrequencyVo();
        salaryFrequencyVo.salary_frequency_id = EXPECTED_SALARY_FREQUENCY;
        configResponseVo.salaryFrequencyOpts.data[0] = salaryFrequencyVo;

        configResponseVo.incomeTypeOpts = new IncomeTypeListResponseVo();
        configResponseVo.incomeTypeOpts.data = new IncomeTypeVo[1];
        IncomeTypeVo incomeTypeVo = new IncomeTypeVo();
        incomeTypeVo.income_type_id = EXPECTED_INCOME_TYPE;
        configResponseVo.incomeTypeOpts.data[0] = incomeTypeVo;

        ContextConfigResponseVo contextConfigResponseVo = new ContextConfigResponseVo();
        contextConfigResponseVo.projectConfiguration = configResponseVo;
        UIStorage.getInstance().setConfig(contextConfigResponseVo);
    }

    @Test
    public void incomeSourceIsStoredInModel() {
        mView.setIncomeType(EXPECTED_INCOME_TYPE);
        mView.setSalaryFrequency(EXPECTED_SALARY_FREQUENCY);
        mPresenter.nextClickHandler();

        DataPointList userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        IncomeSource incomeSource = (IncomeSource) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.IncomeSource, null);
        Assert.assertThat("Income source should not be empty.", incomeSource, not(nullValue()));
        Assert.assertThat("Incorrect income type.", incomeSource.incomeType.getKey(), equalTo(EXPECTED_INCOME_TYPE));
        Assert.assertThat("Incorrect salary frequency.", incomeSource.salaryFrequency.getKey(), equalTo(EXPECTED_SALARY_FREQUENCY));
    }

    @Test
    public void incorrectIncomeSourceIsNotStoredInModel() {
        mView.setIncomeType(99);
        mPresenter.nextClickHandler();

        DataPointList userData = UserStorage.getInstance().getUserData();
        Assert.assertThat("User data should not be empty.", userData, not(nullValue()));
        IncomeSource incomeSource = (IncomeSource) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.IncomeSource, null);
        Assert.assertThat("Income Source should be null.", incomeSource, is(nullValue()));
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        mPresenter.detachView();
        UserStorage.getInstance().setUserData(null);
    }

}
