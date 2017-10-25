package me.ledge.link.sdk.ui.presenters.financialaccountselector;


import android.support.v7.app.AppCompatActivity;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.sdk.ui.ModuleManager;
import me.ledge.link.sdk.ui.models.financialaccountselector.DisplayCardModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.financialaccountselector.DisplayCardView;


/**
 * Concrete {@link Presenter} for the display card screen.
 * @author Adrian
 */
public class DisplayCardPresenter
    extends ActivityPresenter<DisplayCardModel, DisplayCardView>
    implements Presenter<DisplayCardModel, DisplayCardView> {

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
        FinancialAccountSelectorModule currentModule = (FinancialAccountSelectorModule) ModuleManager.getInstance().getCurrentModule();
        Card card = (Card) currentModule.getSelectedFinancialAccount();
        view.setExpiryDate(card.expirationDate);
        view.setCardNumber(card.lastFourDigits);
        view.setCardName(mModel.getCardHolderName());
    }

    @Override
    public void onBack() {
        mDelegate.displayCardOnBackPressed();
    }

    @Override
    public DisplayCardModel createModel() {
        return new DisplayCardModel();
    }
}
