package us.ledge.line.sdk.example.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import us.ledge.line.sdk.example.R;
import us.ledge.line.sdk.example.views.MainView;
import us.ledge.line.sdk.sdk.activities.userdata.LoanAmountActivity;

/**
 * Main display.
 * @author Wijnand
 */
public class MainActivity extends Activity implements MainView.ViewListener {

    private MainView mView;

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
        startActivity(new Intent(this, LoanAmountActivity.class));
    }
}
