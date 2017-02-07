package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.LinkStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.vos.LoanDataVo;

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
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) userData.getUniqueDataPoint(DataPointVo.DataPointType.PhoneNumber, null);
        request.phoneNumber = phoneNumber.getPhone().toString();

        LoanDataVo loanData = LinkStorage.getInstance().getLoanData();
        request.amount = loanData.loanAmount;

        return request;
    }
}
