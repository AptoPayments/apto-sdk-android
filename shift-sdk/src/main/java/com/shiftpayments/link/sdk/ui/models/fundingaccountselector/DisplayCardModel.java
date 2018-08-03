package com.shiftpayments.link.sdk.ui.models.fundingaccountselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.AddFinancialAccountModel;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a credit card.
 * @author Adrian
 */
public class DisplayCardModel implements ActivityModel {

    private String mCardHolderName;

    /**
     * Creates a new {@link DisplayCardModel} instance.
     */
    public DisplayCardModel() {
        mCardHolderName = getUserName();
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
        return R.string.display_card_title;
    }

    private String getUserName() {
        DataPointList userData = UserStorage.getInstance().getUserData();
        PersonalName userName = (PersonalName) userData.getUniqueDataPoint(
                DataPointVo.DataPointType.PersonalName, new PersonalName());

        return userName.firstName + " " + userName.lastName;
    }

    public String getCardHolderName() {
        return mCardHolderName;
    }
}
