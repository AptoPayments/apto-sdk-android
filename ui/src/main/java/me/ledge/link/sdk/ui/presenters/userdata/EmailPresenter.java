package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.EmailModel;
import me.ledge.link.sdk.ui.views.userdata.EmailView;

/**
 * Created by pauteruel on 19/02/2018.
 */

public class EmailPresenter
        extends UserDataPresenter<EmailModel, EmailView>
        implements EmailView.ViewListener {

    private EmailDelegate mDelegate;

    /**
     * Creates a new {@link EmailPresenter} instance.
     * @param activity Activity.
     */
    public EmailPresenter(AppCompatActivity activity, EmailDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public EmailModel createModel() {
        return new EmailModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(EmailView view) {
        super.attachView(view);

        if (mModel.hasEmail()) {
            mView.setEmail(mModel.getEmail());
        }

        mView.setListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.emailLoginOnBackPressed();
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
        mModel.setEmail(mView.getEmail());
        mView.updateEmailError(!mModel.hasEmail(), R.string.email_error);

        if(mModel.hasAllData()) {
            saveData();
            mDelegate.emailStored();
        }
    }

}
