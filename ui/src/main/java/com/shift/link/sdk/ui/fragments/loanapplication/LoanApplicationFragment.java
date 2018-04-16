package com.shift.link.sdk.ui.fragments.loanapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationDetailsPresenter;
import com.shift.link.sdk.ui.views.loanapplication.LoanApplicationDetailsView;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationDetailsPresenter;
import com.shift.link.sdk.ui.views.loanapplication.LoanApplicationDetailsView;

/**
 * Loan application details {@link Fragment}.
 * @author Wijnand
 */
public class LoanApplicationFragment extends Fragment {

    private LoanApplicationDetailsPresenter mPresenter;
    private LoanApplicationDetailsView mView;
    private LoanApplicationDetailsResponseVo mLoanApplication;

    /**
     * Creates a new {@link LoanApplicationFragment} instance.
     */
    public LoanApplicationFragment() { }

    private void createPresenter() {
        mPresenter = new LoanApplicationDetailsPresenter(getResources(), mLoanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (LoanApplicationDetailsView) inflater.inflate(R.layout.sv_loan_application_details, container, false);

        if (mLoanApplication != null) {
            if (mPresenter == null) {
                createPresenter();
            }

            mPresenter.attachView(mView);
        }

        return mView;
    }

    /** {@inheritDoc} */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * Stores new loan application data to use.
     * @param application New loan application data.
     */
    public void setLoanApplication(LoanApplicationDetailsResponseVo application) {
        mLoanApplication = application;
    }
}
