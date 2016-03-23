package me.ledge.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

import java.util.ArrayList;

/**
 * Base {@link Model} implementation for a loan application.
 * @author Wijnand
 */
public abstract class AbstractLoanApplicationModel implements ActivityModel, IntermediateLoanApplicationModel, Model {

    protected final LoanApplicationDetailsResponseVo mLoanApplication;

    /**
     * Creates a new {@link AbstractLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public AbstractLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        mLoanApplication = loanApplication;
    }

    /**
     * @return Lender name.
     */
    protected String getLenderName() {
        String name = "";

        if (mLoanApplication != null && mLoanApplication.offer != null && mLoanApplication.offer.lender != null) {
            name = mLoanApplication.offer.lender.lender_name;
        }

        return name;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_application_label;
    }

    /** {@inheritDoc} */
    @Override
    public int getExplanationTextResource() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public String getExplanationText(Resources resources) {
        String text = "";

        if (mLoanApplication != null && !TextUtils.isEmpty(mLoanApplication.status_message)) {
            text = mLoanApplication.status_message;
        }

        return text;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        ArrayList<Class<? extends MvpActivity>> processOrder = LedgeLinkUi.getProcessOrder();
        return processOrder.get(processOrder.size() - 1);
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean showOffersButton() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(false, -1, -1);
    }
}
