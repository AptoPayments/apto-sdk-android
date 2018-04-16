package com.shift.link.sdk.ui.presenters;

import android.view.View;

import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shift.link.sdk.ui.ShiftUi;
import com.shift.link.sdk.ui.models.Model;

/**
 * Basic {@link Presenter} implementation.
 *
 * @param <M> {@link Model} type.
 * @param <V> {@link View} type.
 *
 * @author Wijnand
 */
public abstract class BasePresenter<M extends Model, V extends View> implements Presenter<M, V> {

    protected ApiResponseHandler mResponseHandler;
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
        mResponseHandler = ShiftUi.getResponseHandler();
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
