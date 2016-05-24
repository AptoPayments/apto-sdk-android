package me.ledge.link.sdk.handlers.eventbus.presenters.offers;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;
import me.ledge.link.sdk.ui.views.offers.OffersListView;
import org.greenrobot.eventbus.Subscribe;

/**
 * An {@link OffersListPresenter} that uses the {@link EventBus} to receive API responses.
 * @author wijnand
 */
public class EventBusOffersListPresenter extends OffersListPresenter {

    /**
     * Creates a new {@link EventBusOffersListPresenter} instance.
     * @param activity Activity.
     */
    public EventBusOffersListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(OffersListView view) {
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
     * Called when the initial offers list API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleOffers(InitialOffersResponseVo response) {
        if (response.offers != null) {
            addOffers(response.offers.data, response.offer_request_id, true);
        }
    }

    /**
     * Called when a loan application response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleLoanApplication(LoanApplicationDetailsResponseVo response) {
        showLoanApplicationScreen(response);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        // TODO: Interface method for setApiError?
        setApiError(error);
    }
}
