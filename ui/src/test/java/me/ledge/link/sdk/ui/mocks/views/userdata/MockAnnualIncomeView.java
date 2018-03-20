package me.ledge.link.sdk.ui.mocks.views.userdata;

import android.content.Context;

import me.ledge.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;

public class MockAnnualIncomeView extends AnnualIncomeView {

    private int mIncome;
    private int mEmploymentStatus;
    private int mSalaryFrequency;

    public MockAnnualIncomeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mIncome = 0;
    }

    @Override
    public void setIncome(long income) {
        mIncome = (int)(income+0.5d);;
    }

    @Override
    public void setIncomeType(int position) {
        mEmploymentStatus = position;
    }

    @Override
    public void setSalaryFrequency(int position) {
        mSalaryFrequency = position;
    }

    @Override
    public void setListener(ViewListener listener) {
        // Do nothing.
    }

    @Override
    public void setMinMax(int min, int max) {
        // Do nothing.
    }

    @Override
    public int getIncome() {
        return mIncome;
    }

    @Override
    public void updateIncomeTypeError(boolean show) {
        // Do nothing.
    }

    @Override
    public void updateSalaryFrequencyError(boolean show) {
        // Do nothing.
    }

    @Override
    public void showEmploymentFields(boolean show) {
        // Do nothing.
    }

    @Override
    public LoadingView getLoadingView() {
        return null;
    }

    @Override
    public void setIncomeTypesAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        // Do nothing.
    }

    @Override
    public void setSalaryFrequencyAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        // Do nothing.
    }

    @Override
    public IdDescriptionPairDisplayVo getIncomeType() {
        return new IdDescriptionPairDisplayVo(mEmploymentStatus, "");
    }

    @Override
    public IdDescriptionPairDisplayVo getSalaryFrequency() {
        return new IdDescriptionPairDisplayVo(mSalaryFrequency, "");
    }
}
