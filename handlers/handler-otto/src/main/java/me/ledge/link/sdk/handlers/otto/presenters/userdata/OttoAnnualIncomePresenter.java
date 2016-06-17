package me.ledge.link.sdk.handlers.otto.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import com.squareup.otto.Subscribe;
import me.ledge.link.api.vos.responses.config.EmploymentStatusListResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;

/**
 * An {@link AnnualIncomePresenter} that uses the Otto event bus to receive API responses.
 * @author Wijnand
 */
public class OttoAnnualIncomePresenter extends AnnualIncomePresenter {

    /**
     * Creates a new {@link AnnualIncomePresenter} instance.
     * @param activity Activity.
     */
    public OttoAnnualIncomePresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AnnualIncomeView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /**
     * Called when the employment statuses list API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(EmploymentStatusListResponseVo response) {
        if (response != null) {
            setEmploymentStatusesList(response.data);
        }
    }
}
