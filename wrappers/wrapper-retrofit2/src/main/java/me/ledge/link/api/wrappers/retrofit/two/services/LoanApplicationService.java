package me.ledge.link.api.wrappers.retrofit.two.services;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Loan application related API calls.
 * @author Wijnand
 */
public interface LoanApplicationService {

    /**
     * Creates a {@link Call} to create a loan application.
     * @param offerId The offer to apply to.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.CREATE_LOAN_APPLICATION_PATH)
    Call<LoanApplicationDetailsResponseVo> createLoanApplication(@Path("offer_id") String offerId);

    /**
     * Creates a {@link Call} to get the list of open loan applications.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.LIST_LOAN_APPLICATIONS_PATH)
    Call<LoanApplicationsListResponseVo> getLoanApplicationsList();
}