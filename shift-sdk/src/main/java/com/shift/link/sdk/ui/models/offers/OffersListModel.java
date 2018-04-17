package com.shift.link.sdk.ui.models.offers;

import android.content.res.Resources;

import com.shift.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.AbstractActivityModel;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.models.loanapplication.BigButtonModel;
import com.shift.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import com.shift.link.sdk.ui.vos.LoanDataVo;

import java.util.Currency;
import java.util.Locale;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class OffersListModel extends AbstractActivityModel
        implements ActivityModel, IntermediateLoanApplicationModel, Model {

    private LoanDataVo mBaseData;

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.offers_list_label;
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_exclamation;
    }

    /** {@inheritDoc} */
    @Override
    public int getExplanationTextResource() {
        return R.string.loan_application_explanation_error;
    }

    /** {@inheritDoc} */
    @Override
    public String getExplanationText(Resources resources) {
        return resources.getString(getExplanationTextResource());
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(
                true,
                R.string.loan_application_button_retry,
                BigButtonModel.Action.RELOAD_LOAN_OFFERS
        );
    }

    /** {@inheritDoc} */
    @Override
    public boolean showOffersButton() {
        return false;
    }

    /**
     * @return Basic user data.
     */
    public LoanDataVo getBaseData() {
        return mBaseData;
    }

    /**
     * Stores new basic user data.
     * @param base User data.
     */
    public void setBaseData(LoanDataVo base) {
        this.mBaseData = base;
    }

    /**
     * @return Populated initial offers request data object.
     */
    public InitialOffersRequestVo getInitialOffersRequest() {
        InitialOffersRequestVo request = new InitialOffersRequestVo();
        if(mBaseData != null) {
            request.loan_amount = mBaseData.loanAmount;
            if(mBaseData.loanPurpose != null) {
                request.loan_purpose_id = mBaseData.loanPurpose.getKey();
            }
        }
        request.currency = Currency.getInstance(Locale.US).getCurrencyCode();

        return request;
    }
}
