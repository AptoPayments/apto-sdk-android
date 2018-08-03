package com.shiftpayments.link.sdk.api.vos.responses.workflow;

/*
 * Created by adrian on 18/10/2017.
 */

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.utils.workflow.WorkflowConfigType;

public class SelectFundingAccountConfigurationVo extends ActionConfigurationVo {

    @SerializedName("ach_enabled")
    public boolean isACHEnabled;

    @SerializedName("card_enabled")
    public boolean isCardEnabled;

    @SerializedName("virtual_card_enabled")
    public boolean isVirtualCardEnabled;

    public SelectFundingAccountConfigurationVo(boolean isACHEnabled, boolean isCardEnabled, boolean isVirtualCardEnabled) {
        super(WorkflowConfigType.SELECT_FUNDING_ACCOUNT_CONFIG);
        this.isACHEnabled = isACHEnabled;
        this.isCardEnabled = isCardEnabled;
        this.isVirtualCardEnabled = isVirtualCardEnabled;
    }
}

