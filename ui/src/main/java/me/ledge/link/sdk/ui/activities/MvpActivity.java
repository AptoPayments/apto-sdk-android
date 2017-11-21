package me.ledge.link.sdk.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

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
        LedgeBaseModule currentModule = ModuleManager.getInstance().getCurrentModule();
        if(currentModule != null) {
            currentModule.setActivity(this);
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

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.onBack();
                break;
            default:
                handled = false;
                break;
        }

        return handled || super.onOptionsItemSelected(item);
    }
}
