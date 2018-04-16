package com.shift.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.ui.views.userdata.ArmedForcesView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.ArmedForcesModel;
import com.shift.link.sdk.ui.views.userdata.ArmedForcesView;

/**
 * Concrete {@link Presenter} for the payday loan screen.
 * @author Adrian
 */
public class ArmedForcesPresenter
        extends UserDataPresenter<ArmedForcesModel, ArmedForcesView>
        implements ArmedForcesView.ViewListener {

    private ArmedForcesDelegate mDelegate;

    /**
     * Creates a new {@link ArmedForcesPresenter} instance.
     * @param activity Activity.
     */
    public ArmedForcesPresenter(AppCompatActivity activity, ArmedForcesDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
    }

    /** {@inheritDoc} */
    @Override
    public ArmedForcesModel createModel() {
        return new ArmedForcesModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ArmedForcesView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setSelection(getArmedForcesId());
    }

    @Override
    public void onBack() {
        mDelegate.armedForcesOnBackPressed();
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
        mModel.setArmedForces(getArmedForces());
        mView.showError(!mModel.hasAllData());

        if (mModel.hasAllData()) {
            saveData();
            mDelegate.armedForcesStored();
        }
    }

    /**
     * @return Whether the user is a member of the armed forces based on the checked Radio Button.
     */
    private Boolean getArmedForces() {
        if(mView.getSelectionId() == -1) {
            return null;
        }
        else {
            return mView.getSelectionId() == R.id.rb_yes;
        }
    }

    /**
     * @return Radio Button ID based on the boolean value.
     */
    private int getArmedForcesId() {
        Boolean isMemberOfArmedForces = mModel.isMemberOfArmedForces();
        if (isMemberOfArmedForces == null) {
            return -1;
        }

        return (mModel.isMemberOfArmedForces()) ? R.id.rb_yes : R.id.rb_no;
    }
}
