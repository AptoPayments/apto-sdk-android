package com.shiftpayments.link.sdk.ui.presenters.showgenericmessage;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.responses.workflow.GenericMessageConfigurationVo;
import com.shiftpayments.link.sdk.ui.models.showgenericmessage.ShowGenericMessageModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataPresenter;
import com.shiftpayments.link.sdk.ui.views.showgenericmessage.ShowGenericMessageView;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperConfiguration;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

/**
 * Concrete {@link Presenter} for the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessagePresenter
        extends UserDataPresenter<ShowGenericMessageModel, ShowGenericMessageView>
        implements Presenter<ShowGenericMessageModel, ShowGenericMessageView>, ShowGenericMessageView.ViewListener {

    private ShowGenericMessageDelegate mDelegate;

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
        mView.setListener(this);
        ShowGenericMessageModule module = (ShowGenericMessageModule) ModuleManager.getInstance().getCurrentModule();
        GenericMessageConfigurationVo actionConfig = module.getConfig();
        mActivity.setTitle(actionConfig.title);
        setGenericMessage(actionConfig.content.value);
        if(actionConfig.callToAction != null) {
            mView.setCallToAction(actionConfig.callToAction.title.toUpperCase());
        }
        if(actionConfig.image != null) {
            mView.setImage(actionConfig.image);
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

    private void setGenericMessage(String genericMessage) {
        mView.setMarkdown(genericMessage);
    }
}
