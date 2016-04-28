package me.ledge.link.sdk.ui.activities.loanapplication;

import android.content.Intent;
import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.AddDocumentsListPresenter;
import me.ledge.link.sdk.ui.views.loanapplication.AddDocumentsListView;

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
    protected AddDocumentsListPresenter createPresenter() {
        return new AddDocumentsListPresenter(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }
}
