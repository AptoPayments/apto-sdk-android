package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import me.ledge.link.sdk.ui.R;

/**
 * Shows the list of documents the lender requires for a loan application.
 * @author Wijnand
 */
public class AddDocumentsView extends ScrollView {

    private LinearLayout mDocucmentsList;

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     */
    public AddDocumentsView(Context context) {
        super(context);
    }

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs See {@link ScrollView#ScrollView}.
     */
    public AddDocumentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mDocucmentsList = (LinearLayout) findViewById(R.id.ll_documents_list);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }
}
