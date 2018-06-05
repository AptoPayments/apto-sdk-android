package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.UserDataModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.views.userdata.UserDataView;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperConfiguration;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Generic {@link Presenter} to handle user data input screens.
 * @author Wijnand
 */
public abstract class UserDataPresenter<M extends UserDataModel, V extends UserDataView & ViewWithToolbar>
        extends ActivityPresenter<M, V>
        implements StepperListener {

    protected static int TOTAL_STEPS;
    protected Observer<Boolean> mUiFieldsObserver;

    /**
     * Creates a new {@link UserDataPresenter} instance.
     * @param activity Activity.
     */
    public UserDataPresenter(AppCompatActivity activity) {
        super(activity);
        populateModelFromStorage();
        createObserver();
    }

    /**
     * @return Stepper configuration.
     */
    protected StepperConfiguration getStepperConfig() {
        int position = 0;
        if(ModuleManager.getInstance().getCurrentModule() instanceof  UserDataCollectorModule) {
            UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();
            position = module.getRequiredActivityPosition(mActivity.getClass());
        }
        return new StepperConfiguration(TOTAL_STEPS, position, true);
    }

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
    public void attachView(V view) {
        super.attachView(view);
        mView.setStepperConfiguration(getStepperConfig());
        mView.setObserver(mUiFieldsObserver);
        if(mView.hasNextButton()) {
            mView.enableNextButton(false);
        }
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
        mView.displayErrorMessage(message);
    }

    private void createObserver() {
        mUiFieldsObserver = new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                mActivity.runOnUiThread(() -> mView.enableNextButton(aBoolean));
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }
}
