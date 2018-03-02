package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adrian on 18/01/2017.
 */

public class Card extends FinancialAccountVo {
    public enum CardNetwork {
        VISA,
        MASTERCARD,
        AMEX
    }

    @SerializedName("card_network")
    public CardNetwork cardNetwork;
    @SerializedName("last_four_digits")
    public String lastFourDigits;
    @SerializedName("pan")
    public String PANToken;
    @SerializedName("cvv_number")
    public String CVVToken;
    @SerializedName("expiration")
    public String expirationDate;
    @SerializedName("state")
    public String state;

    public Card() {
        super(null, FinancialAccountType.Card, false);
        cardNetwork = null;
        lastFourDigits = null;
        PANToken = null;
        CVVToken = null;
        expirationDate = null;
        state = null;
    }

    public Card(String accountId, CardNetwork type, String PANToken, String CVVToken, String lastFourDigits,
                String expirationDate, String state, boolean verified) {
        super(accountId, FinancialAccountType.Card, verified);
        this.cardNetwork = type;
        this.lastFourDigits = lastFourDigits;
        this.PANToken = PANToken;
        this.CVVToken = CVVToken;
        this.expirationDate = expirationDate;
        this.state = state;

    }

    protected Card(FinancialAccountType accountType, String accountId, CardNetwork type, String PANToken, String CVVToken, String lastFourDigits,
                   String expirationDate, String state, boolean verified) {
        super(accountId, accountType, verified);
        this.cardNetwork = type;
        this.lastFourDigits = lastFourDigits;
        this.PANToken = PANToken;
        this.CVVToken = CVVToken;
        this.expirationDate = expirationDate;
        this.state = state;

    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("type", "card");
        gsonObject.addProperty("card_network", cardNetwork.name());
        gsonObject.addProperty("pan_token", PANToken);
        gsonObject.addProperty("cvv_token", CVVToken);
        gsonObject.addProperty("last_four", lastFourDigits);
        gsonObject.addProperty("expiration", getAPIFormatExpirationDate(expirationDate));
        gsonObject.addProperty("state", state);
        return gsonObject;
    }

    private String getAPIFormatExpirationDate(String expirationDate) {
        final String CARD_EXPIRATION_FORMAT = "MM/yy";
        final String API_FORMAT = "yyyy-MM";

        String formattedExpirationDate;
        final SimpleDateFormat dateFormat = new SimpleDateFormat(CARD_EXPIRATION_FORMAT);
        try {
            Date date = dateFormat.parse(expirationDate);
            dateFormat.applyPattern(API_FORMAT);
            formattedExpirationDate = dateFormat.format(date);
        } catch (ParseException e) {
            formattedExpirationDate = null;
        }
        return formattedExpirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        Card card = (Card) o;

        if (cardNetwork != card.cardNetwork) return false;
        if (lastFourDigits != null ? !lastFourDigits.equals(card.lastFourDigits) : card.lastFourDigits != null)
            return false;
        if (PANToken != null ? !PANToken.equals(card.PANToken) : card.PANToken != null)
            return false;
        if (CVVToken != null ? !CVVToken.equals(card.CVVToken) : card.CVVToken != null)
            return false;
        if (state != null ? !state.equals(card.state) : card.state != null)
            return false;
        return expirationDate != null ? expirationDate.equals(card.expirationDate) : card.expirationDate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode()+(cardNetwork != null ? cardNetwork.hashCode() : 0);
        result = 31 * result + (lastFourDigits != null ? lastFourDigits.hashCode() : 0);
        result = 31 * result + (PANToken != null ? PANToken.hashCode() : 0);
        result = 31 * result + (CVVToken != null ? CVVToken.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
