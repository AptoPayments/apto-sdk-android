package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.models.userdata.TermsModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.TermsView;

/**
 * Concrete {@link Presenter} for the terms and conditions screen.
 * @author Wijnand
 */
public class TermsPresenter
        extends UserDataPresenter<TermsModel, TermsView>
        implements Presenter<TermsModel, TermsView> {

    /**
     * Creates a new {@link TermsPresenter} instance.
     * @param activity Activity.
     */
    public TermsPresenter(AppCompatActivity activity) {
        super(activity);
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
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }
}
