package us.ledge.line.sdk.sdk.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import us.ledge.line.sdk.sdk.R;

/**
 * Shows the user details.
 * @author Wijnand
 */
public class UserDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    /**
     * Sets up the toolbar.
     */
    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbar);
    }

    /**{@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_user_details);
        setupActionBar();
    }
}
