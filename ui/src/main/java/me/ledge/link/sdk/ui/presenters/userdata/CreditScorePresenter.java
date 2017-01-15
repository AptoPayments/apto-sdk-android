package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.utils.CreditScoreRange;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.CreditScoreModel;
import me.ledge.link.sdk.ui.views.userdata.CreditScoreView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

import java.util.HashMap;

/**
 * Concrete {@link Presenter} for the credit score screen.
 * @author wijnand
 */
public class CreditScorePresenter
        extends UserDataPresenter<CreditScoreModel, CreditScoreView>
        implements CreditScoreView.ViewListener {

    private HashMap<Integer, Integer> mIdToRangeMap;
    private HashMap<Integer, Integer> mRangeToIdMap;
    private CreditScoreDelegate mDelegate;

    /**
     * Creates a new {@link CreditScorePresenter} instance.
     * @param activity Activity.
     */
    public CreditScorePresenter(AppCompatActivity activity, CreditScoreDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();

        mIdToRangeMap = new HashMap<>(4);
        mIdToRangeMap.put(R.id.rb_excellent, CreditScoreRange.EXCELLENT);
        mIdToRangeMap.put(R.id.rb_good, CreditScoreRange.GOOD);
        mIdToRangeMap.put(R.id.rb_fair, CreditScoreRange.FAIR);
        mIdToRangeMap.put(R.id.rb_poor, CreditScoreRange.POOR);

        mRangeToIdMap = new HashMap<>(4);
        mRangeToIdMap.put(CreditScoreRange.EXCELLENT, R.id.rb_excellent);
        mRangeToIdMap.put(CreditScoreRange.GOOD, R.id.rb_good);
        mRangeToIdMap.put(CreditScoreRange.FAIR, R.id.rb_fair);
        mRangeToIdMap.put(CreditScoreRange.POOR, R.id.rb_poor);
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 6, true, true);
    }

    /**
     * @param map The {@link HashMap} to get the value from.
     * @param key The key we're looking for.
     * @return Value of the key OR -1 if not found.
     */
    private int getMapValue(HashMap<Integer, Integer> map, int key) {
        Integer value = map.get(key);

        if (value == null) {
            value = -1;
        }

        return value;
    }

    /**
     * @return Credit score range based on the checked Radio Button.
     */
    private int getCreditScoreRange() {
        return getMapValue(mIdToRangeMap, mView.getScoreRangeId());
    }

    /**
     * @return Radio Button ID based on the credit score range.
     */
    private int getCreditScoreRangeId() {
        return getMapValue(mRangeToIdMap, mModel.getCreditScoreRange());
    }

    /** {@inheritDoc} */
    @Override
    public CreditScoreModel createModel() {
        return new CreditScoreModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(CreditScoreView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setScoreRangeId(getCreditScoreRangeId());
    }

    @Override
    public void onBack() {
        mDelegate.creditScoreOnBackPressed();
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
        mModel.setCreditScoreRange(getCreditScoreRange());
        mView.showError(!mModel.hasAllData());

        if (mModel.hasAllData()) {
            saveData();
            mDelegate.creditScoreStored();
        }
    }
}
