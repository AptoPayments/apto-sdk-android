package us.ledge.line.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import us.ledge.line.sdk.sdk.models.userdata.UserDataModel;
import us.ledge.line.sdk.sdk.presenters.ActivityPresenter;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;
import us.ledge.line.sdk.sdk.views.userdata.NextButtonListener;

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
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        if (mModel.hasAllData()) {
            startNextActivity();
        }
    }
}
