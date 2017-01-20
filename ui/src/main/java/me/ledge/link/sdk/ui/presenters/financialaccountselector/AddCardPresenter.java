package me.ledge.link.sdk.ui.presenters.financialaccountselector;


import android.support.v7.app.AppCompatActivity;

import me.ledge.link.sdk.ui.models.financialaccountselector.AddCardModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.financialaccountselector.AddCardView;


/**
 * Concrete {@link Presenter} for the add card screen.
 * @author Adrian
 */
public class AddCardPresenter
    extends ActivityPresenter<AddCardModel, AddCardView>
    implements Presenter<AddCardModel, AddCardView>, AddCardView.ViewListener {

    private AddCardDelegate mDelegate;

    /**
     * Creates a new {@link ActivityPresenter} instance.
     *
     * @param activity Activity.
     */
    public AddCardPresenter(AppCompatActivity activity, AddCardDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddCardView view) {
        super.attachView(view);
        view.setListener(this);
        view.setCardName(mModel.getCardHolderName());
    }


    @Override
    public void onBack() {
        mDelegate.addCardOnBackPressed();
    }

    @Override
    public AddCardModel createModel() {
        return new AddCardModel();
    }

    @Override
    public void scanClickHandler() {
        // Card.io
    }

    @Override
    public void addCardClickHandler() {
        mDelegate.cardAdded();
    }
}
