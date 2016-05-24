package me.ledge.link.sdk.handlers.eventbus.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import com.squareup.otto.Subscribe;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.LoanAmountPresenter;
import me.ledge.link.sdk.ui.views.userdata.LoanAmountView;

/**
 * A {@link LoanAmountPresenter} that uses the {@link EventBus} to receive API responses.
 * @author wijnand
 */
public class OttoLoanAmountPresenter extends LoanAmountPresenter {

    /**
     * Creates a new {@link EventBusLoanAmountPresenter} instance.
     * @param activity Activity.
     */
    public OttoLoanAmountPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAmountView view) {
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
     * Called when the list of loan purposes has been received from the API.
     * @param response API response.
     */
    @Subscribe
    public void handlePurposeList(LoanPurposesResponseVo response) {
        setLoanPurposeList(response.data);
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
