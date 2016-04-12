package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.content.res.Resources;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.models.loanapplication.details.LoanApplicationDetailsModel;
import me.ledge.link.sdk.ui.presenters.BasePresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationDetailsView;

/**
 * Concrete {@link Presenter} for an open loan application.
 * @author Wijnand
 */
public class LoanApplicationDetailsPresenter
        extends BasePresenter<LoanApplicationDetailsModel, LoanApplicationDetailsView>
        implements Presenter<LoanApplicationDetailsModel, LoanApplicationDetailsView> {

    private final Resources mResources;
    private final LoanApplicationDetailsResponseVo mRawApplication;

    /**
     * Creates a new {@link LoanApplicationDetailsPresenter} instance.
     * @param resources Android application resources.
     * @param application Loan application details.
     */
    public LoanApplicationDetailsPresenter(Resources resources, LoanApplicationDetailsResponseVo application) {
        super();
        mResources = resources;
        mRawApplication = application;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationDetailsModel createModel() {
        if (mResources == null) {
            return null;
        }

        return new LoanApplicationDetailsModel(mRawApplication, mResources, LedgeLinkUi.getImageLoader());
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanApplicationDetailsView view) {
        super.attachView(view);

        if (mModel == null) {
            mModel = createModel();
        }

        mView.setData(mModel);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setData(null);
        super.detachView();
    }
}
