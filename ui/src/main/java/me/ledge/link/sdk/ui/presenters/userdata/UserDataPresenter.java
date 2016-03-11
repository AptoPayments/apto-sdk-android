package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;
import me.ledge.link.sdk.ui.vos.UserDataVo;

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
        populateModelFromStorage();
    }

    /**
     * Populates the {@link UserDataModel} with the data stored globally.
     */
    protected void populateModelFromStorage() {
        UserDataVo data = UserStorage.getInstance().getUserData();

        if (data == null) {
            mModel.setBaseData(new UserDataVo());
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
    public void nextClickHandler() {
        if (mModel.hasAllData()) {
            saveData();
            startNextActivity();
        }
    }
}
