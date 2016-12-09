package me.ledge.link.sdk.handlers.eventbus.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.responses.config.DisclaimerResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.TermsPresenter;
import me.ledge.link.sdk.ui.views.userdata.TermsView;
import org.greenrobot.eventbus.Subscribe;

/**
 * TODO: Class documentation.
 * @author Wijnand
 */
public class EventBusTermsPresenter extends TermsPresenter {

    /**
     * Creates a new {@link EventBusTermsPresenter} instance.
     * @param activity Activity.
     */
    public EventBusTermsPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(TermsView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    @Subscribe
    public void handleDisclaimer(DisclaimerResponseVo response) {
        if (response != null) {
            setTerms(response.linkDisclaimer.text);
        }
    }
}
