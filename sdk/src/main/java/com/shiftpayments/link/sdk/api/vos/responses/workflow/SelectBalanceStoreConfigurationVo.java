package com.shiftpayments.link.sdk.api.vos.responses.workflow;

/*
 * Created by adrian on 18/10/2017.
 */

import com.google.gson.annotations.SerializedName;

import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowConfigType.SELECT_BALANCE_STORE_CONFIG;

public class SelectBalanceStoreConfigurationVo extends ActionConfigurationVo {

    @SerializedName("allowed_balance_types")
    public AllowedBalancesTypesList allowedBalanceTypes;

    public SelectBalanceStoreConfigurationVo(AllowedBalancesTypesList allowedBalanceTypes) {
        super(SELECT_BALANCE_STORE_CONFIG);
        this.allowedBalanceTypes = allowedBalanceTypes;
    }
}

