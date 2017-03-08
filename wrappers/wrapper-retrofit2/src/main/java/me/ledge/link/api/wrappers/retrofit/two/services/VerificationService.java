package me.ledge.link.api.wrappers.retrofit.two.services;

import me.ledge.link.api.vos.requests.verifications.EmailVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.PhoneVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.api.vos.responses.verifications.FinishPhoneVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartEmailVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartPhoneVerificationResponseVo;
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
     * Creates a {@link Call} to start phone verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.VERIFICATION_PHONE_PATH)
    Call<StartPhoneVerificationResponseVo> startPhoneVerification(@Body PhoneVerificationRequestVo data);

    /**
     * Creates a {@link Call} to finish phone verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.VERIFICATION_FINISH_PATH)
    Call<FinishPhoneVerificationResponseVo> completePhoneVerification(@Body VerificationRequestVo data);

    /**
     * Creates a {@link Call} to start email verification.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.VERIFICATION_EMAIL_PATH)
    Call<StartEmailVerificationResponseVo> startEmailVerification(@Body EmailVerificationRequestVo data);

    /**
     * Creates a {@link Call} to get the current verification status.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.VERIFICATION_STATUS_PATH)
    Call<VerificationStatusResponseVo> getVerificationStatus(@Path("ID") String verificationID);
}
