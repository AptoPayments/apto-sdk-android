package me.ledge.link.sdk.wrappers.retrofit.services;

import com.google.gson.JsonObject;

import me.ledge.link.api.vos.requests.users.DeleteUserRequestVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.LoginUserResponseVo;
import me.ledge.link.api.vos.responses.users.CurrentUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * User related API calls.
 * @author Wijnand
 */
public interface UserService {
    /**
     * Creates a {@link Call} to create a new user.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.CREATE_USER_PATH)
    Call<CreateUserResponseVo> createUser(@Body JsonObject data);

    /**
     * Creates a {@link Call} to update an existing user.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @PUT(LinkApiWrapper.UPDATE_USER_PATH)
    Call<UserResponseVo> updateUser(@Body JsonObject data);

    /**
     * Creates a {@link Call} to login an existing user.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.LOGIN_USER_PATH)
    Call<LoginUserResponseVo> loginUser(@Body LoginRequestVo data);

    /**
     * Creates a {@link Call} to get the current user info.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.GET_CURRENT_USER_PATH)
    Call<CurrentUserResponseVo> getCurrentUser();

    /**
     * Creates a {@link Call} to login an existing user.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.DELETE_USER_PATH)
    Call<Void> deleteUser(@Body DeleteUserRequestVo data);
}
