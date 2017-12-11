package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import me.ledge.link.api.vos.datapoints.FinancialAccountVo;

/**
 * Created by adrian on 05/12/2017.
 */

public interface SelectFinancialAccountCallback {
    void returnSelectedFinancialAccount(FinancialAccountVo selectedFinancialAccount);
}
