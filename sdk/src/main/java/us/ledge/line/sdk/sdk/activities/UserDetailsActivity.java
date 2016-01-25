package us.ledge.line.sdk.sdk.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.presenters.UserDetailsPresenter;
import us.ledge.line.sdk.sdk.views.UserDetailsView;

/**
 * Wires up user details MVP pattern.
 * @author Wijnand
 */
public class UserDetailsActivity extends AppCompatActivity {

    private UserDetailsView mView;
    private UserDetailsPresenter mPresenter;

    /**
     * @return New presenter instance.
     */
    protected UserDetailsPresenter getPresenter() {
        return new UserDetailsPresenter(this);
    }

    /**{@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (UserDetailsView) View.inflate(this, R.layout.act_user_details, null);
        setContentView(mView);

        mPresenter = getPresenter();
        mPresenter.attachView(mView);
    }

    /**{@inheritDoc} */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
