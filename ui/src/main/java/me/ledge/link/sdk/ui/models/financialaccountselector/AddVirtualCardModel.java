package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;

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
        DataPointList userData = UserStorage.getInstance().getUserData();
        PhoneNumberVo phoneNumber = (PhoneNumberVo) userData.getUniqueDataPoint(DataPointVo.DataPointType.Phone, null);
        request.phoneNumber = phoneNumber.getPhone().toString();

        request.amount = (int) LoanStorage.getInstance().getCurrentLoanApplication().offer.loan_amount;
        request.cardIssuer = "MARQETA";

        return request;
    }
}
