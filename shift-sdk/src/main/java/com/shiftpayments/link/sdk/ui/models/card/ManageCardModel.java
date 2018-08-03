package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.shiftpayments.link.sdk.api.vos.Card.FinancialAccountState.ACTIVE;
import static com.shiftpayments.link.sdk.api.vos.Card.FinancialAccountState.CREATED;

/**
 * Concrete {@link Model} for managing a card.
 * @author Adrian
 */
public class ManageCardModel implements Model {

    private Card mCard;
    public boolean showCardInfo;
    private AmountVo mBalance;

    /**
     * Creates a new {@link ManageCardModel} instance.
     */
    public ManageCardModel(Card card) {
        mCard = card;
        showCardInfo = false;
    }

    public String getCardHolderName() {
        PersonalName name = (PersonalName) UserStorage.getInstance().getUserData().getUniqueDataPoint(DataPointVo.DataPointType.PersonalName, null);
        if(name != null) {
            return name.toString().toUpperCase();
        }
        return "";
    }

    public String getCVV() {
        if(mCard != null) {
            if(showCardInfo) {
                return mCard.CVVToken;
            }
            else {

                return "***";
            }
        }
        return "";
    }

    public Card.CardNetwork getCardNetwork() {
        if(mCard != null) {
            return mCard.cardNetwork;
        }
        return Card.CardNetwork.UNKNOWN;
    }

    public String getCardNumber() {
        if(mCard != null) {
            if(showCardInfo) {
                StringBuilder formattedCardNumber = new StringBuilder();
                for (int i = 0; i < mCard.PANToken.length(); i++) {
                    if (i % 4 == 0 && i != 0) {
                        formattedCardNumber.append("   ");
                    }

                    formattedCardNumber.append(mCard.PANToken.charAt(i));
                }
                return formattedCardNumber.toString();
            }
            else {
                return "****    ****    ****    " + mCard.lastFourDigits;
            }
        }
        return "";
    }

    public String getExpirationDate() {
        if(mCard != null) {
            if(showCardInfo) {
                return getFormattedExpirationDate();
            }
            else {
                return "**/**";
            }
        }
        return "";
    }

    private String getFormattedExpirationDate() {
        SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-MM", Locale.US);
        SimpleDateFormat expectedFormat = new SimpleDateFormat("MM/yy", Locale.US);
        try {
            Date date = currentFormat.parse(mCard.expirationDate);
            return expectedFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public String getCardBalance() {
        if (mBalance != null) {
            return mBalance.toString();
        }
        return "";
    }

    public String getAccountId() {
        if(mCard != null) {
            return mCard.mAccountId;
        }
        return "";
    }

    public Card.FinancialAccountState getState() {
        if (mCard != null) {
            return mCard.state;
        }
        return null;
    }

    public boolean isCardActivated() {
        Card.FinancialAccountState state = this.getState();
        return ((state != null) && (state == ACTIVE));
    }

    public boolean isCardCreated() {
        Card.FinancialAccountState state = this.getState();
        return ((state != null) && (state == CREATED));
    }

    public void setCard(Card card) {
        mCard = card;
    }

    public void setBalance(AmountVo balance) {
        this.mBalance = balance;
    }
}