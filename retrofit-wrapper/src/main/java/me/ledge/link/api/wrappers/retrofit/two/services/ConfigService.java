package me.ledge.link.api.wrappers.retrofit.two.services;

import me.ledge.link.api.vos.responses.config.ContextConfigResponseVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
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
    @GET(LinkApiWrapper.LINK_CONFIG_PATH)
    Call<LinkConfigResponseVo> getLoanPurposesList();

    /**
     * Creates a {@link Call} to get the config.
     * It contains the housing types, income types and salary frequencies.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.CONFIG_PATH)
    Call<ContextConfigResponseVo> getUserConfig();
}
