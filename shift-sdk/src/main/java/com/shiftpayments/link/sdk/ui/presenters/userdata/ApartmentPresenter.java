package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.models.userdata.ApartmentModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.userdata.ApartmentView;

/**
 * Concrete {@link Presenter} for the apartment screen.
 * @author Wijnand
 */
public class ApartmentPresenter
        extends UserDataPresenter<ApartmentModel, ApartmentView>
        implements ApartmentView.ViewListener {

    private ApartmentDelegate mDelegate;

    /**
     * Creates a new {@link ApartmentPresenter} instance.
     * @param activity Activity.
     */
    public ApartmentPresenter(AppCompatActivity activity, ApartmentDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ApartmentView view) {
        super.attachView(view);

        // Set data.
        mView.setAddressLabel(mModel.getAddress());

        mView.setListener(this);
        mView.enableNextButton(true);
    }

    @Override
    public void onBack() {
        mDelegate.apartmentOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Store data.
        mModel.setApartmentNumber(mView.getApartment());
        mDelegate.apartmentStored();
    }

    /** {@inheritDoc} */
    @Override
    public ApartmentModel createModel() {
        return new ApartmentModel();
    }
}

