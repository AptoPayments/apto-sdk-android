package us.ledge.line.sdk.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import us.ledge.line.sdk.example.R;

/**
 * Displays the main View.
 * @author Wijnand
 */
public class MainView extends ScrollView implements View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the "Get loan offers" button has been pressed.
         */
        void offersPressedHandler();
    }

    private Button mOffersButton;
    private EditText mLoanAmountField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private EditText mPhoneField;
    private EditText mAddressField;
    private EditText mApartmentField;
    private EditText mCityField;
    private EditText mStateField;
    private EditText mZipField;
    private EditText mIncomeField;

    private ViewListener mListener;

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     */
    public MainView(Context context) {
        super(context);
    }

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs See {@link ScrollView#ScrollView}.
     */
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mOffersButton = (Button) findViewById(R.id.bttn_get_offers);
        mLoanAmountField = (EditText) findViewById(R.id.et_loan_amount);
        mFirstNameField = (EditText) findViewById(R.id.et_first_name);
        mLastNameField = (EditText) findViewById(R.id.et_last_name);
        mEmailField = (EditText) findViewById(R.id.et_email);
        mPhoneField = (EditText) findViewById(R.id.et_phone);
        mAddressField = (EditText) findViewById(R.id.et_address);
        mApartmentField = (EditText) findViewById(R.id.et_apartment_number);
        mCityField = (EditText) findViewById(R.id.et_city);
        mStateField = (EditText) findViewById(R.id.et_state);
        mZipField = (EditText) findViewById(R.id.et_zip_code);
        mIncomeField = (EditText) findViewById(R.id.et_income);

        mOffersButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.bttn_get_offers:
                mListener.offersPressedHandler();
                break;
            default:
                // Do nothing.
                break;
        }
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener The new listener.
     */
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * @return Loan amount.
     */
    public String getLoanAmount() {
        return mLoanAmountField.getText().toString();
    }

    /**
     * @return First name.
     */
    public String getFirstName() {
        return mFirstNameField.getText().toString();
    }

    /**
     * @return Last name.
     */
    public String getLastName() {
        return mLastNameField.getText().toString();
    }

    /**
     * @return Email address.
     */
    public String getEmail() {
        return mEmailField.getText().toString();
    }

    /**
     * @return Phone number.
     */
    public String getPhoneNumber() {
        return mPhoneField.getText().toString();
    }

    /**
     * @return Address.
     */
    public String getAddress() {
        return mAddressField.getText().toString();
    }

    /**
     * @return Apartment number.
     */
    public String getApartmentNumber() {
        return mApartmentField.getText().toString();
    }

    /**
     * @return City.
     */
    public String getCity() {
        return mCityField.getText().toString();
    }

    /**
     * @return State.
     */
    public String getState() {
        return mStateField.getText().toString();
    }

    /**
     * @return Zip code.
     */
    public String getZipCode() {
        return mZipField.getText().toString();
    }

    /**
     * @return Income.
     */
    public String getIncome() {
        return mIncomeField.getText().toString();
    }
}
