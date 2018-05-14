package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 20/03/2018.
 */

public class TransactionVo implements Parcelable {

    public static final Parcelable.Creator<TransactionVo> CREATOR = new Parcelable.Creator<TransactionVo>() {
        public TransactionVo createFromParcel(Parcel in) {
            return new TransactionVo(in);
        }

        public TransactionVo[] newArray(int size) {
            return new TransactionVo[size];
        }
    };

    @SerializedName("transaction_type")
    public TransactionType type;

    public String id;

    @SerializedName("external_id")
    public String externalId;

    @SerializedName("authorized")
    public boolean isAuthorized;

    @SerializedName("created_at")
    public String creationTime;

    public String description;

    public MerchantVo merchant;

    public StoreVo store;

    public String state;

    @SerializedName("local_amount")
    public MoneyVo localAmount;

    @SerializedName("cashback_amount")
    public MoneyVo cashBackAmount;

    @SerializedName("ecommerce")
    public boolean isECommerce;

    @SerializedName("international")
    public boolean isInternational;

    @SerializedName("card_present")
    public boolean isCardPresent;

    @SerializedName("emv")
    public boolean isEmv;

    @SerializedName("network")
    public String network;

    @SerializedName("decline_reason")
    public String declineReason;

    @SerializedName("hold_amount")
    public MoneyVo holdAmount;

    @SerializedName("billing_amount")
    public MoneyVo billingAmount;

    @SerializedName("fee_amount")
    public MoneyVo fee;

    @SerializedName("native_balance")
    public MoneyVo nativeBalance;

    @SerializedName("settlement")
    public SettlementVo settlement;

    @SerializedName("last_message")
    public String lastMessage;

    @SerializedName("adjustments")
    public AdjustmentListResponseVo adjustmentsList;

    public TransactionVo(Parcel in) {
        id = in.readString();
        externalId = in.readString();
        type = TransactionType.valueOf(in.readString());
        isAuthorized = in.readInt() == 1;
        creationTime = in.readString();
        description = in.readString();
        merchant = in.readParcelable(MerchantVo.class.getClassLoader());
        store = in.readParcelable(StoreVo.class.getClassLoader());
        state = in.readString();
        localAmount = in.readParcelable(MoneyVo.class.getClassLoader());
        cashBackAmount = in.readParcelable(MoneyVo.class.getClassLoader());
        isECommerce = in.readInt() == 1;
        isInternational = in.readInt() == 1;
        isCardPresent = in.readInt() == 1;
        isEmv = in.readInt() == 1;
        network = in.readString();
        declineReason = in.readString();
        holdAmount = in.readParcelable(MoneyVo.class.getClassLoader());
        billingAmount = in.readParcelable(MoneyVo.class.getClassLoader());
        fee = in.readParcelable(MoneyVo.class.getClassLoader());
        nativeBalance = in.readParcelable(MoneyVo.class.getClassLoader());
        settlement = in.readParcelable(SettlementVo.class.getClassLoader());
        lastMessage = in.readString();
        adjustmentsList = in.readParcelable(AdjustmentListResponseVo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(externalId);
        parcel.writeString(type.name());
        parcel.writeInt(isAuthorized ? 1 : 0);
        parcel.writeString(creationTime);
        parcel.writeString(description);
        parcel.writeParcelable(merchant, flags);
        parcel.writeParcelable(store, flags);
        parcel.writeString(state);
        parcel.writeParcelable(localAmount, flags);
        parcel.writeParcelable(cashBackAmount, flags);
        parcel.writeInt(isECommerce ? 1 : 0);
        parcel.writeInt(isInternational ? 1 : 0);
        parcel.writeInt(isCardPresent ? 1 : 0);
        parcel.writeInt(isEmv ? 1 : 0);
        parcel.writeString(network);
        parcel.writeString(declineReason);
        parcel.writeParcelable(holdAmount, flags);
        parcel.writeParcelable(billingAmount, flags);
        parcel.writeParcelable(fee, flags);
        parcel.writeParcelable(nativeBalance, flags);
        parcel.writeParcelable(settlement, flags);
        parcel.writeString(lastMessage);
        parcel.writeParcelable(adjustmentsList, flags);
    }

    public enum TransactionType {
        @SerializedName("sent")
        SENT,
        @SerializedName("deposit")
        DEPOSIT,
        @SerializedName("atm_withdrawal")
        ATM_WITHDRAWAL,
        @SerializedName("withdrawal")
        WITHDRAWAL,
        @SerializedName("settlement")
        SETTLEMENT,
        @SerializedName("pin_purchase")
        PIN_PURCHASE,
        @SerializedName("fee")
        FEE,
        @SerializedName("refund")
        REFUND,
        @SerializedName("pending")
        PENDING,
        @SerializedName("decline")
        DECLINE,
        @SerializedName("received")
        RECEIVED,
        @SerializedName("reversal")
        REVERSAL,
        @SerializedName("unsupported")
        UNSUPPORTED,
        @SerializedName("other")
        OTHER;

        @Override
        public String toString() {
            switch (this) {
                case SENT:
                    return "Sent";
                case DEPOSIT:
                    return "Deposited";
                case ATM_WITHDRAWAL:
                    return "ATM withdrawal";
                case WITHDRAWAL:
                    return "Withdrawal";
                case SETTLEMENT:
                    return "Purchase";
                case PIN_PURCHASE:
                    return "PIN purchase";
                case FEE:
                    return "Fee";
                case REFUND:
                    return "Refund";
                case PENDING:
                case DECLINE:
                case UNSUPPORTED:
                    return "Merchant";
                case RECEIVED:
                    return "Received";
                case REVERSAL:
                    return "Reversal";
                case OTHER:
                    return "Other";
                default:
                    return "Unavailable";
            }
        }
    }
}
