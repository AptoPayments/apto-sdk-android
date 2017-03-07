package me.ledge.link.sdk.ui.models.loanapplication;

import java.util.LinkedList;

import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Loan summary {@link Model}.
 * @author Adrian
 */
public class LoanApplicationSummaryModel extends LoanAgreementModel implements ActivityModel, Model {

    private LinkedList<RequiredDataPointVo> mRequiredDataPointsList;

    /**
     * Creates a new {@link LoanApplicationSummaryModel} instance.
     * @param offer The selected loan offer.
     * @param imageLoader Image loader.
     */
    public LoanApplicationSummaryModel(OfferVo offer, GenericImageLoader imageLoader) {
        super(offer, imageLoader);

    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_confirmation_label;
    }

    public void setRequiredData(LinkedList<RequiredDataPointVo> requiredDataPointsList) {
        mRequiredDataPointsList = requiredDataPointsList;
    }

    public LinkedList<RequiredDataPointVo> getRequiredData() {
        return mRequiredDataPointsList;
    }
}
