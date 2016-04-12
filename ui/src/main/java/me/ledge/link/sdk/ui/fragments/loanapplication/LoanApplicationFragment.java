package me.ledge.link.sdk.ui.fragments.loanapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationDetailsPresenter;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationDetailsView;

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

    /** {@inheritDoc} */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new LoanApplicationDetailsPresenter(getResources(), mLoanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (LoanApplicationDetailsView) inflater.inflate(R.layout.sv_loan_application_details, container, false);
        mPresenter.attachView(mView);

        return mView;
    }

    /** {@inheritDoc} */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    /**
     * Stores new loan application data to use.
     * @param application New loan application data.
     */
    public void setLoanApplication(LoanApplicationDetailsResponseVo application) {
        mLoanApplication = application;
    }
}
