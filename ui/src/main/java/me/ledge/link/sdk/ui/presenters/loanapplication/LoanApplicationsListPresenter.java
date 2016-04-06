package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.adapters.loanapplication.LoanApplicationPagerAdapter;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationsListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationsListView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the loan applications list.
 * @author Wijnand
 */
public class LoanApplicationsListPresenter
        extends ActivityPresenter<LoanApplicationsListModel, LoanApplicationsListView>
        implements Presenter<LoanApplicationsListModel, LoanApplicationsListView>,
        LoanApplicationsListView.ViewListener {

    /**
     * Creates a new {@link LoanApplicationsListPresenter} instance.
     * @param activity Activity.
     */
    public LoanApplicationsListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    private StepperConfiguration createStepperConfig(int page) {
        int total = mModel.getTotalApplications();
        return new StepperConfiguration(total, page, page != 0, page != total - 1);
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationsListModel createModel() {
        return new LoanApplicationsListModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanApplicationsListView view) {
        super.attachView(view);
        LoanApplicationPagerAdapter adapter
                = new LoanApplicationPagerAdapter(mActivity.getSupportFragmentManager(), mActivity);

        mView.setListener(this);
        mView.setPagerAdapter(adapter);
        mView.setStepperConfig(createStepperConfig(0));
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.clearListener();
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void onPageScrolled(int i, float v, int i1) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onPageSelected(int page) {
        mView.setStepperConfig(createStepperConfig(page));
    }

    /** {@inheritDoc} */
    @Override
    public void onPageScrollStateChanged(int i) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void stepperBackClickHandler() {
        mView.previousPage();
    }

    /** {@inheritDoc} */
    @Override
    public void stepperNextClickHandler() {
        mView.nextPage();
    }
}
