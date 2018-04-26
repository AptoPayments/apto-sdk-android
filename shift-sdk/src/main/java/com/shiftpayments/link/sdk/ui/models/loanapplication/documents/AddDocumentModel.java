package com.shiftpayments.link.sdk.ui.models.loanapplication.documents;

import com.shiftpayments.link.sdk.ui.vos.DocumentVo;

import java.util.ArrayList;

/**
 * Information about a document that needs to be added to a loan application.
 * @author Wijnand
 */
public interface AddDocumentModel {

    /**
     * @return Icon resource ID.
     */
    int getIconResourceId();

    /**
     * @return Title resource ID.
     */
    int getTitleResourceId();

    /**
     * @return Description.
     */
    String getDescription();

    /**
     * Adds a document to the list.
     * @param document The document to add.
     */
    void addDocument(DocumentVo document);

    /**
     * @return List of documents.
     */
    ArrayList<DocumentVo> getDocumentList();

    /**
     * @return Whether one or more documents have been attached.
     */
    boolean hasDocument();

}
