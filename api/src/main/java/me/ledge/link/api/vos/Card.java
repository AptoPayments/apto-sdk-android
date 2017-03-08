package me.ledge.link.api.vos;

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
}
