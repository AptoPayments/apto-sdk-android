package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.api.vos.responses.config.LinkDisclaimerVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.sdk.storages.LinkDisclaimerDelegate;
import me.ledge.link.sdk.ui.models.link.TermsModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.views.userdata.TermsView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the terms and conditions screen.
 * @author Wijnand
 */
public class TermsPresenter
        extends UserDataPresenter<TermsModel, TermsView>
        implements Presenter<TermsModel, TermsView>, TermsView.ViewListener, LinkDisclaimerDelegate {

    private String mTermsText;
    private TermsDelegate mDelegate;

    /**
     * Creates a new {@link TermsPresenter} instance.
     * @param activity Activity.
     */
    public TermsPresenter(AppCompatActivity activity, TermsDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        showToolbarUpArrow();
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public TermsModel createModel() {
        return new TermsModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(TermsView view) {
        super.attachView(view);
        mView.setListener(this);
        if (mTermsText == null) {
            mView.showLoading(true);
            ConfigStorage.getInstance().getLinkDisclaimer(this);
        } else {
            setTerms(mTermsText);
        }
    }

    @Override
    public void onBack() {
        mDelegate.termsOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    private void setTerms(String terms) {
        mTermsText = terms;

        mView.setTerms(terms);
        mView.showLoading(false);
    }

    private boolean isDisclaimerPresent(LinkDisclaimerVo disclaimer) {
        return disclaimer!=null;
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        if (mModel.hasAllData()) {
            saveData();
            mDelegate.termsPresented();
        }
    }

    @Override
    public void linkDisclaimersRetrieved(LinkDisclaimerVo disclaimer) {
        if (isDisclaimerPresent(disclaimer)) {
            setTerms(disclaimer.text);
        }
    }

    @Override
    public void errorReceived(String error) {
        mView.displayErrorMessage(error);
    }
}
