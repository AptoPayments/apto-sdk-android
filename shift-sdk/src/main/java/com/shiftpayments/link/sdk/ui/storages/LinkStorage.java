package com.shiftpayments.link.sdk.ui.storages;

import com.shiftpayments.link.sdk.ui.vos.LoanDataVo;

/**
 * Stores loan related data.
 * @author Wijnand
 */
public class LinkStorage {

    private LoanDataVo mLoanData;

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
}
