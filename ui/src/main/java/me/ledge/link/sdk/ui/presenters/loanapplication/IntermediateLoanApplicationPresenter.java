package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.RejectedLoanApplicationModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.loanapplication.IntermediateLoanApplicationView;

/**
 * Concrete {@link Presenter} for a loan application in an intermediate state.
 * @author Wijnand
 */
public class IntermediateLoanApplicationPresenter
        extends ActivityPresenter<IntermediateLoanApplicationModel, IntermediateLoanApplicationView>
        implements Presenter<IntermediateLoanApplicationModel, IntermediateLoanApplicationView> {

    /**
     * Creates a new {@link IntermediateLoanApplicationPresenter} instance.
     * @param activity Activity.
     */
    public IntermediateLoanApplicationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public IntermediateLoanApplicationModel createModel() {
        return new RejectedLoanApplicationModel(null);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IntermediateLoanApplicationView view) {
        super.attachView(view);
        view.setData(mModel);
    }
}
