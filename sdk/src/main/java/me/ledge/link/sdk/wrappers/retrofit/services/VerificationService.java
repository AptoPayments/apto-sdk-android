package me.ledge.link.sdk.wrappers.retrofit.services;

import me.ledge.link.api.vos.requests.verifications.StartVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.api.vos.responses.verifications.FinishVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartEmailVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
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
    @POST(LinkApiWrapper.VERIFICATION_START_PATH)
    Call<StartVerificationResponseVo> startVerification(@Body StartVerificationRequestVo data);

    /**
     * Creates a {@link Call} to finish a verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.VERIFICATION_FINISH_PATH)
    Call<FinishVerificationResponseVo> completeVerification(@Path("ID") String verificationID, @Body VerificationRequestVo data);

    /**
     * Creates a {@link Call} to start email verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.VERIFICATION_START_PATH)
    Call<StartEmailVerificationResponseVo> startEmailVerification(@Body StartVerificationRequestVo data);

    /**
     * Creates a {@link Call} to get the current verification status.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.VERIFICATION_STATUS_PATH)
    Call<VerificationStatusResponseVo> getVerificationStatus(@Path("ID") String verificationID);

    /**
     * Creates a {@link Call} to restart the verification.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.VERIFICATION_RESTART_PATH)
    Call<VerificationResponseVo> restartVerification(@Path("ID") String verificationID);
}
