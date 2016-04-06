package me.ledge.link.sdk.ui.adapters.loanapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import me.ledge.link.sdk.ui.fragments.loanapplication.LoanApplicationFragment;

/**
 * A concrete {@link FragmentStatePagerAdapter} that lists the user's open loan applications.
 * @author Wijnand
 */
public class LoanApplicationPagerAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;

    public LoanApplicationPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    /** {@inheritDoc} */
    @Override
    public Fragment getItem(int position) {
        LoanApplicationFragment fragment = null;

        if (position >= 0) {
            fragment = (LoanApplicationFragment) LoanApplicationFragment.instantiate(
                    mContext, LoanApplicationFragment.class.getName());
        }

        return fragment;
    }

    /** {@inheritDoc} */
    @Override
    public int getCount() {
        return 4;
    }
}
