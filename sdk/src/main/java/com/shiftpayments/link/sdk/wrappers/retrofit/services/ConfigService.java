package com.shiftpayments.link.sdk.wrappers.retrofit.services;

import com.shiftpayments.link.sdk.api.vos.responses.cardconfig.CardConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LinkConfigResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Configuration related API calls.
 * @author wijnand
 */
public interface ConfigService {
    /**
     * Creates a {@link Call} to get the link config.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.LINK_CONFIG_PATH)
    Call<LinkConfigResponseVo> getLinkConfig();

    /**
     * Creates a {@link Call} to get the config.
     * It contains the housing types, income types and salary frequencies.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.CONFIG_PATH)
    Call<ContextConfigResponseVo> getUserConfig();

    /**
     * Creates a {@link Call} to get the card config.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.CARD_CONFIG_PATH)
    Call<CardConfigResponseVo> getCardConfig();
}
