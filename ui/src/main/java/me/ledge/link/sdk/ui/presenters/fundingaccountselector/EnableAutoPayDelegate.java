package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import me.ledge.link.api.vos.datapoints.FinancialAccountVo;

/**
 * Delegation interface to enable auto-pay.
 *
 * @author Adrian
 */
public interface EnableAutoPayDelegate {

    void primaryButtonPressed();

    void secondaryButtonPressed();

    void autoPayOnBackPressed();

    FinancialAccountVo getFinancialAccount();
}
