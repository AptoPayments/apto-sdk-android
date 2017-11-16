package me.ledge.link.sdk.ui.presenters.showgenericmessage;

import android.support.v7.app.AppCompatActivity;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.workflow.ActionVo;
import me.ledge.link.api.vos.responses.workflow.GenericMessageConfigurationVo;
import me.ledge.link.sdk.ui.models.showgenericmessage.ShowGenericMessageModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.showgenericmessage.ShowGenericMessageView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessagePresenter
        extends UserDataPresenter<ShowGenericMessageModel, ShowGenericMessageView>
        implements Presenter<ShowGenericMessageModel, ShowGenericMessageView>, ShowGenericMessageView.ViewListener {

    private String mGenericMessage;
    private ShowGenericMessageDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link ShowGenericMessagePresenter} instance.
     * @param activity Activity.
     */
    public ShowGenericMessagePresenter(AppCompatActivity activity, ShowGenericMessageDelegate delegate) {
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
    public ShowGenericMessageModel createModel() {
        return new ShowGenericMessageModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ShowGenericMessageView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mView.setListener(this);
        if (mGenericMessage == null) {
            mLoadingSpinnerManager.showLoading(true);
            CompletableFuture
                    .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::projectConfigRetrieved);
        } else {
            setGenericMessage(mGenericMessage);
        }
    }

    @Override
    public void onBack() {
        mActivity.finish();
        mDelegate.showGenericMessageScreenOnBackPressed();
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
            mDelegate.showGenericMessageScreenOnNextPressed();
        }
    }

    private void projectConfigRetrieved(ConfigResponseVo configResponseVo) {
        // TODO: receive GenericMessageConfigurationVo as input
        ActionVo welcomeScreenAction = configResponseVo.welcomeScreenAction;
        GenericMessageConfigurationVo actionConfig = (GenericMessageConfigurationVo) welcomeScreenAction.configuration;
        mActivity.runOnUiThread(() -> {
            mActivity.setTitle(actionConfig.title);
            mView.setCallToAction(actionConfig.callToAction.title.toUpperCase());
            setGenericMessage(actionConfig.content.value);
            if(actionConfig.image != null) {
                mView.setImage(actionConfig.image);
            }
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    private void errorReceived(String error) {
        mView.displayErrorMessage(error);
    }

    private void setGenericMessage(String genericMessage) {
        mGenericMessage = genericMessage;
        mView.setMarkdown(genericMessage);
    }
}
