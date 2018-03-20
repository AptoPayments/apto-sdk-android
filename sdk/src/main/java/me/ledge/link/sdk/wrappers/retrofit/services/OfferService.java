package me.ledge.link.sdk.wrappers.retrofit.services;

import me.ledge.link.sdk.api.vos.requests.base.ListRequestVo;
import me.ledge.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.sdk.api.vos.responses.offers.OffersListVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
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
    @POST(LinkApiWrapper.INITIAL_OFFERS_PATH)
    Call<InitialOffersResponseVo> getInitialOffers(@Body InitialOffersRequestVo data);

    /**
     * Creates a {@link Call} to get more loan offers.
     * @param offerRequestId Offer request ID.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.MORE_OFFERS_PATH)
    Call<OffersListVo> getMoreOffers(@Path("offer_request_id") String offerRequestId,
            @Body ListRequestVo data);
}
