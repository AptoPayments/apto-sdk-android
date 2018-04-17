package com.shift.link.sdk.wrappers.retrofit.services;

import com.shift.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.LinkConfigResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Configuration related API calls.
 * @author wijnand
 */
public interface ConfigService {
    /**
     * Creates a {@link Call} to get the loan purposes list.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.LINK_CONFIG_PATH)
    Call<LinkConfigResponseVo> getLoanPurposesList();

    /**
     * Creates a {@link Call} to get the config.
     * It contains the housing types, income types and salary frequencies.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.CONFIG_PATH)
    Call<ContextConfigResponseVo> getUserConfig();
}
