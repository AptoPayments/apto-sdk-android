package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.ArmedForcesModel;
import com.shift.link.sdk.ui.presenters.userdata.ArmedForcesDelegate;
import com.shift.link.sdk.ui.presenters.userdata.ArmedForcesPresenter;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.views.userdata.ArmedForcesView;

/**
 * Wires up the MVP pattern for the member of the armed forces screen.
 * @author Adrian
 */
public class ArmedForcesActivity extends UserDataActivity<ArmedForcesModel, ArmedForcesView, ArmedForcesPresenter> {

    /** {@inheritDoc} */
    @Override
    protected ArmedForcesView createView() {
        return (ArmedForcesView) View.inflate(this, R.layout.act_armed_forces, null);
    }

    /** {@inheritDoc} */
    @Override
    protected ArmedForcesPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof ArmedForcesDelegate) {
            return new ArmedForcesPresenter(this, (ArmedForcesDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ArmedForcesDelegate!");
        }
    }
}
