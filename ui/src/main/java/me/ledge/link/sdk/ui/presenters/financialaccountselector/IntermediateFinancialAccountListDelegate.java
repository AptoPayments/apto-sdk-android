package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import me.ledge.link.api.vos.DataPointList;

/**
 * Created by adrian on 26/01/2017.
 */
public interface IntermediateFinancialAccountListDelegate {
    void onIntermediateFinancialAccountListBackPressed();
    void noFinancialAccountsReceived();
    void financialAccountsReceived(DataPointList userData);
}