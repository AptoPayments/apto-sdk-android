package com.shiftpayments.link.sdk.ui.activities.loanapplication;

import android.content.Intent;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.AddDocumentsListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.AddDocumentsListPresenter;
import com.shiftpayments.link.sdk.ui.views.loanapplication.AddDocumentsListView;

/**
 * Wires up the MVP pattern for the screen that shows the document upload list.
 * @author Wijnand
 */
public class AddDocumentsListActivity
        extends MvpActivity<AddDocumentsListModel, AddDocumentsListView, AddDocumentsListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddDocumentsListView createView() {
        return (AddDocumentsListView) View.inflate(this, R.layout.act_add_documents, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddDocumentsListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddDocumentsListDelegate) {
            return new AddDocumentsListPresenter(this, (AddDocumentsListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddDocumentsListDelegate!");
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }
}
