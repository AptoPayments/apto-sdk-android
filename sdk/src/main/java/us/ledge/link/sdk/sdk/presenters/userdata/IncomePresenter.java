package us.ledge.link.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.models.userdata.IncomeModel;
import us.ledge.link.sdk.sdk.presenters.Presenter;
import us.ledge.link.sdk.sdk.views.userdata.IncomeView;

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
    public IncomeModel createModel() {
        return new IncomeModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromParcel() {
        mModel.setMinIncome(mActivity.getResources().getInteger(R.integer.min_income))
                .setMaxIncome(mActivity.getResources().getInteger(R.integer.max_income))
                .setIncome(mActivity.getResources().getInteger(R.integer.default_income));

        super.populateModelFromParcel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IncomeView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setMinMax(mModel.getMinIncome(), mModel.getMaxIncome());
        mView.setIncome(mModel.getIncome());
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
        mModel.setIncome(mView.getIncome());
        super.nextClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        mView.updateIncomeText(mActivity.getString(R.string.income_format, value));
    }

    /** {@inheritDoc} */
    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }
}
