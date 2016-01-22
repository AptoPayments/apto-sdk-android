package us.ledge.line.sdk.sdk.presenters;

import android.view.View;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Basic {@link Presenter} implementation.
 * @author Wijnand
 */
public class BasePresenter<V extends View, M extends Model> implements Presenter<V, M> {

    protected V mView;
    protected M mModel;

    /** {@inheritDoc} */
    @Override
    public void attachView(V view) {
        mView = view;
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView = null;
    }
}
