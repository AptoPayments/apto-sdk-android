package com.shiftpayments.link.sdk.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

/**
 * Generic MVP Activity, wires up the MVP parts.
 *
 * @param <M> {@link Model} type.
 * @param <V> {@link View} type.
 * @param <P> {@link Presenter} type.
 *
 * @author Wijnand
 */
public abstract class MvpActivity<M extends ActivityModel, V extends View & ViewWithToolbar, P extends ActivityPresenter<M, V>>
        extends AppCompatActivity {

    protected V mView;
    protected P mPresenter;

    /**
     * @return New {@link View} instance.
     */
    protected abstract V createView();

    /**
     * @return New {@link Presenter} instance.
     */
    protected abstract P createPresenter(BaseDelegate delegate);

    /**
     * Creates the {@link View} and {@link Presenter} and wires everything.<br/>
     * Assumes that the {@link Presenter} will instantiate the {@link Model}.<br/>
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = createView();
        setContentView(mView);
        ShiftBaseModule currentModule = ModuleManager.getInstance().getCurrentModule();
        if(currentModule != null) {
            currentModule.setActivity(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(UIStorage.getInstance().getStatusBarColor(getResources().getColor(R.color.llsdk_actionbar_background)));
        }

        mPresenter = createPresenter(currentModule);
        mPresenter.attachView(mView);
    }

    /**
     * Detaches the {@link View} from the {@link Presenter}. Avoids memory leaks.<br/>
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void onBackPressed() {
        mPresenter.onBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        int i = item.getItemId();
        if (i == android.R.id.home) {
            mPresenter.onBack();
        } else if (i == R.id.action_next) {
            mPresenter.nextClickHandler();
        } else {
            handled = false;
        }

        return handled || super.onOptionsItemSelected(item);
    }
}
