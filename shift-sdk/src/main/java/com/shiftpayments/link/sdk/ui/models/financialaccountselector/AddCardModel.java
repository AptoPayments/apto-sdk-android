package com.shiftpayments.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a credit card.
 * @author Adrian
 */
public class AddCardModel implements AddFinancialAccountModel, ActivityModel {

    private String mCardHolderName;
    private Card mCard;

    /**
     * Creates a new {@link AddCardModel} instance.
     */
    public AddCardModel() {
        mCardHolderName = getUserName();
        mCard = new Card();
        mCard.state = Card.FinancialAccountState.ACTIVE;
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_card;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return getTitle();
    }

    @Override
    public int getDescription() {
        return R.string.add_financial_account_card_description;
    }

    @Override
    public int getActivityTitleResource() {
        return getTitle();
    }

    @Override
    public Class getPreviousActivity(Activity current) {
        return null;
    }

    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }

    private int getTitle() {
        return R.string.add_card_title;
    }

    public Card getCard() {
        return mCard;
    }

    private String getUserName() {
        DataPointList userData = UserStorage.getInstance().getUserData();
        PersonalName userName = (PersonalName) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.PersonalName, new PersonalName());

        return userName.firstName + " " + userName.lastName;
    }

    public String getCardHolderName() {
        return mCardHolderName;
    }

    public void setCardNetwork(String cardNetwork) {
        mCard.cardNetwork = Card.CardNetwork.valueOf(cardNetwork.toUpperCase());
    }

    public void setLastFourDigits(String lastFourDigits) {
        mCard.lastFourDigits = lastFourDigits;
    }

    public void setExpirationDate(String expirationDate) {
        mCard.expirationDate = expirationDate;
    }

    public void setPANToken(String PANToken) {
        mCard.PANToken = PANToken;
    }

    public void setCVVToken(String CVVToken) {
        mCard.CVVToken = CVVToken;
    }
}
