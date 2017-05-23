package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import me.ledge.link.api.utils.TimeAtAddressRange;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.TimeAtAddressModel;
import me.ledge.link.sdk.ui.views.userdata.TimeAtAddressView;

/**
 * Concrete {@link Presenter} for the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressPresenter
        extends UserDataPresenter<TimeAtAddressModel, TimeAtAddressView>
        implements TimeAtAddressView.ViewListener {

    private HashMap<Integer, Integer> mIdToRangeMap;
    private HashMap<Integer, Integer> mRangeToIdMap;
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
    protected void init() {
        super.init();

        mIdToRangeMap = new HashMap<>(4);
        mIdToRangeMap.put(R.id.rb_less_than_6_months, TimeAtAddressRange.LESS_THAN_SIX_MONTHS);
        mIdToRangeMap.put(R.id.rb_6_to_12_months, TimeAtAddressRange.SIX_TO_TWELVE_MONTHS);
        mIdToRangeMap.put(R.id.rb_1_to_2_years, TimeAtAddressRange.ONE_TO_TWO_YEARS);
        mIdToRangeMap.put(R.id.rb_more_than_2_years, TimeAtAddressRange.MORE_THAN_TWO_YEARS);

        mRangeToIdMap = new HashMap<>(4);
        mRangeToIdMap.put(TimeAtAddressRange.LESS_THAN_SIX_MONTHS, R.id.rb_less_than_6_months);
        mRangeToIdMap.put(TimeAtAddressRange.SIX_TO_TWELVE_MONTHS, R.id.rb_6_to_12_months);
        mRangeToIdMap.put(TimeAtAddressRange.ONE_TO_TWO_YEARS, R.id.rb_1_to_2_years);
        mRangeToIdMap.put(TimeAtAddressRange.MORE_THAN_TWO_YEARS, R.id.rb_more_than_2_years);
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
     * @return Time at address range based on the checked Radio Button.
     */
    private int getTimeAtAddressRange() {
        return getMapValue(mIdToRangeMap, mView.getScoreRangeId());
    }

    /**
     * @return Radio Button ID based on the time at address range.
     */
    private int getTimeAtAddressRangeId() {
        return getMapValue(mRangeToIdMap, mModel.getTimeAtAddressRange());
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

        mView.setListener(this);
        mView.setScoreRangeId(getTimeAtAddressRangeId());
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
        mModel.setTimeAtAddressRange(getTimeAtAddressRange());
        mView.showError(!mModel.hasAllData());

        if (mModel.hasAllData()) {
            saveData();
            mDelegate.timeAtAddressStored();
        }
    }
}
