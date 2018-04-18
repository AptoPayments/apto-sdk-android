package com.shift.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.TimeAtAddressVo;
import com.shift.link.sdk.ui.models.userdata.TimeAtAddressModel;
import com.shift.link.sdk.ui.storages.UIStorage;
import com.shift.link.sdk.ui.views.userdata.TimeAtAddressView;

/**
 * Concrete {@link Presenter} for the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressPresenter
        extends UserDataPresenter<TimeAtAddressModel, TimeAtAddressView>
        implements TimeAtAddressView.ViewListener {

    private TimeAtAddressDelegate mDelegate;

    /**
     * Creates a new {@link TimeAtAddressPresenter} instance.
     * @param activity Activity.
     */
    public TimeAtAddressPresenter(AppCompatActivity activity, TimeAtAddressDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public TimeAtAddressModel createModel() {
        return new TimeAtAddressModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(TimeAtAddressView view) {
        super.attachView(view);
        showTimeAtAddressOptions(UIStorage.getInstance().getContextConfig());
        mView.setListener(this);
    }

    private void showTimeAtAddressOptions(ConfigResponseVo config) {
        TimeAtAddressVo[] typesList = config.timeAtAddressOpts.data;
        if (typesList != null) {
            mView.makeRadioButtons(typesList);
            mView.setColors();
            mView.setScoreRangeId(mModel.getTimeAtAddressRange());
        }
    }

    @Override
    public void onBack() {
        mDelegate.timeAtAddressOnBackPressed();
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
        mModel.setTimeAtAddressRange(mView.getScoreRangeId());
        mView.showError(!mModel.hasAllData());

        if (mModel.hasAllData()) {
            saveData();
            mDelegate.timeAtAddressStored();
        }
    }
}
