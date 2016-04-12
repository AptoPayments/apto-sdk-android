package me.ledge.link.sdk.handlers.eventbus.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationsListPresenter;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationsListView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A {@link LoanApplicationsListPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Wijnand
 */
public class EventBusLoanApplicationsListPresenter extends LoanApplicationsListPresenter {

    private EventBus mBus;

    /**
     * Creates a new {@link EventBusLoanApplicationsListPresenter} instance.
     * @param activity Activity.
     */
    public EventBusLoanApplicationsListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
        mBus = EventBus.getDefault();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanApplicationsListView view) {
        super.attachView(view);
        mBus.register(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mBus.unregister(this);
        super.detachView();
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

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        setApiError(error);
    }
}
