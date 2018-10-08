package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.models.userdata.AddressModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.userdata.AddressView;

/**
 * Concrete {@link Presenter} for the address screen.
 * @author Wijnand
 */
public class AddressPresenter
        extends UserDataPresenter<AddressModel, AddressView>
        implements AddressView.ViewListener {

    private AddressDelegate mDelegate;

    /**
     * Creates a new {@link AddressPresenter} instance.
     * @param activity Activity.
     */
    public AddressPresenter(AppCompatActivity activity, AddressDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddressView view) {
        super.attachView(view);

        // Set data.
        mView.setAddress(mModel.getFullAddress());

        mView.setListener(this);
        mView.enableNextButton(true);
    }

    @Override
    public void onBack() {
        mDelegate.addressOnBackPressed();
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
        mDelegate.addressStored();
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }

}

