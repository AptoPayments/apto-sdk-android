package me.ledge.link.api.vos.datapoints;

/**
 * Created by pauteruel on 12/03/2018.
 */

public class Custodian {

    public String custodianName;
    public String custodianLogo;

    public Custodian() {

        this(null, null);
    }

    public Custodian(String custodianName, String custodianLogo) {

        this.custodianName = custodianName;
        this.custodianLogo = custodianLogo;

    }

    public String getName() {
        return this.custodianName;
    }

    public String getLogo() {
        return this.custodianLogo;
    }


}