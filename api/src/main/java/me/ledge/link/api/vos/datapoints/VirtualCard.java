package me.ledge.link.api.vos.datapoints;

/**
 * Created by adrian on 18/01/2017.
 */

public class VirtualCard extends Card {
    public VirtualCard(String accountId, CardType type, String PANToken, String CVVToken, String lastFourDigits, String expirationDate, boolean verified) {
        super(accountId, type, PANToken, CVVToken, lastFourDigits, expirationDate, verified);
    }
}
