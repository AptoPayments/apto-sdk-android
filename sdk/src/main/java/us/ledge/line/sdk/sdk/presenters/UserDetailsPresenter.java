package us.ledge.line.sdk.sdk.presenters;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import us.ledge.line.sdk.sdk.fragments.DatePickerFragment;
import us.ledge.line.sdk.sdk.models.UserDetailsModel;
import us.ledge.line.sdk.sdk.views.UserDetailsView;

/**
 * Concrete {@link Presenter} for the user details screen.
 * @author Wijnand
 */
public class UserDetailsPresenter
        extends BasePresenter<UserDetailsView, UserDetailsModel>
        implements Presenter<UserDetailsView, UserDetailsModel>, UserDetailsView.ViewListener,
        DatePickerDialog.OnDateSetListener {

    private final AppCompatActivity mActivity;

    /**
     * Creates a new {@link UserDetailsPresenter} instance.
     */
    public UserDetailsPresenter(AppCompatActivity activity) {
        super();

        mActivity = activity;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mModel = new UserDetailsModel(mActivity);
    }

    /**
     * Sets up the toolbar.
     */
    protected void setupToolbar() {
        mActivity.setSupportActionBar(mView.getToolbar());

        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(UserDetailsView view) {
        super.attachView(view);
        mView.setListener(this);
        setupToolbar();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void birthdayClickHandler() {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(this);
        fragment.show(mActivity.getFragmentManager(), DatePickerFragment.TAG);
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Validate input.
        mModel.setSocialSecurityNumber(mView.getSocialSecurityNumber());

        mView.updateBirthdayError(!mModel.hasValidBirthday(), mModel.getBirthdayErrorString());
        mView.updateSocialSecurityError(!mModel.hasValidSsn(), mModel.getSsnErrorString());
    }

    /** {@inheritDoc} */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mModel.setBirthday(year, monthOfYear, dayOfMonth);
        mView.setBirthday(String.format("%02d/%02d/%02d", monthOfYear + 1, dayOfMonth, year));
    }
}
