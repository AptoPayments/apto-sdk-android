package me.ledge.link.sdk.ui.models.loanapplication.documents;

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
     * @return Whether one or more documents have been attached.
     */
    boolean hasDocument();

}
