package me.ledge.link.sdk.sdk.storages;

import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;

/**
 * Delegation interface for accessing the loan purposes stored in the Config Storage.
 *
 * @author Adrian
 */
public interface LoanPurposesDelegate {

    void loanPurposesListRetrieved(LoanPurposesResponseVo loanPurposesList);

    void errorReceived(String error);
}
