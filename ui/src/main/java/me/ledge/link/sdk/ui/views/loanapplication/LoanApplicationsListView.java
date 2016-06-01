package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.widgets.steppers.DotStepperWidget;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the loan applications list.
 * @author Wijnand
 */
public class LoanApplicationsListView extends RelativeLayout implements ViewWithToolbar {

    private Toolbar mToolbar;
    private ViewPager mPager;
    private DotStepperWidget mStepper;
    private RelativeLayout mLoadingIndicator;

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends ViewPager.OnPageChangeListener, StepperListener {}

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public LoanApplicationsListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public LoanApplicationsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all relevant child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mPager = (ViewPager) findViewById(R.id.vp_loan_applications_pager);
        mStepper = (DotStepperWidget) findViewById(R.id.dsw_stepper);
        mLoadingIndicator = (RelativeLayout) findViewById(R.id.rl_loading_overlay);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Registers a new {@link ViewListener}.
     * @param listener New listener.
     */
    public void setListener(ViewListener listener) {
        mPager.addOnPageChangeListener(listener);
        mStepper.setListener(listener);
    }

    /**
     * Clears all registered {@link ViewListener}s.
     */
    public void clearListener() {
        mPager.clearOnPageChangeListeners();
        mStepper.setListener(null);
    }

    /**
     * Stores a new {@link FragmentStatePagerAdapter}.
     * @param adapter New adapter.
     */
    public void setPagerAdapter(FragmentStatePagerAdapter adapter) {
        mPager.setAdapter(adapter);
    }

    /**
     * Stores a new {@link StepperConfiguration}.
     * @param config New configuration.
     */
    public void setStepperConfig(StepperConfiguration config) {
        mStepper.setConfiguration(config);
    }

    /**
     * Shows the previous page.
     */
    public void previousPage() {
        mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
    }

    /**
     * Shows the next page.
     */
    public void nextPage() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
    }

    /**
     * Changes the visibility of the loading indicator.
     * @param show Whether the loading indicator should be shown.
     */
    public void showLoading(boolean show) {
        if (show) {
            mLoadingIndicator.setVisibility(VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(GONE);
        }
    }
}
