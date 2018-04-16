package com.shift.link.sdk.ui.models.financialaccountselector;

import com.shift.link.sdk.ui.storages.UserStorage;

import com.shift.link.sdk.api.vos.requests.financialaccounts.CustodianVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.OAuthCredentialVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.storages.UserStorage;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a virtual credit card.
 * @author Adrian
 */
public class AddVirtualCardModel implements AddFinancialAccountModel {

    /**
     * Creates a new {@link AddVirtualCardModel} instance.
     */
    public AddVirtualCardModel() {

    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_virtual_card;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return R.string.add_financial_account_virtual_card;
    }

    @Override
    public int getDescription() {
        return R.string.add_financial_account_virtual_card_description;
    }

    public IssueVirtualCardRequestVo getRequest() {
        IssueVirtualCardRequestVo request = new IssueVirtualCardRequestVo();
        request.cardIssuer = "SHIFT";

        // TODO: hardcoding to coinbase for now
        OAuthCredentialVo coinbaseCredentials = new OAuthCredentialVo(UserStorage.getInstance().getCoinbaseAccessToken(), UserStorage.getInstance().getCoinbaseRefreshToken());
        request.custodian = new CustodianVo("coinbase", coinbaseCredentials);

        return request;
    }
}
