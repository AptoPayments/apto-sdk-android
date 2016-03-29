package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import me.ledge.common.fragments.dialogs.NotificationDialogFragment;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.views.loanapplication.LoanAgreementView;

/**
 * Concrete {@link Presenter} for the loan agreement.
 * @author Wijnand
 */
public class LoanAgreementPresenter
        extends ActivityPresenter<LoanAgreementModel, LoanAgreementView>
        implements Presenter<LoanAgreementModel, LoanAgreementView>, LoanAgreementView.ViewListener {

    private NotificationDialogFragment mNotificationDialog;

    /**
     * Creates a new {@link LoanAgreementPresenter} instance.
     * @param activity Activity.
     */
    public LoanAgreementPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public LoanAgreementModel createModel() {
        LoanApplicationDetailsResponseVo application = LoanStorage.getInstance().getCurrentLoanApplication();
        OfferVo offer = null;

        if (application != null) {
            offer = application.offer;
        }

        return new LoanAgreementModel(offer, LedgeLinkUi.getImageLoader());
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAgreementView view) {
        super.attachView(view);
        mView.setViewListener(this);
        mView.showLoading(false);
        mView.updateBottomButton(false);
        mView.setData(mModel);

        mNotificationDialog = new NotificationDialogFragment();
        mNotificationDialog.setTitle(mActivity.getString(R.string.loan_agreement_terms_dialog_title));
        mNotificationDialog.setMessage(mActivity.getString(R.string.loan_agreement_terms_dialog_message));
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (mView == null) {
            return;
        }

        boolean fullyScrolled = false;
        if (scrollY >= mView.getMaxScroll()) {
            fullyScrolled = true;
        }

        mView.updateBottomButton(fullyScrolled);
    }

    /** {@inheritDoc} */
    @Override
    public void onDownMotionEvent() { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void confirmClickHandler() {
        if (mView.getCurrentScroll() >= mView.getMaxScroll()) {
            if (mView.hasAcceptedTerms()) {
                // TODO: makeConfirmApiCall();
            } else {
                mNotificationDialog.show(mActivity.getFragmentManager(), NotificationDialogFragment.DIALOG_TAG);
            }
        } else {
            mView.scrollToBottom();
        }
    }
}