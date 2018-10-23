package com.shiftpayments.link.sdk.wrappers.retrofit.services;

import com.shiftpayments.link.sdk.api.vos.requests.cardapplication.CreateCardApplicationRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.CardApplicationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Card application related API calls.
 * @author Adrian
 */
public interface CardApplicationService {

    /** Creates a {@link Call} to create the card application
     * @param request Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.CREATE_CARD_APPLICATION_PATH)
    Call<CardApplicationResponseVo> createCardApplication(@Body CreateCardApplicationRequestVo request);

    /**
     * Creates a {@link Call} to get the application status.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.CARD_APPLICATION_STATUS_PATH)
    Call<CardApplicationResponseVo> getApplicationStatus(@Path("application_id") String applicationId);

    /**
     * Delete a card application
     * @param applicationId of the account to delete
     * @return
     */
    @DELETE(ShiftApiWrapper.CARD_APPLICATION_PATH)
    Call<ApiEmptyResponseVo> deleteApplicationAccount(@Path("application_id") String applicationId);
}