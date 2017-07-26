package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.responses.config.DisclaimerVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.models.link.TermsModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
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
    private TermsDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

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
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mView.setListener(this);
        if (mTermsText == null) {
            mLoadingSpinnerManager.showLoading(true);
            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getLinkDisclaimer())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::showDisclaimer);
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
        mLoadingSpinnerManager.showLoading(false);
    }

    private void setMarkDownTerms(String terms) {
        mTermsText = terms;
        mActivity.runOnUiThread(() -> {
            mView.setMarkDownTerms(terms);
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    private boolean isDisclaimerPresent(DisclaimerVo disclaimer) {
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

    public void showDisclaimer(DisclaimerVo disclaimer) {
        if (!isDisclaimerPresent(disclaimer)) {
            return;
        }
        switch(DisclaimerVo.formatValues.valueOf(disclaimer.format)) {
            case plain_text:
                setTerms(disclaimer.value);
                break;
            case markdown:
                setMarkDownTerms(disclaimer.value);
                break;
        }
    }

    public void errorReceived(String error) {
        mView.displayErrorMessage(error);
    }
}
