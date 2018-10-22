package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.views.card.CardWelcomeView;

public class CardWelcomePresenter extends BasePresenter<ManageCardModel, CardWelcomeView>
        implements Presenter<ManageCardModel, CardWelcomeView>, CardWelcomeView.ViewListener {

    private CardWelcomeDelegate mDelegate;

    @Override
    public void attachView(CardWelcomeView view) {
        super.attachView(view);
        view.setViewListener(this);
        view.setCardholderName(mModel.getCardHolderName());
        view.setCardNumber(mModel.getCardNumber());
        view.setExpiryDate(mModel.getExpirationDate());
        view.setCvv(mModel.getCVV());
        view.setCardLogo(mModel.getCardNetwork());
        view.showCheckCardOverlay();
    }

    public CardWelcomePresenter(CardWelcomeDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public ManageCardModel createModel() {
        return new ManageCardModel(CardStorage.getInstance().getCard());
    }

    @Override
    public void activateCardClickHandler() {
        mDelegate.onCardWelcomeNextClickHandler();
    }
}
