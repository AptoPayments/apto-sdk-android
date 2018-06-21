package com.shiftpayments.link.sdk.ui.storages;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.datapoints.CreditScore;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Housing;
import com.shiftpayments.link.sdk.api.vos.datapoints.IncomeSource;
import com.shiftpayments.link.sdk.api.vos.responses.config.CreditScoreVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.HousingTypeVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.IncomeTypeVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.SalaryFrequencyVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;

/**
 * Stores user related data.
 * @author Wijnand
 */
public class UserStorage {

    private DataPointList mUserDataPoints;
    private String mBearerToken;
    private String mCoinbaseAccessToken;
    private String mCoinbaseRefreshToken;
    private String mFirebaseToken;
    private String mSelectedFinancialAccountId;

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
        if(userData!=null) {
            Housing housing = (Housing) userData.getUniqueDataPoint(DataPointVo.DataPointType.Housing, null);
            if(housing != null && !isHousingTypeValid(housing)) {
                userData.removeDataPointsOf(DataPointVo.DataPointType.Housing);
            }
            IncomeSource incomeSource = (IncomeSource) userData.getUniqueDataPoint(DataPointVo.DataPointType.IncomeSource, null);
            if(incomeSource != null && (!isIncomeTypeValid(incomeSource) || !isSalaryFrequencyValid(incomeSource))) {
                userData.removeDataPointsOf(DataPointVo.DataPointType.IncomeSource);
            }
            CreditScore creditScore = (CreditScore) userData.getUniqueDataPoint(DataPointVo.DataPointType.CreditScore, null);
            if(creditScore != null && !isCreditScoreValid(creditScore)) {
                userData.removeDataPointsOf(DataPointVo.DataPointType.CreditScore);
            }
        }
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
        registerFirebaseToken();
    }

    public boolean hasBearerToken() {
        return !TextUtils.isEmpty(mBearerToken);
    }

    private boolean isHousingTypeValid(Housing housing) {
        HousingTypeVo[] validHousingTypes = UIStorage.getInstance().getContextConfig().housingTypeOpts.data;
        boolean isValid = false;
        for (HousingTypeVo type : validHousingTypes) {
            if(housing.housingType.getKey() == type.housing_type_id) {
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isIncomeTypeValid(IncomeSource incomeSource) {
        IncomeTypeVo[] validIncomeTypes = UIStorage.getInstance().getContextConfig().incomeTypeOpts.data;
        boolean isValid = false;
        for (IncomeTypeVo type : validIncomeTypes) {
            if(incomeSource.incomeType.getKey() == type.income_type_id) {
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isSalaryFrequencyValid(IncomeSource incomeSource) {
        SalaryFrequencyVo[] validSalaryFrequencies = UIStorage.getInstance().getContextConfig().salaryFrequencyOpts.data;
        boolean isValid = false;
        for (SalaryFrequencyVo type : validSalaryFrequencies) {
            if(incomeSource.salaryFrequency.getKey() == type.salary_frequency_id) {
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isCreditScoreValid(CreditScore creditScore) {
        CreditScoreVo[] validCreditScores = UIStorage.getInstance().getContextConfig().creditScoreOpts.data;
        boolean isValid = false;
        for (CreditScoreVo type : validCreditScores) {
            if(creditScore.creditScoreRange == type.creditScoreId) {
                isValid = true;
            }
        }
        return isValid;
    }

    public String getCoinbaseAccessToken() {
        return mCoinbaseAccessToken;
    }

    public void setCoinbaseAccessToken(String coinbaseAccessToken) {
        this.mCoinbaseAccessToken = coinbaseAccessToken;
    }

    public String getCoinbaseRefreshToken() {
        return mCoinbaseRefreshToken;
    }

    public void setCoinbaseRefreshToken(String coinbaseRefreshToken) {
        this.mCoinbaseRefreshToken = coinbaseRefreshToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.mFirebaseToken = firebaseToken;
        registerFirebaseToken();
    }

    public String getSelectedFinancialAccountId() {
        return mSelectedFinancialAccountId;
    }

    public void setSelectedFinancialAccountId(String selectedFinancialAccountId) {
        mSelectedFinancialAccountId = selectedFinancialAccountId;
    }

    public boolean hasUserData() {
        return mUserDataPoints != null && !mUserDataPoints.getDataPoints().isEmpty();
    }

    private void registerFirebaseToken() {
        if(isValid(mBearerToken) && isValid(mFirebaseToken)) {
            ShiftLinkSdk.registerPushNotificationToken(mFirebaseToken);
        }
    }

    private boolean isValid(String token) {
        return token != null && !token.isEmpty();
    }
}
