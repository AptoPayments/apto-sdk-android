package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;
import me.ledge.link.sdk.ui.views.userdata.UserDataView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Generic {@link Presenter} to handle user data input screens.
 * @author Wijnand
 */
public abstract class UserDataPresenter<M extends UserDataModel, V extends UserDataView & ViewWithToolbar>
        extends ActivityPresenter<M, V>
        implements StepperListener, NextButtonListener {

    protected static final int TOTAL_STEPS = 8;

    /**
     * Creates a new {@link UserDataPresenter} instance.
     * @param activity Activity.
     */
    public UserDataPresenter(AppCompatActivity activity) {
        super(activity);
        populateModelFromStorage();
    }

    /**
     * @return Stepper configuration.
     */
    protected abstract StepperConfiguration getStepperConfig();

    /**
     * Populates the {@link UserDataModel} with the data stored globally.
     */
    protected void populateModelFromStorage() {
        DataPointList data = UserStorage.getInstance().getUserData();
        if (data == null) {
            mModel.setBaseData(new DataPointList());
        } else {
            mModel.setBaseData(data);
        }
    }

    /**
     * Saves the updated data from the Model in the global storage.
     */
    protected void saveData() {
        UserStorage.getInstance().setUserData(mModel.getBaseData());
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        initToolbar();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(V view) {
        super.attachView(view);
        mView.setStepperConfiguration(getStepperConfig());
    }

    /** {@inheritDoc} */
    @Override
    public void stepperNextClickHandler() {
        nextClickHandler();
    }

    /**
     * Deals with an API error.
     * @param error API error.
     */
    public void setApiError(ApiErrorVo error) {
        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }
}
