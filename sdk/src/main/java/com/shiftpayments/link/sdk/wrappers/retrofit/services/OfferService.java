package com.shiftpayments.link.sdk.wrappers.retrofit.services;

import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.OffersListVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Offers related API calls.
 * @author wijnand
 */
public interface OfferService {

    /**
     * Creates a {@link Call} to get the initial list of loan offers.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.INITIAL_OFFERS_PATH)
    Call<InitialOffersResponseVo> getInitialOffers(@Body InitialOffersRequestVo data);

    /**
     * Creates a {@link Call} to get more loan offers.
     * @param offerRequestId Offer request ID.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.MORE_OFFERS_PATH)
    Call<OffersListVo> getMoreOffers(@Path("offer_request_id") String offerRequestId,
            @Body ListRequestVo data);
}
