package com.shiftpayments.link.sdk.ui.mocks.views.userdata;

import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.shiftpayments.link.sdk.ui.views.userdata.MonthlyIncomeView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class MockMonthlyIncomeView extends MonthlyIncomeView {

    private int mIncome;

    public MockMonthlyIncomeView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mIncome = 0;
        mToolbar = new Toolbar(context);
    }

    @Override
    public void setListener(ViewListener listener) {
        // Do nothing.
    }

    @Override
    public void setSeekBarTransformer(DiscreteSeekBar.NumericTransformer transformer) {
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
    public void setIncome(double income) {
        mIncome = (int)(income+0.5d);
    }
}
