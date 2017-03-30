package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adrian on 18/01/2017.
 */

public class Card extends FinancialAccountVo {
    public enum CardType {
        MARQETA,
        VISA,
        MASTERCARD,
        AMEX
    }

    public CardType cardType;
    public String lastFourDigits;
    public String PANToken;
    public String CVVToken;
    public String expirationDate;

    public Card() {
        super(null, FinancialAccountType.Card, false);
        cardType = null;
        lastFourDigits = null;
        PANToken = null;
        CVVToken = null;
        expirationDate = null;
    }

    public Card(String accountId, CardType type, String PANToken, String CVVToken, String lastFourDigits,
                String expirationDate, boolean verified) {
        super(accountId, FinancialAccountType.Card, verified);
        this.cardType = type;
        this.lastFourDigits = lastFourDigits;
        this.PANToken = PANToken;
        this.CVVToken = CVVToken;
        this.expirationDate = expirationDate;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("type", "card");
        gsonObject.addProperty("card_brand", cardType.name());
        gsonObject.addProperty("pan_token", PANToken);
        gsonObject.addProperty("cvv_token", CVVToken);
        gsonObject.addProperty("last_four", lastFourDigits);
        gsonObject.addProperty("expiration", getAPIFormatExpirationDate(expirationDate));
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

        Card card = (Card) o;

        if (cardType != card.cardType) return false;
        if (lastFourDigits != null ? !lastFourDigits.equals(card.lastFourDigits) : card.lastFourDigits != null)
            return false;
        if (PANToken != null ? !PANToken.equals(card.PANToken) : card.PANToken != null)
            return false;
        if (CVVToken != null ? !CVVToken.equals(card.CVVToken) : card.CVVToken != null)
            return false;
        return expirationDate != null ? expirationDate.equals(card.expirationDate) : card.expirationDate == null;

    }

    @Override
    public int hashCode() {
        int result = cardType != null ? cardType.hashCode() : 0;
        result = 31 * result + (lastFourDigits != null ? lastFourDigits.hashCode() : 0);
        result = 31 * result + (PANToken != null ? PANToken.hashCode() : 0);
        result = 31 * result + (CVVToken != null ? CVVToken.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
