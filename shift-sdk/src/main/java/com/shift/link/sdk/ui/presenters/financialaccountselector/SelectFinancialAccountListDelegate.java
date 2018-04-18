package com.shift.link.sdk.ui.presenters.financialaccountselector;

import com.shift.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;

/**
 * Delegation interface for adding financial accounts.
 *
 * @author Adrian
 */
public interface SelectFinancialAccountListDelegate {
    void selectFinancialAccountListOnBackPressed();

    void addAccountPressed();

    void accountSelected(SelectFinancialAccountModel model);
}
