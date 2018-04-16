package com.shift.link.sdk.ui.presenters.fundingaccountselector;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.ui.ShiftUi;
import com.shift.link.sdk.ui.presenters.ActivityPresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.storages.LoanStorage;
import com.shift.link.sdk.ui.views.fundingaccountselector.DisplayCardView;

import org.greenrobot.eventbus.Subscribe;

import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.sdk.LedgeLinkSdk;
import com.shift.link.sdk.ui.ShiftUi;
import com.shift.link.sdk.ui.models.fundingaccountselector.DisplayCardModel;
import com.shift.link.sdk.ui.presenters.ActivityPresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.storages.LoanStorage;
import com.shift.link.sdk.ui.views.fundingaccountselector.DisplayCardView;


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
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        ShiftUi.getFinancialAccount(mDelegate.getFinancialAccountId());
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        super.detachView();
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

    /**
     * Called when the get financial account response has been received.
     * @param account API response.
     */
    @Subscribe
    public void handleResponse(FinancialAccountVo account) {
        Card card = (Card) account;
        mActivity.runOnUiThread(() -> {
            mView.setExpiryDate(card.expirationDate);
            mView.setCardNumber(card.PANToken);
            mView.setCardName(mModel.getCardHolderName());
            mView.setCardBalance(String.valueOf(LoanStorage.getInstance().getCurrentLoanApplication().offer.loan_amount));
        });
    }
}
