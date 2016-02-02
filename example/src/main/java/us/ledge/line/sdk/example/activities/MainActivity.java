package us.ledge.line.sdk.example.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import us.ledge.line.sdk.example.R;
import us.ledge.line.sdk.example.views.MainView;
import us.ledge.line.sdk.sdk.activities.userdata.LoanAmountActivity;
import us.ledge.line.sdk.sdk.vos.UserDataVo;

/**
 * Main display.
 * @author Wijnand
 */
public class MainActivity extends Activity implements MainView.ViewListener {

    private MainView mView;

    /**
     * @return Start {@link Intent} for the Ledge Line SDK.
     */
    private Intent createStartIntent() {
        boolean hasExtraData = false;
        UserDataVo data = new UserDataVo();

        if (hasValue(mView.getLoanAmount())) {
            data.loanAmount = Integer.parseInt(mView.getLoanAmount());
            hasExtraData = true;
        }
        if (hasValue(mView.getFirstName())) {
            data.firstName = mView.getFirstName();
            hasExtraData = true;
        }
        if (hasValue(mView.getLastName())) {
            data.lastName = mView.getLastName();
            hasExtraData = true;
        }
        if (hasValue(mView.getEmail())) {
            data.emailAddress = mView.getEmail();
            hasExtraData = true;
        }
        if (hasValue(mView.getPhoneNumber())) {
            data.phoneNumber = Long.parseLong(mView.getPhoneNumber());
            hasExtraData = true;
        }
        if (hasValue(mView.getAddress())) {
            data.address = mView.getAddress();
            hasExtraData = true;
        }
        if (hasValue(mView.getApartmentNumber())) {
            data.apartmentNumber = mView.getApartmentNumber();
            hasExtraData = true;
        }
        if (hasValue(mView.getCity())) {
            data.city = mView.getCity();
            hasExtraData = true;
        }
        if (hasValue(mView.getState())) {
            data.state = mView.getState();
            hasExtraData = true;
        }
        if (hasValue(mView.getZipCode())) {
            data.zip = mView.getZipCode();
            hasExtraData = true;
        }
        if (hasValue(mView.getIncome())) {
            data.income = Integer.parseInt(mView.getIncome());
            hasExtraData = true;
        }

        Intent startIntent = new Intent(this, LoanAmountActivity.class);
        if (hasExtraData) {
            startIntent.putExtra(UserDataVo.USER_DATA_KEY, data);
        }

        return startIntent;
    }

    /**
     * @param fieldValue Field value to check.
     * @return Whether the field has a value set.
     */
    private boolean hasValue(String fieldValue) {
        return !TextUtils.isEmpty(fieldValue);
    }

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (MainView) View.inflate(this, R.layout.act_main, null);
        mView.setViewListener(this);

        setContentView(mView);
    }

    /** {@inheritDoc} */
    @Override
    public void offersPressedHandler() {
        startActivity(createStartIntent());
    }
}
