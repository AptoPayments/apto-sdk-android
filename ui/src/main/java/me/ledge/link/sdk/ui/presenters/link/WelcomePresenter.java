package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.responses.config.DisclaimerVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.models.link.WelcomeModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.link.WelcomeView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the welcome screen.
 * @author Adrian
 */
public class WelcomePresenter
        extends UserDataPresenter<WelcomeModel, WelcomeView>
        implements Presenter<WelcomeModel, WelcomeView>, WelcomeView.ViewListener {

    private String mWelcomeText;
    private WelcomeDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link WelcomePresenter} instance.
     * @param activity Activity.
     */
    public WelcomePresenter(AppCompatActivity activity, WelcomeDelegate delegate) {
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
    public WelcomeModel createModel() {
        return new WelcomeModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(WelcomeView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mView.setListener(this);
        if (mWelcomeText == null) {
            mLoadingSpinnerManager.showLoading(true);
            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getLinkDisclaimer())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::showDisclaimer);
        } else {
            setWelcome(mWelcomeText);
        }
    }

    @Override
    public void onBack() {
        mActivity.finish();
        mDelegate.welcomeScreenOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    private void setWelcome(String terms) {
        mWelcomeText = terms;

        //mView.setWelcome(terms);
        mLoadingSpinnerManager.showLoading(false);
    }

    private void setMarkDownWelcome(String terms) {
        mWelcomeText = terms;
        mActivity.runOnUiThread(() -> {
            //mView.setMarkDownWelcome(terms);
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
            mDelegate.welcomeScreenPresented();
        }
    }

    public void showDisclaimer(DisclaimerVo disclaimer) {
        if (!isDisclaimerPresent(disclaimer)) {
            return;
        }
        switch(DisclaimerVo.formatValues.valueOf(disclaimer.format)) {
            case plain_text:
                setWelcome(disclaimer.value);
                break;
            case markdown:
                setMarkDownWelcome(disclaimer.value);
                break;
        }
    }

    public void errorReceived(String error) {
        mView.displayErrorMessage(error);
    }
}
