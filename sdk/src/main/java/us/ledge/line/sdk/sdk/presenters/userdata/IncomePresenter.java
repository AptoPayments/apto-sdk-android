package us.ledge.line.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.userdata.IncomeModel;
import us.ledge.line.sdk.sdk.presenters.Presenter;
import us.ledge.line.sdk.sdk.views.userdata.IncomeView;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class IncomePresenter
        extends UserDataPresenter<IncomeModel, IncomeView>
        implements IncomeView.ViewListener {

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
        onProgressChanged(null, mView.getAmount(), false);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public IncomeModel createModel() {
        return new IncomeModel();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // TODO
        super.nextClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        mView.updateAmountText(mActivity.getString(R.string.income_format, value));
    }

    /** {@inheritDoc} */
    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }
}
