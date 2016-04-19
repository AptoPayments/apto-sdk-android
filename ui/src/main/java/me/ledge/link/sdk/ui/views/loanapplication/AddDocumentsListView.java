package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Shows the list of documents the lender requires for a loan application.
 * @author Wijnand
 */
public class AddDocumentsListView extends RelativeLayout implements ViewWithToolbar {

    private Toolbar mToolbar;
    private LinearLayout mDocucmentsList;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public AddDocumentsListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public AddDocumentsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        mDocucmentsList = (LinearLayout) findViewById(R.id.ll_documents_list);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(AddDocumentModel[] data) {
        mDocucmentsList.removeAllViews();

        AddDocumentCardView view;
        for (AddDocumentModel model : data) {
            view = (AddDocumentCardView) LayoutInflater.from(getContext())
                    .inflate(R.layout.cv_add_document, mDocucmentsList, false);

            view.setData(model);
            mDocucmentsList.addView(view);
        }
    }
}
