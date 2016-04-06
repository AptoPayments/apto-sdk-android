package me.ledge.link.sdk.ui.fragments.loanapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.ledge.link.sdk.ui.R;

/**
 * Loan application details {@link Fragment}.
 * @author Wijnand
 */
public class LoanApplicationFragment extends Fragment {

    /**
     * Creates a new {@link LoanApplicationFragment} instance.
     */
    public LoanApplicationFragment() { }

    /** {@inheritDoc} */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sv_loan_application_details, container, false);
    }
}
