package com.shift.link.sdk.wrappers.retrofit.utils;

import com.shift.link.sdk.api.vos.responses.errors.ErrorResponseVo;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Retrofit 2 error response utility.
 * @author Wijnand
 */
public class ErrorUtil {

    private final Retrofit mRetrofit;

    /**
     * Creates a new {@link ErrorUtil} instance.
     * @param retrofit References to the {@link Retrofit} instance that is being used.
     */
    public ErrorUtil(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    /**
     * @param response Retrofit response object.
     * @return The {@link ErrorResponseVo} parsed from the Retrofit {@link Response}.
     */
    public ErrorResponseVo parseError(Response response) {
        Converter<ResponseBody, ErrorResponseVo> converter = mRetrofit
                .responseBodyConverter(ErrorResponseVo.class, new Annotation[0]);

        ErrorResponseVo error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException ioe) {
            error = new ErrorResponseVo();
        }

        return error;
    }
}
