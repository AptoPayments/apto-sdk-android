package me.ledge.link.sdk.handlers.eventbus.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import com.squareup.otto.Subscribe;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationsListPresenter;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationsListView;

/**
 * A {@link LoanApplicationsListPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Wijnand
 */
public class OttoLoanApplicationsListPresenter extends LoanApplicationsListPresenter {

    /**
     * Creates a new {@link OttoLoanApplicationsListPresenter} instance.
     * @param activity Activity.
     */
    public OttoLoanApplicationsListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanApplicationsListView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
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
