package me.ledge.link.sdk.sdk.mocks.api.wrappers;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.utils.TermUnit;
import me.ledge.link.api.utils.loanapplication.LoanApplicationMethod;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.api.vos.responses.base.ListResponseVo;
import me.ledge.link.api.vos.responses.config.EmploymentStatusListResponseVo;
import me.ledge.link.api.vos.responses.config.HousingTypeListResponseVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationActionVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.api.vos.responses.offers.LenderVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.api.vos.responses.offers.OffersListVo;
import me.ledge.link.api.vos.responses.offers.TermVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;

/**
 * Mock implementation of the {@link LinkApiWrapper} interface.
 * @author Wijnand
 */
public class MockApiWrapper implements LinkApiWrapper {

    public static final String TOKEN = "bearer_token";

    private static final String LENDER_ONE_NAME = "More money";
    private static final String LENDER_ONE_LARGE_IMAGE
            = "http://cdn-media-1.lifehack.org/wp-content/files/2016/01/04125044/10-Simple-Things-You-Can-Do-To-Earn-More-Money.jpg";
    private static final long OFFER_ONE_AMOUNT = 5555;
    private static final float OFFER_ONE_INTEREST = 19.9f;
    private static final float OFFER_ONE_PAYMENT = 123.45f;
    private static final String OFFER_ONE_APPLICATION_URL = "http://www.moremoney.com/";

    private String mDeveloperKey;
    private String mDevice;
    private String mBearerToken;
    private String mEndPoint;

    /** {@inheritDoc} */
    @Override
    public void setBaseRequestData(String developerKey, String device) {
        mDeveloperKey = developerKey;
        mDevice = device;
    }

    /** {@inheritDoc} */
    @Override
    public void setBearerToken(String token) {
        mBearerToken = token;
    }

    /** {@inheritDoc} */
    @Override
    public String getApiEndPoint() {
        return mEndPoint;
    }

    /** {@inheritDoc} */
    @Override
    public void setApiEndPoint(String endPoint) {
        mEndPoint = endPoint;
    }

    /** {@inheritDoc} */
    @Override
    public LoanPurposesResponseVo getLoanPurposesList(UnauthorizedRequestVo requestData) throws ApiException {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public HousingTypeListResponseVo getHousingTypeList(UnauthorizedRequestVo requestData) throws ApiException {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public EmploymentStatusListResponseVo getEmploymentStatusList(UnauthorizedRequestVo requestData)
            throws ApiException {

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CreateUserResponseVo createUser(CreateUserRequestVo requestData) throws ApiException {
        CreateUserResponseVo response = new CreateUserResponseVo();
        response.token = TOKEN;

        return response;
    }

    /** {@inheritDoc} */
    @Override
    public UserResponseVo updateUser(CreateUserRequestVo requestData) throws ApiException {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public InitialOffersResponseVo getInitialOffers(InitialOffersRequestVo requestData) {
        OfferVo offerOne = new OfferVo();
        offerOne.id = 1;
        offerOne.loan_amount = OFFER_ONE_AMOUNT;
        offerOne.interest_rate = OFFER_ONE_INTEREST;
        offerOne.payment_amount = OFFER_ONE_PAYMENT;
        offerOne.application_method = LoanApplicationMethod.WEB;
        offerOne.application_url = OFFER_ONE_APPLICATION_URL;
        offerOne.term = new TermVo();
        offerOne.term.unit = TermUnit.WEEK;
        offerOne.term.duration = 3;
        offerOne.lender = new LenderVo();
        offerOne.lender.lender_name = LENDER_ONE_NAME;
        offerOne.lender.large_image = LENDER_ONE_LARGE_IMAGE;

        OfferVo offerTwo = new OfferVo();
        offerTwo.id = 2;

        OfferVo[] rawOffers = new OfferVo[]{ offerOne, offerTwo };

        OffersListVo offerList = new OffersListVo();
        offerList.data = rawOffers;
        offerList.page = 1;
        offerList.rows = rawOffers.length;
        offerList.total_count = rawOffers.length;
        offerList.has_more = false;

        InitialOffersResponseVo response = new InitialOffersResponseVo();
        response.offer_request_id = 69;
        response.offers = offerList;

        return response;
    }

    /** {@inheritDoc} */
    @Override
    public OffersListVo getMoreOffers(long offerRequestId, ListRequestVo requestData) throws ApiException {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationDetailsResponseVo createLoanApplication(long offerId) {
        LoanApplicationDetailsResponseVo application = new LoanApplicationDetailsResponseVo();
        application.status = "";
        application.required_actions = new ListResponseVo<>();
        application.required_actions.data = new LoanApplicationActionVo[] { new LoanApplicationActionVo() };

        return application;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationsListResponseVo getLoanApplicationsList(ListRequestVo requestData) {
        LoanApplicationsListResponseVo response = new LoanApplicationsListResponseVo();
        response.data = new LoanApplicationDetailsResponseVo[] { createLoanApplication(-1) };

        return response;
    }
}
