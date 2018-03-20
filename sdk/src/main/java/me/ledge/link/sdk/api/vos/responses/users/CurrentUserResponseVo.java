package me.ledge.link.sdk.api.vos.responses.users;

/**
 * Current user data in response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class CurrentUserResponseVo {

    /**
     * Type.
     */
    public String type;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("user_data")
    public UserDataListResponseVo userData;
}