package us.ledge.line.sdk.sdk.activities;

import android.view.View;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.UserDetailsModel;
import us.ledge.line.sdk.sdk.presenters.UserDetailsPresenter;
import us.ledge.line.sdk.sdk.views.UserDetailsView;

/**
 * Wires up the MVP pattern for the user details screen.
 * @author Wijnand
 */
public class UserDetailsActivity extends MvpActivity<UserDetailsModel, UserDetailsView, UserDetailsPresenter> {

    /** {@inheritDoc} */
    @Override
    protected UserDetailsView createView() {
        return (UserDetailsView) View.inflate(this, R.layout.act_user_details, null);
    }

    /** {@inheritDoc} */
    @Override
    protected UserDetailsPresenter createPresenter() {
        return new UserDetailsPresenter(this);
    }
}
