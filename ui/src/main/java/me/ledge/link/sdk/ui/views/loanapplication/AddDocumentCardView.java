package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;

/**
 * Displays a single request to add a document for a loan application.
 * @author Wijnand
 */
public class AddDocumentCardView extends CardView {

    private ImageView mIconView;
    private TextView mTitleField;
    private TextView mDescriptionField;

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

        mIconView.setImageResource(data.getIconResourceId());
        mTitleField.setText(data.getTitleResourceId());
        mDescriptionField.setText(data.getDescription());
    }
}
