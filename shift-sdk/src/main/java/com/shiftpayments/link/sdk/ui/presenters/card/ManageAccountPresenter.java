package com.shiftpayments.link.sdk.ui.presenters.card;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shiftpayments.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shiftpayments.link.sdk.ui.models.card.ManageAccountModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.SendEmailUtil;
import com.shiftpayments.link.sdk.ui.views.card.ManageAccountView;

/**
 * Concrete {@link Presenter} for the manage account screen.
 * @author Adrian
 */
public class ManageAccountPresenter
        extends BasePresenter<ManageAccountModel, ManageAccountView>
        implements Presenter<ManageAccountModel, ManageAccountView>, ManageAccountView.ViewListener {

    private FundingSourcesListRecyclerAdapter mAdapter;
    private ManageAccountActivity mActivity;
    private ManageAccountDelegate mDelegate;

    public ManageAccountPresenter(ManageAccountActivity activity, ManageAccountDelegate delegate) {
        mActivity = activity;
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ManageAccountView view) {
        super.attachView(view);
        view.setViewListener(this);
        mActivity.setSupportActionBar(mView.getToolbar());
        mActivity.getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.account_management_title));
    }

    @Override
    public ManageAccountModel createModel() {
        return new ManageAccountModel();
    }

    @Override
    public void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(mActivity.getString(R.string.account_management_dialog_message))
                .setTitle(mActivity.getString(R.string.sign_out))
                .setNegativeButton("NO", (dialog, id) -> dialog.dismiss())
                .setPositiveButton("YES", (dialog, id) -> {
                    mResponseHandler.unsubscribe(this);
                    mActivity.finish();
                    mDelegate.onSignOut();
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(UIStorage.getInstance().getTextPrimaryColor());
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(UIStorage.getInstance().getPrimaryColor());
        });
        dialog.show();
    }

    @Override
    public void contactSupport() {
        new SendEmailUtil(UIStorage.getInstance().getContextConfig().supportEmailAddress).execute(mActivity);
    }

    @Override
    public void faqClickHandler() {
        // TODO: URL should be read from config
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.shiftpayments.com/faq"));
        mActivity.startActivity(browserIntent);
    }

    @Override
    public void onBack() {
        mActivity.onBackPressed();
    }
}
