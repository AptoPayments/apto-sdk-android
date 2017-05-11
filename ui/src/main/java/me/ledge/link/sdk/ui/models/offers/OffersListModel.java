package me.ledge.link.sdk.ui.models.offers;

import android.app.Activity;
import android.content.res.Resources;

import java.util.Currency;
import java.util.Locale;

import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.link.TermsActivity;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.vos.LoanDataVo;

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

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return TermsActivity.class;
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
