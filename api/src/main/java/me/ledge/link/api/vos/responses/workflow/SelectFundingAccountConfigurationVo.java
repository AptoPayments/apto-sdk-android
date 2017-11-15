package me.ledge.link.api.vos.responses.workflow;

/**
 * Created by adrian on 18/10/2017.
 */

import com.google.gson.annotations.SerializedName;

public class SelectFundingAccountConfigurationVo extends ActionConfigurationVo {

    @SerializedName("ach_enabled")
    public boolean isACHEnabled;

    @SerializedName("card_enabled")
    public boolean isCardEnabled;

    @SerializedName("virtual_card_enabled")
    public boolean isVirtualCardEnabled;

    public SelectFundingAccountConfigurationVo(boolean isACHEnabled, boolean isCardEnabled, boolean isVirtualCardEnabled) {
        this.isACHEnabled = isACHEnabled;
        this.isCardEnabled = isCardEnabled;
        this.isVirtualCardEnabled = isVirtualCardEnabled;
    }
}

