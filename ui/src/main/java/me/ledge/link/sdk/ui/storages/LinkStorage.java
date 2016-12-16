package me.ledge.link.sdk.ui.storages;

import me.ledge.link.sdk.ui.vos.LoanDataVo;

/**
 * Stores loan related data.
 * @author Wijnand
 */
public class LinkStorage {

    private LoanDataVo mLoanData;
    private String mBearerToken;

    private static LinkStorage mInstance;

    /**
     * Creates a new {@link LinkStorage} instance.
     */
    private LinkStorage() { }

    /**
     * @return The single instance of this class.
     */
    public static synchronized LinkStorage getInstance() {
        if (mInstance == null) {
            mInstance = new LinkStorage();
        }

        return mInstance;
    }

    /**
     * @return User data.
     */
    public LoanDataVo getLoanData() {
        return mLoanData;
    }

    /**
     * Stores new user data.
     * @param loanData New user data.
     */
    public void setLoanData(LoanDataVo loanData) {
        mLoanData = loanData;
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
