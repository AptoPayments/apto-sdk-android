package me.ledge.link.api.vos.datapoints;

/**
 * Created by adrian on 18/01/2017.
 */

public class VirtualCard extends Card {
    public VirtualCard() {}

    public VirtualCard(String accountId, CardNetwork type, String PANToken, String CVVToken, String lastFourDigits, String expirationDate, String state, boolean verified) {
        super(FinancialAccountType.VirtualCard, accountId, type, PANToken, CVVToken, lastFourDigits, expirationDate, state, verified);
    }
}
