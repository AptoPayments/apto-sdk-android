package me.ledge.link.sdk.ui.models.financialaccountselector;

import android.content.res.Resources;

import me.ledge.link.api.vos.datapoints.BankAccount;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.PhoneHelperUtil;

/**
 * Concrete {@link Model} for the auto-pay screen.
 * @author Adrian
 */
public class EnableAutoPayModel extends AbstractActivityModel
        implements Model {

    private FinancialAccountVo mFinancialAccount;

    public EnableAutoPayModel() {
        mFinancialAccount = null;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.enable_auto_pay_title;
    }

    public String getFinancialAccountInfo(Resources resources) {
        if (mFinancialAccount == null) {
            return "";
        }
        switch (mFinancialAccount.mAccountType) {
            case Bank:
                BankAccount bankAccount = (BankAccount) mFinancialAccount;
                return resources.getString(R.string.enable_auto_pay_bank, bankAccount.bankName);
            case Card:
                Card card = (Card) mFinancialAccount;
                if (card.cardType == Card.CardType.MARQETA) {
                    DataPointList userData = UserStorage.getInstance().getUserData();
                    PhoneNumberVo phoneNumber = (PhoneNumberVo) userData.
                            getUniqueDataPoint(DataPointVo.DataPointType.PhoneNumber, new PhoneNumberVo());
                    return resources.getString(R.string.enable_auto_pay_virtual_card, PhoneHelperUtil.formatPhone(phoneNumber.phoneNumber));
                }
                return resources.getString(R.string.enable_auto_pay_card, card.lastFourDigits);
            default:
                return "";
        }
    }

    public void setFinancialAccount(FinancialAccountVo financialAccount) {
        this.mFinancialAccount = financialAccount;
    }
}
