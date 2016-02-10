package me.ledge.link.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.models.userdata.IncomeModel;
import me.ledge.link.sdk.sdk.presenters.Presenter;
import me.ledge.link.sdk.sdk.views.userdata.IncomeView;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class IncomePresenter
        extends UserDataPresenter<IncomeModel, IncomeView>
        implements IncomeView.ViewListener {

    private int mIncomeMultiplier;
    private int mMaxIncome;

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
        mIncomeMultiplier = mActivity.getResources().getInteger(R.integer.income_increment);
        mMaxIncome = mActivity.getResources().getInteger(R.integer.max_income);

        mModel.setMinIncome(mActivity.getResources().getInteger(R.integer.min_income))
                .setMaxIncome(mMaxIncome)
                .setIncome(mActivity.getResources().getInteger(R.integer.default_income));

        super.populateModelFromParcel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IncomeView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setMinMax(mModel.getMinIncome() / mIncomeMultiplier, mModel.getMaxIncome() / mIncomeMultiplier);
        mView.setIncome(mModel.getIncome() / mIncomeMultiplier);
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
        mModel.setIncome(mView.getIncome() * mIncomeMultiplier);
        super.nextClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        String formatted = mActivity.getString(R.string.income_format, value);
        if (value * mIncomeMultiplier >= mMaxIncome) {
            formatted = mActivity.getString(R.string.income_format_and_more, value);
        }

        mView.updateIncomeText(formatted);
    }

    /** {@inheritDoc} */
    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }
}
