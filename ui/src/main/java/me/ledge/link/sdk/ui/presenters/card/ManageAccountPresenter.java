package me.ledge.link.sdk.ui.presenters.card;

/**
 * Created by adrian on 27/11/2017.
 */


import me.ledge.link.sdk.ui.models.card.ManageAccountModel;
import me.ledge.link.sdk.ui.presenters.BasePresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.card.ManageAccountView;

/**
 * Concrete {@link Presenter} for the manage account screen.
 * @author Adrian
 */
public class ManageAccountPresenter
        extends BasePresenter<ManageAccountModel, ManageAccountView>
        implements Presenter<ManageAccountModel, ManageAccountView>, ManageAccountView.ViewListener {

    /** {@inheritDoc} */
    @Override
    public void attachView(ManageAccountView view) {
        super.attachView(view);
        view.setViewListener(this);
        view.showLoading(false);
    }

    @Override
    public ManageAccountModel createModel() {
        return new ManageAccountModel();
    }

}
