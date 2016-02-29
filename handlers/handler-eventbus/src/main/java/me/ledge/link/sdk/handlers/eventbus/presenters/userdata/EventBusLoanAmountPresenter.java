package me.ledge.link.sdk.handlers.eventbus.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.LoanAmountPresenter;
import me.ledge.link.sdk.ui.views.userdata.LoanAmountView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A {@link LoanAmountPresenter} that uses the {@link EventBus} to receive API responses.
 * @author wijnand
 */
public class EventBusLoanAmountPresenter extends LoanAmountPresenter {

    private EventBus mBus;

    /**
     * Creates a new {@link EventBusLoanAmountPresenter} instance.
     * @param activity Activity.
     */
    public EventBusLoanAmountPresenter(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected void init() {
        super.init();
        mBus = EventBus.getDefault();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAmountView view) {
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
     * Called when the list of loan purposes has been received from the API.
     * @param response API response.
     */
    @Subscribe
    public void handlePurposeList(LoanPurposesResponseVo response) {
        setLoanPurposeList(response.loan_purposes);
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
