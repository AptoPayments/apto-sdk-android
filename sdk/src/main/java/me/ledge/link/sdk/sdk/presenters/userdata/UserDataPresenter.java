package me.ledge.link.sdk.sdk.presenters.userdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.ledge.link.sdk.sdk.models.userdata.UserDataModel;
import me.ledge.link.sdk.sdk.presenters.ActivityPresenter;
import me.ledge.link.sdk.sdk.views.ViewWithToolbar;
import me.ledge.link.sdk.sdk.views.userdata.NextButtonListener;
import me.ledge.link.sdk.sdk.vos.UserDataVo;

/**
 * Generic {@link Presenter} to handle user data input screens.
 * @author Wijnand
 */
public abstract class UserDataPresenter<M extends UserDataModel, V extends View & ViewWithToolbar>
        extends ActivityPresenter<M, V>
        implements NextButtonListener {

    /**
     * Creates a new {@link UserDataPresenter} instance.
     * @param activity Activity.
     */
    public UserDataPresenter(AppCompatActivity activity) {
        super(activity);
        populateModelFromParcel();
    }

    /**
     * Populates the {@link UserDataModel} with the data stored in the Activity's start {@link Intent}.
     */
    protected void populateModelFromParcel() {
        if (mActivity == null || mActivity.getIntent() == null) {
            return;
        }

        Intent intent = mActivity.getIntent();
        UserDataVo data = intent.getParcelableExtra(UserDataVo.USER_DATA_KEY);

        if (data == null) {
            mModel.setBaseData(new UserDataVo());
        } else {
            mModel.setBaseData(data);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected Intent getStartIntent(Class activity) {
        Intent intent = super.getStartIntent(activity);
        intent.putExtra(UserDataVo.USER_DATA_KEY, mModel.getBaseData());

        return intent;
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        if (mModel.hasAllData()) {
            startNextActivity();
        }
    }
}
