package me.ledge.link.sdk.ui.storages;

import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Stores user related data.
 * @author Wijnand
 */
public class UserStorage {

    private UserDataVo mUserData;
    private String mBearerToken;

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
    public UserDataVo getUserData() {
        return mUserData;
    }

    /**
     * Stores new user data.
     * @param userData New user data.
     */
    public void setUserData(UserDataVo userData) {
        mUserData = userData;
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
}
