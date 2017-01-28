package me.ledge.link.sdk.ui.models.financialaccountselector;

import android.content.res.Resources;

import me.ledge.link.api.vos.BankAccount;
import me.ledge.link.api.vos.Card;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.FinancialAccountVo;
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
            return "no account found!";
        }
        switch (mFinancialAccount.mAccountType) {
            case Bank:
                BankAccount bankAccount = (BankAccount) mFinancialAccount;
                return resources.getString(R.string.enable_auto_pay_bank, bankAccount.bankName);
            case Card:
                Card card = (Card) mFinancialAccount;
                if (card.cardType == Card.CardType.MARQETA) {
                    DataPointList userData = UserStorage.getInstance().getUserData();
                    DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) userData.
                            getUniqueDataPoint(DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());
                    return resources.getString(R.string.enable_auto_pay_virtual_card, PhoneHelperUtil.formatPhone(phoneNumber.phoneNumber));
                }
                return resources.getString(R.string.enable_auto_pay_card, card.lastFourDigits);
        }
        return "";
    }

    public void setFinancialAccount(FinancialAccountVo financialAccount) {
        this.mFinancialAccount = financialAccount;
    }
}
