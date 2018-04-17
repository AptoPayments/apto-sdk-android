package com.shift.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.ui.models.link.LoanDataModel;
import com.shift.link.sdk.ui.presenters.ActivityPresenter;
import com.shift.link.sdk.ui.storages.LinkStorage;
import com.shift.link.sdk.ui.views.ViewWithToolbar;
import com.shift.link.sdk.ui.views.userdata.NextButtonListener;
import com.shift.link.sdk.ui.views.userdata.UserDataView;
import com.shift.link.sdk.ui.vos.LoanDataVo;
import com.shift.link.sdk.ui.widgets.steppers.StepperConfiguration;
import com.shift.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Generic presenter to handle loan data input screens.
 * @author Wijnand
 */
public abstract class LoanDataPresenter<M extends LoanDataModel, V extends UserDataView & ViewWithToolbar>
        extends ActivityPresenter<M, V>
        implements StepperListener, NextButtonListener {

    protected static final int TOTAL_STEPS = 8;

    /**
     * Creates a new {@link LoanDataPresenter} instance.
     * @param activity Activity.
     */
    public LoanDataPresenter(AppCompatActivity activity) {
        super(activity);
        populateModelFromStorage();
    }

    /**
     * @return Stepper configuration.
     */
    protected abstract StepperConfiguration getStepperConfig();

    /**
     * Populates the {@link LoanDataModel} with the data stored globally.
     */
    protected void populateModelFromStorage() {
        LoanDataVo data = LinkStorage.getInstance().getLoanData();

        if (data == null) {
            mModel.setBaseData(new LoanDataVo());
        } else {
            mModel.setBaseData(data);
        }
    }

    /**
     * Saves the updated data from the Model in the global storage.
     */
    protected void saveData() {
        LinkStorage.getInstance().setLoanData(mModel.getBaseData());
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

    /** {@inheritDoc} */
    @Override
    public void stepperBackClickHandler() {
        this.onBack();
    }
}
