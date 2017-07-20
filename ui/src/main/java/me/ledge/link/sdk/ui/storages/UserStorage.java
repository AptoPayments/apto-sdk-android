package me.ledge.link.sdk.ui.storages;

import me.ledge.link.api.vos.datapoints.CreditScore;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Housing;
import me.ledge.link.api.vos.datapoints.IncomeSource;
import me.ledge.link.api.vos.responses.config.CreditScoreVo;
import me.ledge.link.api.vos.responses.config.HousingTypeVo;
import me.ledge.link.api.vos.responses.config.IncomeTypeVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointsListResponseVo;
import me.ledge.link.api.vos.responses.config.SalaryFrequencyVo;

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
}
