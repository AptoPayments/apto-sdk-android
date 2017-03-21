package me.ledge.link.sdk.ui.storages;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.responses.config.RequiredDataPointsListResponseVo;

/**
 * Stores user related data.
 * @author Wijnand
 */
public class UserStorage {

    private DataPointList mUserDataPoints;
    private String mBearerToken;
    private RequiredDataPointsListResponseVo mRequiredData;

    private static UserStorage mInstance;

    /**
     * Creates a new {@link UserStorage} instance.
     */
    private UserStorage() { }

    /**
     * @return The single instance of this class.
     */
    public static synchronized UserStorage getInstance() {
        if (mInstance == null) {
            mInstance = new UserStorage();
        }

        return mInstance;
    }

    /**
     * @return User data.
     */
    public DataPointList getUserData() {
        return mUserDataPoints;
    }

    /**
     * Stores new user data.
     * @param userData New user data.
     */
    public void setUserData(DataPointList userData) {
        mUserDataPoints = userData;
    }

    /**
     * @return Bearer token.
     */
    public String getBearerToken() {
        return mBearerToken;
    }

    /**
     * Stores a new bearer token.
     * @param bearerToken New bearer token.
     */
    public void setBearerToken(String bearerToken) {
        mBearerToken = bearerToken;
    }

    public RequiredDataPointsListResponseVo getRequiredData() {
        return mRequiredData;
    }

    public void setRequiredData(RequiredDataPointsListResponseVo requiredData) {
        mRequiredData = requiredData;
    }
}
