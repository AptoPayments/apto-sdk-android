package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.api.vos.FinancialAccountVo;

/**
 * Information about a financial account.
 * @author Adrian
 */
public interface SelectFinancialAccountModel {

    /**
     * @return Icon resource ID.
     */
    int getIconResourceId();

    /**
     * @return Description.
     */
    String getDescription();

    /**
     * @return The financial account selected by the user.
     */
    FinancialAccountVo getFinancialAccount();

}
