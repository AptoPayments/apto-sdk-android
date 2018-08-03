package com.shiftpayments.link.sdk.ui.presenters.loanapplication;

import com.shiftpayments.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;

/**
 * Delegation interface for the the add documents screen.
 *
 * @author Adrian
 */
public interface AddDocumentsListDelegate {

    void addDocumentsListShowNext(AddDocumentsListModel model);
    void addDocumentsListShowPrevious(AddDocumentsListModel model);
}
