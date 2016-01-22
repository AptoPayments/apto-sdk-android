package us.ledge.line.sdk.sdk.presenters;

import android.content.Context;
import us.ledge.line.sdk.sdk.models.UserDetailsModel;
import us.ledge.line.sdk.sdk.views.UserDetailsView;

/**
 * Concrete {@link Presenter} for the user details screen.
 * @author Wijnand
 */
public class UserDetailsPresenter
        extends BasePresenter<UserDetailsView, UserDetailsModel>
        implements Presenter<UserDetailsView, UserDetailsModel>, UserDetailsView.ViewListener {

    private final Context mContext;

    /**
     * Creates a new {@link UserDetailsPresenter} instance.
     */
    public UserDetailsPresenter(Context context) {
        super();

        mContext = context;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mModel = new UserDetailsModel(mContext);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(UserDetailsView view) {
        super.attachView(view);
        mView.setListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Validate input.
        mModel.setBirthday(mView.getBirthday());
        mModel.setSocialSecurityNumber(mView.getSocialSecurityNumber());

        mView.updateBirthdayError(!mModel.hasValidBirthday(), mModel.getBirthdayErrorString());
        mView.updateSocialSecurityError(!mModel.hasValidSsn(), mModel.getSsnErrorString());
    }
}
