package com.shiftpayments.link.sdk.ui.presenters.card;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.models.card.GetPinModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.card.GetPinView;

public class GetPinPresenter extends ActivityPresenter<GetPinModel, GetPinView>
        implements Presenter<GetPinModel, GetPinView>, GetPinView.ViewListener {

    private GetPinDelegate mDelegate;

    @Override
    public void attachView(GetPinView view) {
        super.attachView(view);
        view.setViewListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.onGetPinOnClose();
    }

    public GetPinPresenter(AppCompatActivity activity, GetPinDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    @Override
    public GetPinModel createModel() {
        return new GetPinModel();
    }

    @Override
    public void getPinClickHandler() {
        mDelegate.onGetPinClickHandler();
    }

}
