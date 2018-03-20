package me.ledge.link.sdk.api.vos.responses.workflow;

/**
 * Created by adrian on 18/10/2017.
 */

import com.google.gson.annotations.SerializedName;

public class SelectCustodianConfigurationVo extends ActionConfigurationVo {

    @SerializedName("coinbase_enabled")
    public boolean isCoinbaseEnabled;

    @SerializedName("dwolla_enabled")
    public boolean isDwollaEnabled;

    public SelectCustodianConfigurationVo(boolean isCoinbaseEnabled, boolean isDwollaEnabled) {
        this.isCoinbaseEnabled = isCoinbaseEnabled;
        this.isDwollaEnabled = isDwollaEnabled;
    }
}

