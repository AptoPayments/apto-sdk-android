package com.shiftpayments.link.sdk.ui.models.card;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Concrete {@link Model} for managing a card.
 * @author Adrian
 */
public class ManageCardModel implements ActivityModel {

    private Card mCard;
    private AmountVo mBalance;
    private AmountVo mNativeBalance;
    private boolean mIsBalanceValid;

    /**
     * Creates a new {@link ManageCardModel} instance.
     */
    public ManageCardModel(Card card) {
        mCard = card;
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
            if(CardStorage.getInstance().showCardInfo) {
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
            if(CardStorage.getInstance().showCardInfo) {
                return mCard.PANToken;
            }
            else {
                return "************" + mCard.lastFourDigits;
            }
        }
        return "";
    }

    public String getExpirationDate() {
        if(mCard != null) {
            if(CardStorage.getInstance().showCardInfo) {
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

    public String getSpendableAmount() {
        if (mCard.spendableAmount != null) {
            return new AmountVo(mCard.spendableAmount.amount, mCard.spendableAmount.currency).toString();
        }
        return "";
    }

    public String getNativeBalance() {
        if (mNativeBalance != null) {
            return mNativeBalance.toString();
        }
        return "";
    }

    public String getNativeSpendableAmount() {
        if (mCard.nativeSpendableAmount != null) {
            return new AmountVo(mCard.nativeSpendableAmount.amount, mCard.nativeSpendableAmount.currency).toString();
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

    public boolean isNativeBalanceCurrencyDifferentFromLocalBalanceCurrency() {
        return !mBalance.getCurrency().equals(mNativeBalance.getCurrency());
    }

    public boolean isSpendableAmountCurrencyDifferentFromNativeSpendableAmountCurrency() {
        return !mCard.nativeSpendableAmount.currency.equals(mCard.spendableAmount);
    }

    public boolean isCardActivated() {
        return mCard.isCardActivated();
    }

    public boolean isCardCreated() {
        return mCard.isCardCreated();
    }

    public boolean isPhysicalCardActivationRequired() {
        return mCard.physicalCardActivationRequired;
    }

    public boolean cardNumberShown() {
        return CardStorage.getInstance().showCardInfo;
    }

    public boolean hasBalance() {
        return mBalance != null;
    }

    public boolean isBalanceValid() {
        return mIsBalanceValid;
    }

    public void setBalanceState(String state) {
        mIsBalanceValid = state!=null && state.equals("valid");
    }

    public void setCard(Card card) {
        mCard = card;
    }

    public void setBalance(AmountVo balance) {
        mBalance = balance;
    }

    public void setNativeBalance(AmountVo nativeBalance) {
        mNativeBalance = nativeBalance;
    }

    @Override
    public int getActivityTitleResource() {
        return 0;
    }

    @Override
    public Class getPreviousActivity(Activity current) {
        return null;
    }

    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }
}