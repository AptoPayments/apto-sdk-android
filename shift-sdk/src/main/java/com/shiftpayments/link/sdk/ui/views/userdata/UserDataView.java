package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.ProgressBarWidget;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperConfiguration;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Generic user data related View.
 * @author Wijnand
 */
public class UserDataView<L extends StepperListener & NextButtonListener>
        extends RelativeLayout
        implements ViewWithToolbar {

    protected L mListener;
    protected Toolbar mToolbar;
    protected ProgressBarWidget mProgressBar;
    protected Observable<Boolean> mUiFieldsObservable;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public UserDataView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public UserDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    protected void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mProgressBar = findViewById(R.id.pbw_progress_bar_wrapper);
    }

    /**
     * Sets up all required listeners.
     */
    protected void setupListeners() {
        if (mProgressBar != null) {
            mProgressBar.setListener(mListener);
        }
    }

    protected void setColors() {
        int color = UIStorage.getInstance().getUiPrimaryColor();
        if(mProgressBar != null) {
            mProgressBar.setProgressBarColor(color);
        }
    }

    /**
     * Updates an error display.
     * @param wrapper The {@link TextInputLayout} to update.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    protected void updateErrorDisplay(TextInputLayout wrapper, boolean show, int errorMessageId) {
        if (show) {
            wrapper.setError(getResources().getString(errorMessageId));
        } else {
            wrapper.setError(null);
            wrapper.setErrorEnabled(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new {@link L}istener.
     * @param listener New {@link L}istener.
     */
    public void setListener(L listener) {
        mListener = listener;

        if (mProgressBar != null) {
            mProgressBar.setListener(listener);
        }
    }

    /**
     * Stores a new stepper configuration.
     * @param configuration New configuration.
     */
    public void setStepperConfiguration(StepperConfiguration configuration) {
        if (mProgressBar != null) {
            mProgressBar.setConfiguration(configuration);
        }
    }

    public boolean hasNextButton() {
        return mToolbar.getMenu().findItem(R.id.action_next) != null;
    }

    public void enableNextButton(boolean enable) {
        mToolbar.post(() -> {
            MenuItem item = mToolbar.getMenu().findItem(R.id.action_next);
            int color;
            if(enable) {
                color = UIStorage.getInstance().getTextTopbarColor();
            }
            else {
                color = UIStorage.getInstance().getDisabledTextTopbarColor();
            }
            View view = findViewById(R.id.action_next);
            // Cast to a TextView instance if the menu item was found
            if (view != null && view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            }
            item.setEnabled(enable);
        });
    }

    public void setObserver(Observer<Boolean> observer) {
        if(mUiFieldsObservable != null) {
            mUiFieldsObservable.subscribe(observer);
        }
    }

    public void setUiFieldsObservable(EditText field) {
        mUiFieldsObservable = getObservable(field);
    }

    private Observable<Boolean> getObservable(EditText field) {
        return RxTextView.textChanges(field)
                .map(charSequence -> (charSequence.length() > 0))
                .distinctUntilChanged();
    }

    public void setUiFieldsObservable(ArrayList<EditText> fields) {
        if(fields.size() == 2) {
            mUiFieldsObservable = getObservableForTwoFields(fields.get(0), fields.get(1));
        }
        else if (fields.size() == 3) {
            mUiFieldsObservable = getObservableForThreeFields(fields.get(0), fields.get(1), fields.get(2));
        }
    }

    private Observable<Boolean> getObservableForTwoFields(EditText first, EditText second) {
        return Observable.combineLatest(
                getObservable(first),
                getObservable(second),
                ((firstValid, secondValid) -> (firstValid && secondValid)));
    }

    private Observable<Boolean> getObservableForThreeFields(EditText first, EditText second, EditText third) {
        return Observable.combineLatest(
                getObservable(first),
                getObservable(second),
                getObservable(third),
                ((firstValid, secondValid, thirdValid) -> (firstValid && secondValid && thirdValid)));
    }
}
