package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Shows the list of documents the lender requires for a loan application.
 * @author Wijnand
 */
public class AddDocumentsListView extends CoordinatorLayout implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener {

        /**
         * Called when one of the document cards has been clicked.
         */
        void cardClickHandler();

        /**
         * Called when the "Take photo" button has been pressed.
         */
        void photoClickHandler();

        /**
         * Called when the "Select from library" button has been pressed.
         */
        void libraryClickHandler();

        /**
         * Called when the "DONE" button has been pressed.
         */
        void doneClickHandler();
    }

    private Toolbar mToolbar;
    private LinearLayout mDocumentsList;
    private TextView mDoneButton;

    private NestedScrollView mBottomSheet;
    private TextView mPhotoButton;
    private TextView mLibraryButton;

    private ViewListener mListener;

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
        mDocumentsList = (LinearLayout) findViewById(R.id.ll_documents_list);
        mDoneButton = (TextView) findViewById(R.id.tv_done_bttn);
        mBottomSheet = (NestedScrollView) findViewById(R.id.nsv_bottom_sheet);
        mPhotoButton = (TextView) findViewById(R.id.tv_photo_bttn);
        mLibraryButton = (TextView) findViewById(R.id.tv_library_bttn);
    }

    /**
     * Sets up all callback listeners for this View.
     */
    private void setUpListeners() {
        mDoneButton.setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
        mLibraryButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_photo_bttn) {
            mListener.photoClickHandler();
        } else if (id == R.id.tv_library_bttn) {
            mListener.libraryClickHandler();
        } else if (id == R.id.tv_done_bttn) {
            mListener.doneClickHandler();
        } else {
            mListener.cardClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new reference to a {@link ViewListener} that will be invoked by this View.
     * @param listener The new {@link ViewListener} to store.
     */
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(AddDocumentModel[] data) {
        mDocumentsList.removeAllViews();

        AddDocumentCardView view;
        for (AddDocumentModel model : data) {
            view = (AddDocumentCardView) LayoutInflater.from(getContext())
                    .inflate(R.layout.cv_add_document, mDocumentsList, false);

            view.setData(model);
            view.setOnClickListener(this);
            mDocumentsList.addView(view);
        }
    }

    /**
     * @return Reference to the bottom sheet.
     */
    public View getBottomSheet() {
        return mBottomSheet;
    }
}
