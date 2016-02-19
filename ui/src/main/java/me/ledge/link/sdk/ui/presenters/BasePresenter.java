package me.ledge.link.sdk.ui.presenters;

import android.view.View;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Basic {@link Presenter} implementation.
 *
 * @param <M> {@link Model} type.
 * @param <V> {@link View} type.
 *
 * @author Wijnand
 */
public abstract class BasePresenter<M extends Model, V extends View> implements Presenter<M, V> {

    protected M mModel;
    protected V mView;

    /**
     * Creates a new {@link BasePresenter} instance.
     */
    public BasePresenter() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mModel = createModel();
        mView = null;
    }

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