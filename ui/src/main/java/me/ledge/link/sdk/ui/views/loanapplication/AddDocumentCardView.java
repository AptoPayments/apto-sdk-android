package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;
import me.ledge.link.sdk.ui.utils.ResourceUtil;

/**
 * Displays a single request to add a document for a loan application.
 * @author Wijnand
 */
public class AddDocumentCardView extends CardView {

    private ImageView mIconView;
    private TextView mTitleField;
    private TextView mDescriptionField;
    private AddDocumentModel mData;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public AddDocumentCardView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public AddDocumentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mIconView = (ImageView) findViewById(R.id.iv_icon);
        mTitleField = (TextView) findViewById(R.id.tv_title);
        mDescriptionField = (TextView) findViewById(R.id.tv_description);
    }

    /**
     * Updates the card's icon background and tint based on whether a file has been attached.
     * @param data Latest data.
     */
    private void updateIcon(AddDocumentModel data) {
        mIconView.setImageResource(data.getIconResourceId());

        ResourceUtil util = new ResourceUtil();
        int backgroundId = util.getResourceIdForAttribute(
                getContext(), R.attr.llsdk_addDocuments_iconBackgroundUncheckedColor);
        int tintId = util.getResourceIdForAttribute(getContext(), R.attr.llsdk_addDocuments_iconUncheckedColor);

        if (data.hasDocument()) {
            backgroundId = util.getResourceIdForAttribute(
                    getContext(), R.attr.llsdk_addDocuments_iconBackgroundCheckedColor);
            tintId = util.getResourceIdForAttribute(getContext(), R.attr.llsdk_addDocuments_iconCheckedColor);
        }

        mIconView.setBackgroundColor(getResources().getColor(backgroundId));
        mIconView.setColorFilter(getResources().getColor(tintId));
    }

    /**
     * Updates the card's description based on whether a file has been attached.
     * @param data Latest data.
     */
    private void updateDescription(AddDocumentModel data) {
        String description = data.getDescription();
        mDescriptionField.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (data.hasDocument()) {
            description = getResources().getString(R.string.add_documents_description_checked);
            mDescriptionField.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_help_outline_black_24dp, 0, 0, 0);
        }

        mDescriptionField.setText(description);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(AddDocumentModel data) {
        if (data == null) {
            return;
        }

        mData = data;
        updateIcon(data);
        mTitleField.setText(data.getTitleResourceId());
        updateDescription(data);
    }

    /**
     * @return The data this View is showing.
     */
    public AddDocumentModel getData() {
        return mData;
    }
}
