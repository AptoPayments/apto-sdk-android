package me.ledge.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.storages.UserStorage;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a credit card.
 * @author Adrian
 */
public class AddCardModel implements AddFinancialAccountModel, ActivityModel {

    private String mCardHolderName;

    /**
     * Creates a new {@link AddCardModel} instance.
     */
    public AddCardModel() {
        mCardHolderName = getUserName();
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_doc_generic;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return getTitle();
    }

    @Override
    public int getDescription() {
        return R.string.add_financial_account_card_description;
    }

    @Override
    public int getActivityTitleResource() {
        return getTitle();
    }

    @Override
    public Class getPreviousActivity(Activity current) {
        return null;
    }

    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }

    private int getTitle() {
        return R.string.add_card_title;
    }

    private String getUserName() {
        DataPointList userData = UserStorage.getInstance().getUserData();
        DataPointVo.PersonalName userName = (DataPointVo.PersonalName) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.PersonalName, new DataPointVo.PersonalName());

        return userName.firstName + " " + userName.lastName;
    }

    public String getCardHolderName() {
        return mCardHolderName;
    }
}
