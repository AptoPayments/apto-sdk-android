package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.PhoneModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.PhoneView;

/**
 * Concrete {@link Presenter} for the phone screen.
 * @author Adrian
 */
public class PhonePresenter
        extends UserDataPresenter<PhoneModel, PhoneView>
        implements PhoneView.ViewListener {

    private PhoneDelegate mDelegate;

    /**
     * Creates a new {@link PhonePresenter} instance.
     * @param activity Activity.
     */
    public PhonePresenter(AppCompatActivity activity, PhoneDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public PhoneModel createModel() {
        return new PhoneModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PhoneView view) {
        super.attachView(view);

        if (mModel.hasPhone()) {
            mView.setPhone(Long.toString(mModel.getPhone().getNationalNumber()));
        }

        mView.setListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.phoneOnBackPressed();
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
        mModel.setPhone(mView.getPhone());
        mView.updatePhoneError(!mModel.hasPhone(), R.string.phone_error);

        if(mModel.hasAllData()) {
            saveData();
            mDelegate.phoneStored();
        }
    }
}
