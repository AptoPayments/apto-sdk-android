package us.ledge.link.sdk.sdk.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.views.ViewWithToolbar;

/**
 * Displays the address screen.
 * @author Wijnand
 */
public class AddressView
        extends UserDataView<NextButtonListener>
        implements ViewWithToolbar, View.OnClickListener {

    private TextInputLayout mAddressWrapper;
    private EditText mAddressField;

    private EditText mApartmentField;

    private TextInputLayout mCityWrapper;
    private EditText mCityField;

    private TextInputLayout mStateWrapper;
    private EditText mStateField;

    private TextInputLayout mZipWrapper;
    private EditText mZipField;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public AddressView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mAddressWrapper = (TextInputLayout) findViewById(R.id.til_address);
        mAddressField = (EditText) findViewById(R.id.et_address);

        mApartmentField = (EditText) findViewById(R.id.et_apartment_number);

        mCityWrapper = (TextInputLayout) findViewById(R.id.til_city);
        mCityField = (EditText) findViewById(R.id.et_city);

        mStateWrapper = (TextInputLayout) findViewById(R.id.til_state);
        mStateField = (EditText) findViewById(R.id.et_state);

        mZipWrapper = (TextInputLayout) findViewById(R.id.til_zip_code);
        mZipField = (EditText) findViewById(R.id.et_zip_code);
    }

    /**
     * @return Address.
     */
    public String getAddress() {
        return mAddressField.getText().toString();
    }

    /**
     * Shows the address.
     * @param address New address.
     */
    public void setAddress(String address) {
        mAddressField.setText(address);
    }

    /**
     * @return Apartment or unit number.
     */
    public String getApartment() {
        return mApartmentField.getText().toString();
    }

    /**
     * Shows the apartment or unit number.
     * @param apartment New apartment or unit number.
     */
    public void setApartment(String apartment) {
        mApartmentField.setText(apartment);
    }

    /**
     * @return City.
     */
    public String getCity() {
        return mCityField.getText().toString();
    }

    /**
     * Shows the city.
     * @param city New city.
     */
    public void setCity(String city) {
        mCityField.setText(city);
    }

    /**
     * @return State.
     */
    public String getState() {
        return mStateField.getText().toString();
    }

    /**
     * Shows the new state.
     * @param state New State.
     */
    public void setState(String state) {
        mStateField.setText(state);
    }

    /**
     * @return Zip or postal code.
     */
    public String getZipCode() {
        return mZipField.getText().toString();
    }

    /**
     * Shows the zip or postal code.
     * @param zip New zip or postal code.
     */
    public void setZipCode(String zip) {
        mZipField.setText(zip);
    }

    /**
     * Updates the address field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateAddressError(boolean show, int errorMessageId) {
        updateErrorDisplay(mAddressWrapper, show, errorMessageId);
    }

    /**
     * Updates the city field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateCityError(boolean show, int errorMessageId) {
        updateErrorDisplay(mCityWrapper, show, errorMessageId);
    }

    /**
     * Updates the state field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateStateError(boolean show, int errorMessageId) {
        updateErrorDisplay(mStateWrapper, show, errorMessageId);
    }

    /**
     * Updates the ZIP field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateZipError(boolean show, int errorMessageId) {
        updateErrorDisplay(mZipWrapper, show, errorMessageId);
    }
}
