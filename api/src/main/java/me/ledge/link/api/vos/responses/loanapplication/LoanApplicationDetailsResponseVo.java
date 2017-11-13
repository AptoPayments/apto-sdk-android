package me.ledge.link.api.vos.responses.loanapplication;

import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.api.vos.responses.workflow.ActionVo;

/**
 * Loan application details API response object.
 * @author Wijnand
 */
public class LoanApplicationDetailsResponseVo {

    /**
     * Loan application ID.
     */
    public String id;

    /**
     * Loan application status.
     * @see me.ledge.link.api.utils.loanapplication.LoanApplicationStatus
     */
    public String status;

    /**
     * Loan application creation time.<br />
     * Time past since Unix epoch.
     */
    public float create_time;

    /**
     * The loan offer.
     */
    public OfferVo offer;

    /**
     * Status message.
     */
    public String status_message;

    public ActionVo next_action;

}
