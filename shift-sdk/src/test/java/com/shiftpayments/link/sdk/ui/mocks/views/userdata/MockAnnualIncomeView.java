package com.shiftpayments.link.sdk.ui.mocks.views.userdata;

import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.userdata.AnnualIncomeView;
import com.shiftpayments.link.sdk.ui.widgets.HintArrayAdapter;

public class MockAnnualIncomeView extends AnnualIncomeView {

    private int mIncome;
    private int mEmploymentStatus;
    private int mSalaryFrequency;

    public MockAnnualIncomeView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mIncome = 0;
        mToolbar = new Toolbar(context);
    }

    @Override
    public void setIncome(long income) {
        mIncome = (int)(income+0.5d);
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
