package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.Menu;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.IdentityVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.IdentityVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.IdentityVerificationView;

import java.util.HashMap;
import java.util.List;

/**
 * Wires up the MVP pattern for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationActivity
        extends MvpActivity<IdentityVerificationModel, IdentityVerificationView, IdentityVerificationPresenter> {

    public static final String EXTRA_ALLOWED_DOCUMENT_TYPES = "com.shiftpayments.link.sdk.ui.presenters.userdata.ALLOWEDDOCUMENTS";

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationView createView() {
        return (IdentityVerificationView) View.inflate(this, R.layout.act_id_verification, null);
    }

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof IdentityVerificationDelegate) {
            HashMap<String, List<String>> allowedDocumentTypes =
                    (HashMap<String, List<String>>)getIntent().getSerializableExtra(EXTRA_ALLOWED_DOCUMENT_TYPES);
            return new IdentityVerificationPresenter(this, (IdentityVerificationDelegate) delegate, allowedDocumentTypes);
        }
        else {
            throw new NullPointerException("Received Module does not implement IdentityVerificationDelegate!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do not show next button for this screen
        return true;
    }
}
