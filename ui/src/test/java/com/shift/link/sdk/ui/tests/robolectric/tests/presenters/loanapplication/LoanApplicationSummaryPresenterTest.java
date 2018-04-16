package com.shift.link.sdk.ui.tests.robolectric.tests.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import com.shift.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryPresenter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link LoanApplicationSummaryPresenter} class.
 * @author Adrian
 */
@RunWith(RobolectricGradleTestRunner.class)
public class LoanApplicationSummaryPresenterTest {

    private LoanApplicationSummaryPresenter mPresenter;
    private AppCompatActivity mActivity;

    /**
     * Creates a new {@link LoanApplicationSummaryPresenter}.
     */
    private void createPresenter() {
        mPresenter = new LoanApplicationSummaryPresenter(mActivity, LoanApplicationModule.getInstance(mActivity));
    }

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
        createPresenter();
    }

    /**
     * Verifies that the {@link LoanApplicationSummaryModel} has been created AND is of the correct type.
     */
    @Test
    public void loanApplicationSummaryModelIsCreated() {
        Assert.assertThat("Model should be created.", mPresenter.createModel(), not(nullValue()));
        Assert.assertThat("Incorrect type.", mPresenter.createModel(), instanceOf(LoanApplicationSummaryModel.class));
    }
}
