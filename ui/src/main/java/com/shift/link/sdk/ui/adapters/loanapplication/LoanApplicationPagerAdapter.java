package com.shift.link.sdk.ui.adapters.loanapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shift.link.sdk.ui.fragments.loanapplication.LoanApplicationFragment;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.fragments.loanapplication.LoanApplicationFragment;

/**
 * A concrete {@link FragmentStatePagerAdapter} that lists the user's open loan applications.
 * @author Wijnand
 */
public class LoanApplicationPagerAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private final LoanApplicationDetailsResponseVo[] mApplicationList;

    /**
     * Creates a new {@link LoanApplicationPagerAdapter} instance.
     * @param fm Fragment manager.
     * @param context Context.
     * @param applicationList List of open loan applications.
     */
    public LoanApplicationPagerAdapter(FragmentManager fm, Context context,
            LoanApplicationDetailsResponseVo[] applicationList) {

        super(fm);
        mContext = context;
        mApplicationList = applicationList;
    }

    /** {@inheritDoc} */
    @Override
    public Fragment getItem(int position) {
        LoanApplicationFragment fragment = null;

        if (position >= 0 && position < getCount()) {
            fragment = (LoanApplicationFragment) LoanApplicationFragment.instantiate(
                    mContext, LoanApplicationFragment.class.getName());
            fragment.setLoanApplication(mApplicationList[position]);
        }

        return fragment;
    }

    /** {@inheritDoc} */
    @Override
    public int getCount() {
        int count = 0;

        if (mApplicationList != null) {
            count = mApplicationList.length;
        }

        return count;
    }
}
