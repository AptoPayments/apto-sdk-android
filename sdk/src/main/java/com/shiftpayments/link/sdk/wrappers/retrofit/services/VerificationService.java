package com.shiftpayments.link.sdk.wrappers.retrofit.services;

import com.shiftpayments.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.StartEmailVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.StartVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Verification related API calls.
 * @author Wijnand
 */
public interface VerificationService {
    /**
     * Creates a {@link Call} to start a verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.VERIFICATION_START_PATH)
    Call<StartVerificationResponseVo> startVerification(@Body StartVerificationRequestVo data);

    /**
     * Creates a {@link Call} to finish a verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.VERIFICATION_FINISH_PATH)
    Call<FinishVerificationResponseVo> completeVerification(@Path("ID") String verificationID, @Body VerificationRequestVo data);

    /**
     * Creates a {@link Call} to start email verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.VERIFICATION_START_PATH)
    Call<StartEmailVerificationResponseVo> startEmailVerification(@Body StartVerificationRequestVo data);

    /**
     * Creates a {@link Call} to get the current verification status.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.VERIFICATION_STATUS_PATH)
    Call<VerificationStatusResponseVo> getVerificationStatus(@Path("ID") String verificationID);

    /**
     * Creates a {@link Call} to restart the verification.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.VERIFICATION_RESTART_PATH)
    Call<VerificationResponseVo> restartVerification(@Path("ID") String verificationID);
}
