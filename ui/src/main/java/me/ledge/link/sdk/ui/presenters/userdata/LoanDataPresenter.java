package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.sdk.ui.models.userdata.LoanDataModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.storages.LinkStorage;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;
import me.ledge.link.sdk.ui.views.userdata.UserDataView;
import me.ledge.link.sdk.ui.vos.LoanDataVo;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Generic presenter to handle loan data input screens.
 * @author Wijnand
 */
public abstract class LoanDataPresenter<M extends LoanDataModel, V extends UserDataView & ViewWithToolbar>
        extends ActivityPresenter<M, V>
        implements StepperListener, NextButtonListener {

    protected static final int TOTAL_STEPS = 9;

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
    public void nextClickHandler() {
        if (mModel.hasAllData()) {
            saveData();
            startNextActivity();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void stepperBackClickHandler() {
        startPreviousActivity();
    }

    /** {@inheritDoc} */
    @Override
    public void stepperNextClickHandler() {
        nextClickHandler();
    }
}
