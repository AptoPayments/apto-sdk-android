package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 17/01/2017.
 */

public class FinancialAccountVo extends DataPointVo {
    public enum FinancialAccountType {
        Bank,
        Card,
        VirtualCard
    }

    @SerializedName("account_id")
    public String mAccountId;
    @SerializedName("account_type")
    public FinancialAccountType mAccountType;

    public FinancialAccountVo(String accountId, FinancialAccountType type, boolean verified) {
        super(DataPointType.FinancialAccount, verified, false);
        mAccountId = accountId;
        mAccountType = type;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        if(isAccountIdPresent()) {
            gsonObject.addProperty("account_id", mAccountId);
        }
        gsonObject.addProperty("account_type", mAccountType.name());
        return gsonObject;
    }

    private boolean isAccountIdPresent() {
        return mAccountId != null && !mAccountId.equals("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FinancialAccountVo that = (FinancialAccountVo) o;

        return (mAccountId != null ? mAccountId.equals(that.mAccountId) : that.mAccountId == null) && mAccountType == that.mAccountType;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode() + (mAccountId != null ? mAccountId.hashCode() : 0);
        result = 31 * result + (mAccountType != null ? mAccountType.hashCode() : 0);
        return result;
    }
}
