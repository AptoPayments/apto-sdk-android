package me.ledge.link.sdk.ui.views.custodianselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.custodianselector.AddCustodianModel;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.DisplayErrorMessage;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;


/**
 * Created by adrian on 17/01/2017.
 */

public class AddCustodianListView extends CoordinatorLayout
        implements ViewWithToolbar, View.OnClickListener, ViewWithIndeterminateLoading, DisplayErrorMessage {

    private Toolbar mToolbar;
    private LinearLayout mAccountsList;

    private AddCustodianListView.ViewListener mListener;
    private AddCustodianModel[] mData;
    private LoadingView mLoadingView;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public AddCustodianListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public AddCustodianListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mAccountsList = (LinearLayout) findViewById(R.id.ll_accounts_list);
        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        AddCustodianModel model = ((AddCustodianCardView) view).getData();
        mListener.accountClickHandler(model);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setColors();
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    @Override
    public void displayErrorMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public interface ViewListener {
        /**
         * Called when one of the document cards has been clicked.
         * @param model Card data.
         */
        void accountClickHandler(AddCustodianModel model);
    }

    /**
     * Stores a new reference to a {@link AddCustodianListView.ViewListener} that will be invoked by this View.
     * @param listener The new {@link AddCustodianListView.ViewListener} to store.
     */
    public void setViewListener(AddCustodianListView.ViewListener listener) {
        mListener = listener;
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(AddCustodianModel[] data) {
        mData = data;
        if(data == null) {
            return;
        }

        mAccountsList.removeAllViews();

        AddCustodianCardView view;
        for (AddCustodianModel model : data) {
            view = (AddCustodianCardView) LayoutInflater.from(getContext())
                    .inflate(R.layout.cv_add_custodian, mAccountsList, false);

            view.setData(model);
            view.setOnClickListener(this);
            mAccountsList.addView(view);
        }
    }
}
