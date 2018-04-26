package com.shiftpayments.link.sdk.api.vos;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.datapoints.Custodian;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;

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

    public enum FinancialAccountState {
        ACTIVE,
        INACTIVE,
        CANCELLED,
        CREATED,
        UNKNOWN
    }

    @SerializedName("card_network")
    public CardNetwork cardNetwork;
    @SerializedName("last_four")
    public String lastFourDigits;
    @SerializedName("card_brand")
    public String cardBrand;
    @SerializedName("card_issuer")
    public String cardIssuer;
    @SerializedName("pan")
    public String PANToken;
    @SerializedName("cvv")
    public String CVVToken;
    @SerializedName("expiration")
    public String expirationDate;
    @SerializedName("card_state")
    public FinancialAccountState state;
    //TODO: remove custodian
    public Custodian custodian;
    @SerializedName("kyc_status")
    public KycStatus kycStatus;
    @SerializedName("kyc_reason")
    public String[] kycReason;

    public Card() {
        super(null, FinancialAccountType.Card, false);
        cardNetwork = null;
        lastFourDigits = null;
        cardBrand = null;
        cardIssuer = null;
        PANToken = null;
        CVVToken = null;
        expirationDate = null;
        state = null;
        custodian = null;
        kycStatus = null;
        kycReason = null;
    }

    public Card(String accountId, String lastFourDigits, CardNetwork type, String cardBrand, String cardIssuer, String expirationDate,
                String PANToken, String CVVToken, FinancialAccountState state, Custodian custodian, boolean verified) {
        super(accountId, FinancialAccountType.Card, verified);
        this.cardNetwork = type;
        this.lastFourDigits = lastFourDigits;
        this.cardBrand = cardBrand;
        this.cardIssuer = cardIssuer;
        this.PANToken = PANToken;
        this.CVVToken = CVVToken;
        this.expirationDate = expirationDate;
        this.state = state;
        this.custodian = custodian;
        this.kycStatus = null;
        this.kycReason = null;
    }

    public Card(String accountId, String lastFourDigits, CardNetwork type, String cardBrand, String cardIssuer, String expirationDate,
                String PANToken, String CVVToken, FinancialAccountState state, Custodian custodian, KycStatus kycStatus, String[] kycReason, boolean verified) {
        super(accountId, FinancialAccountType.Card, verified);
        this.cardNetwork = type;
        this.lastFourDigits = lastFourDigits;
        this.cardBrand = cardBrand;
        this.cardIssuer = cardIssuer;
        this.PANToken = PANToken;
        this.CVVToken = CVVToken;
        this.expirationDate = expirationDate;
        this.state = state;
        this.custodian = custodian;
        this.kycStatus = kycStatus;
        this.kycReason = kycReason;
    }

    protected Card(FinancialAccountType accountType, String accountId, String lastFourDigits, CardNetwork type, String cardBrand, String cardIssuer, String expirationDate,
                   String PANToken, String CVVToken, FinancialAccountState state, Custodian custodian, KycStatus kycStatus, String[] kycReason, boolean verified) {
        super(accountId, accountType, verified);
        this.cardNetwork = type;
        this.lastFourDigits = lastFourDigits;
        this.cardBrand = cardBrand;
        this.cardIssuer = cardIssuer;
        this.state = state;
        this.PANToken = PANToken;
        this.CVVToken = CVVToken;
        this.expirationDate = expirationDate;
        this.state = state;
        this.custodian = custodian;
        this.kycStatus = kycStatus;
        this.kycReason = kycReason;
    }

    public Card(Card c) {
        super(c.mAccountId, FinancialAccountType.Card, c.isVerified());
        this.cardNetwork = c.cardNetwork;
        this.lastFourDigits = c.lastFourDigits;
        this.cardBrand = c.cardBrand;
        this.cardIssuer = c.cardIssuer;
        this.state = c.state;
        this.PANToken = c.PANToken;
        this.CVVToken = c.CVVToken;
        this.expirationDate = c.expirationDate;
        this.state = c.state;
        this.custodian = c.custodian;
        this.kycStatus = c.kycStatus;
        this.kycReason = c.kycReason;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("type", "card");
        gsonObject.addProperty("card_network", cardNetwork != null ? cardNetwork.name() : "");
        gsonObject.addProperty("card_brand", cardBrand);
        gsonObject.addProperty("card_issuer", cardIssuer);
        gsonObject.addProperty("state", state != null ? state.name() : "");
        gsonObject.addProperty("pan", PANToken);
        gsonObject.addProperty("cvv", CVVToken);
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
        if(!super.equals(o)) return false;
        Card card = (Card) o;

        if (cardNetwork != card.cardNetwork) return false;
        if (lastFourDigits != null ? !lastFourDigits.equals(card.lastFourDigits) : card.lastFourDigits != null)
            return false;
        if (PANToken != null ? !PANToken.equals(card.PANToken) : card.PANToken != null)
            return false;
        if (CVVToken != null ? !CVVToken.equals(card.CVVToken) : card.CVVToken != null)
            return false;
        if (cardNetwork != null ? !cardNetwork.equals(card.cardNetwork) : card.cardNetwork != null)
            return false;
        if (cardBrand != null ? !cardBrand.equals(card.cardBrand) : card.cardBrand != null)
            return false;
        if (cardIssuer != null ? !cardIssuer.equals(card.cardIssuer) : card.cardIssuer != null)
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
        result = 31 * result + (cardNetwork != null ? cardNetwork.hashCode() : 0);
        result = 31 * result + (cardBrand != null ? cardBrand.hashCode() : 0);
        result = 31 * result + (cardIssuer != null ? cardIssuer.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
