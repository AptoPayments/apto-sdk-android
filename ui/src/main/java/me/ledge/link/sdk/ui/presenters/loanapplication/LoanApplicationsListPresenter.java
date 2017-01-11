package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
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

    private LoanApplicationDetailsResponseVo[] mApplicationsList;

    /**
     * Creates a new {@link LoanApplicationsListPresenter} instance.
     * @param activity Activity.
     */
    public LoanApplicationsListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        mApplicationsList = null;
        super.init();
    }

    /**
     * @param page Current page.
     * @return New stepper configuration.
     */
    private StepperConfiguration createStepperConfig(int page) {
        int total = mModel.getTotalApplications();
        return new StepperConfiguration(total, page, page > 0, page < total - 1);
    }

    /**
     * Updates the data displayed by the View.
     */
    private void updateViewData() {
        LoanApplicationPagerAdapter adapter = new LoanApplicationPagerAdapter(
                mActivity.getSupportFragmentManager(), mActivity, mModel.getApplicationList());

        mView.setPagerAdapter(adapter);
        mView.setStepperConfig(createStepperConfig(0));
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationsListModel createModel() {
        return new LoanApplicationsListModel(mApplicationsList);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanApplicationsListView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
        mView.setListener(this);
        mView.showLoading(true);
        updateViewData();

        LedgeLinkUi.getLoanApplicationsList(new ListRequestVo());
    }

    @Override
    public void onBack() {
        mActivity.onBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.clearListener();
        mResponseHandler.unsubscribe(this);
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

    /**
     * Show the list of open loan applications.
     * @param applicationsList List of open loan applications.
     */
    public void showLoanApplicationList(LoanApplicationDetailsResponseVo[] applicationsList) {
        mApplicationsList = applicationsList;

        mModel = createModel();
        updateViewData();

        mView.showLoading(false);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();

        mView.showLoading(false);
    }

    /**
     * Called when the loan applications list has been loaded.
     * @param response The API response.
     */
    @Subscribe
    public void handleLoanApplicationsList(LoanApplicationsListResponseVo response) {
        if (response == null || response.data == null) {
            return;
        }

        showLoanApplicationList(response.data);
    }
}
