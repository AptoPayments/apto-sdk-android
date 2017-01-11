package me.ledge.link.sdk.ui.presenters.loanapplication;

import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;

/**
 * Delegation interface for the the add documents screen.
 *
 * @author Adrian
 */
public interface AddDocumentsListDelegate {

    void showNext(AddDocumentsListModel model);
    void showPrevious(AddDocumentsListModel model);
}
