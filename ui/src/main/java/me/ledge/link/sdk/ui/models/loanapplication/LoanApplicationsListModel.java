package me.ledge.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the list of open loan applications.
 * @author Wijnand
 */
public class LoanApplicationsListModel implements Model, ActivityModel {

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_applications_list_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }

    public int getTotalApplications() {
        return 4; // TODO
    }
}
