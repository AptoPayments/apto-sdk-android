package me.ledge.link.sdk.ui.models.card;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.ledge.link.sdk.api.vos.datapoints.Card;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.datapoints.PersonalName;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.storages.UserStorage;

import static me.ledge.link.sdk.api.vos.datapoints.Card.FinancialAccountState.ACTIVE;

/**
 * Concrete {@link Model} for managing a card.
 * @author Adrian
 */
public class ManageCardModel implements Model {

    private Card mCard;
    public boolean showCardInfo;
    private String mBalance;

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
        return null;
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
        if (mBalance != null && !mBalance.isEmpty()) {
            return "$" + mBalance;
        }
        return "";
    }

    public String getAccountId() {
        if(mCard != null) {
            return mCard.mAccountId;
        }
        return "";
    }

    public String getCustodianName() {
        if (mCard != null) {
            if (mCard.custodian.name != null) {
                return mCard.custodian.name;
            } else {
                return "Card";
            }
        }
        return "";
    }
    public String getCustodianLogo() {
        if (mCard != null) {
            if (mCard.custodian.logo != null) {
                return mCard.custodian.logo;
            } else {
                getCustodianName();
            }
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

    public void setCard(Card card) {
        mCard = card;
    }

    public void setBalance(String balance) {
        this.mBalance = balance;
    }
}