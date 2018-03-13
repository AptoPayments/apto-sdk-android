package me.ledge.link.api.vos.datapoints;

/**
 * Created by adrian on 18/01/2017.
 */

public class VirtualCard extends Card {
    public VirtualCard() {}

    public VirtualCard(String accountId, String lastFourDigits, CardNetwork type, String CardBrand, String CardIssuer, String expirationDate, String PANToken, String CVVToken, FinancialAccountState state, String balance, Custodian custodian, boolean verified) {
        super(FinancialAccountType.Card, accountId, lastFourDigits, type, CardBrand, CardIssuer, expirationDate, PANToken, CVVToken, state, balance, custodian, verified);
    }
}
