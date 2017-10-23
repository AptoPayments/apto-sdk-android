package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.workflow.ActionVo;
import me.ledge.link.api.vos.responses.workflow.GenericMessageConfigurationVo;
import me.ledge.link.sdk.ui.models.link.WelcomeModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
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
                    .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::projectConfigRetrieved);
        } else {
            setWelcomeText(mWelcomeText);
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

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        if (mModel.hasAllData()) {
            saveData();
            mDelegate.welcomeScreenOnNextPressed();
        }
    }

    private void projectConfigRetrieved(ConfigResponseVo configResponseVo) {
        ActionVo welcomeScreenAction = configResponseVo.welcomeScreenAction;
        GenericMessageConfigurationVo actionConfig = (GenericMessageConfigurationVo) welcomeScreenAction.configuration;
        mActivity.runOnUiThread(() -> {
            mActivity.setTitle(actionConfig.title);
            mView.setCallToAction(actionConfig.callToAction.title);
            setWelcomeText(actionConfig.content.value);
            if(actionConfig.image != null) {
                mView.setImage(actionConfig.image);
            }
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    private void errorReceived(String error) {
        mView.displayErrorMessage(error);
    }

    private void setWelcomeText(String welcomeText) {
        mWelcomeText = welcomeText;
        mView.setMarkdown(welcomeText);
    }
}
