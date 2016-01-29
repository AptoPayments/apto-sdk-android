package us.ledge.line.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import us.ledge.line.sdk.sdk.models.userdata.IncomeModel;
import us.ledge.line.sdk.sdk.presenters.ActivityPresenter;
import us.ledge.line.sdk.sdk.presenters.Presenter;
import us.ledge.line.sdk.sdk.views.userdata.IncomeView;
import us.ledge.line.sdk.sdk.views.userdata.NextButtonListener;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class IncomePresenter
        extends ActivityPresenter<IncomeModel, IncomeView>
        implements NextButtonListener {

    /**
     * Creates a new {@link IncomePresenter} instance.
     * @param activity Activity.
     */
    public IncomePresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // TODO
    }

    /** {@inheritDoc} */
    @Override
    public IncomeModel createModel() {
        return new IncomeModel();
    }
}
