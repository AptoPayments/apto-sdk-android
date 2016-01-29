package us.ledge.line.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import us.ledge.line.sdk.sdk.models.userdata.IncomeModel;
import us.ledge.line.sdk.sdk.presenters.Presenter;
import us.ledge.line.sdk.sdk.views.userdata.IncomeView;
import us.ledge.line.sdk.sdk.views.userdata.NextButtonListener;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class IncomePresenter
        extends UserDataPresenter<IncomeModel, IncomeView>
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
    public void attachView(IncomeView view) {
        super.attachView(view);
        mView.setListener(this);
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
        // TODO
        super.nextClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    public IncomeModel createModel() {
        return new IncomeModel();
    }
}
