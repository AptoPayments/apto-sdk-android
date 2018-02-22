package me.ledge.link.api.vos.requests.financialaccounts;

/**
 * Custodian data
 * @author Adrian
 */
public class CustodianVo {

    public String type;

    public String oauth;

    public CustodianVo(String type, String oauth) {
        this.type = type;
        this.oauth = oauth;
    }
}
