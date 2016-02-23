package me.ledge.link.sdk.sdk.mocks.api.wrappers;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;

/**
 * Mock implementation of the {@link LinkApiWrapper} interface.
 * @author Wijnand
 */
public class MockApiWrapper implements LinkApiWrapper {

    public static final String TOKEN = "bearer_token";

    private long mDeveloperId;
    private String mDevice;
    private String mEndPoint;

    /** {@inheritDoc} */
    @Override
    public void setBaseRequestData(long developerId, String device) {
        mDeveloperId = developerId;
        mDevice = device;
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
    public CreateUserResponseVo createUser(CreateUserRequestVo requestData) throws ApiException {
        CreateUserResponseVo response = new CreateUserResponseVo();
        response.token = TOKEN;

        return response;
    }
}
