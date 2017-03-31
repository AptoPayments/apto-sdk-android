package me.ledge.link.sdk.ui.views.offers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;
import me.ledge.link.sdk.ui.views.DisplayErrorMessage;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Created by adrian on 31/03/2017.
 */

public abstract class OffersBaseView extends RelativeLayout
        implements DisplayErrorMessage, ViewWithToolbar, ViewWithIndeterminateLoading, View.OnClickListener {
    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public OffersBaseView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public OffersBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void showError(boolean show);

    public abstract void setData(IntermediateLoanApplicationModel data);

    public abstract void showEmptyCase(boolean show);

    public abstract void setListener(OffersListPresenter offersListPresenter);
}
