package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.responses.config.DisclaimerResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
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
        implements Presenter<TermsModel, TermsView>, TermsView.ViewListener {

    private String mTermsText;

    /**
     * Creates a new {@link TermsPresenter} instance.
     * @param activity Activity.
     */
    public TermsPresenter(AppCompatActivity activity) {
        super(activity);
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
        mResponseHandler.subscribe(this);
        if (mTermsText == null) {
            mView.showLoading(true);
            LedgeLinkUi.getLinkDisclaimer();
        } else {
            setTerms(mTermsText);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        mView.setListener(null);
        super.detachView();
    }

    public void setTerms(String terms) {
        mTermsText = terms;

        mView.setTerms(terms);
        mView.showLoading(false);
    }

    @Subscribe
    public void handleDisclaimer(DisclaimerResponseVo response) {
        if (isDisclaimerPresent(response)) {
            setTerms(response.linkDisclaimer.text);
        }
    }

    private boolean isDisclaimerPresent(DisclaimerResponseVo response) {
        return response!=null && response.linkDisclaimer!=null;
    }
}
