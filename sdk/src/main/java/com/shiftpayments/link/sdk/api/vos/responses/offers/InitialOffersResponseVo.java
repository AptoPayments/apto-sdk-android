package com.shiftpayments.link.sdk.api.vos.responses.offers;

/**
 * Initial offers list API response.
 * @author wijnand
 */
public class InitialOffersResponseVo {

    /**
     * The loan offers.
     */
    public OffersListVo offers;

    /**
     * Offer request ID. Use with any calls to get more offers.
     */
    public String offer_request_id;

}
