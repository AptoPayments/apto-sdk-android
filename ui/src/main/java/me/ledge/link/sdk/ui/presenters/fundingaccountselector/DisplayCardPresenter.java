package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.sdk.ui.models.fundingaccountselector.DisplayCardModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.views.fundingaccountselector.DisplayCardView;


/**
 * Concrete {@link Presenter} for the display card screen.
 * @author Adrian
 */
public class DisplayCardPresenter
    extends ActivityPresenter<DisplayCardModel, DisplayCardView>
    implements Presenter<DisplayCardModel, DisplayCardView>, DisplayCardView.ViewListener {

    private DisplayCardDelegate mDelegate;

    /**
     * Creates a new {@link ActivityPresenter} instance.
     *
     * @param activity Activity.
     */
    public DisplayCardPresenter(AppCompatActivity activity, DisplayCardDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(DisplayCardView view) {
        super.attachView(view);
        view.setViewListener(this);
        Card card = mDelegate.getCard();
        view.setExpiryDate(card.expirationDate);
        view.setCardNumber(card.PANToken);
        view.setCardName(mModel.getCardHolderName());
        view.setCardBalance(String.valueOf(LoanStorage.getInstance().getCurrentLoanApplication().offer.loan_amount));
    }

    @Override
    public void onBack() {
        mDelegate.displayCardOnBackPressed();
    }

    @Override
    public DisplayCardModel createModel() {
        return new DisplayCardModel();
    }

    @Override
    public void primaryButtonClickHandler() {
        mDelegate.displayCardPrimaryButtonPressed();
    }

    @Override
    public void secondaryButtonClickHandler() {
        mDelegate.displayCardSecondaryButtonPressed();
    }
}
