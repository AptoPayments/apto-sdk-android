package com.shift.link.sdk.ui.presenters.financialaccountselector;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;

/**
 * Created by adrian on 26/01/2017.
 */
public interface IntermediateFinancialAccountListDelegate {
    void onIntermediateFinancialAccountListBackPressed();
    void noFinancialAccountsReceived();
    void financialAccountsReceived(DataPointList financialAccounts);
}