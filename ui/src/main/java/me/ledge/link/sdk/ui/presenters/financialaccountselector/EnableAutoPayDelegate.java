package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import me.ledge.link.api.vos.FinancialAccountVo;

/**
 * Delegation interface to enable auto-pay.
 *
 * @author Adrian
 */
public interface EnableAutoPayDelegate {

    void autoPayEnabled();

    void autoPayOnBackPressed();

    FinancialAccountVo getFinancialAccount();
}
