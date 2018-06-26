package com.shiftpayments.link.sdk.sdk.storages;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardconfig.CardConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LinkConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LoanProductListVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LoanPurposesResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;

import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;

/**
 * Stores config data.
 * @author Adrian
 */
public class ConfigStorage {

    private static ConfigStorage mInstance;
    private LinkConfigResponseVo mLinkConfig;
    private CardConfigResponseVo mCardConfig;
    private UserDataCollectorConfigurationVo mUserDataCollectorConfig;
    private String mCoinbaseClientId;
    private String mCoinbaseClientSecret;

    public enum OffersListStyle {
        list, carousel
    }

    /**
     * Creates a new {@link ConfigStorage} instance.
     */
    private ConfigStorage() { }

    /**
     * @return The single instance of this class.
     */
    public static synchronized ConfigStorage getInstance() {
        if (mInstance == null) {
            mInstance = new ConfigStorage();
        }
        return mInstance;
    }

    public void setLinkConfig(LinkConfigResponseVo mLinkConfig) {
        this.mLinkConfig = mLinkConfig;
    }

    public synchronized LoanPurposesResponseVo getLoanPurposes() {
        if(isLinkConfigCached()) {
            return mLinkConfig.loanPurposesList;
        }
        else {
            CompletableFuture<LoanPurposesResponseVo> future = CompletableFuture.supplyAsync(() -> {

                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanPurposesList;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (LoanPurposesResponseVo) getResultFromFuture(future);
        }
    }

    public synchronized ContentVo getLinkDisclaimer() {
        if(isLinkConfigCached()) {
            return mLinkConfig.linkDisclaimer;
        }
        else {
            CompletableFuture<ContentVo> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.linkDisclaimer;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (ContentVo) getResultFromFuture(future);
        }
    }

    public synchronized LoanProductListVo getLoanProducts() {
        if(isLinkConfigCached()) {
            return mLinkConfig.loanProductList;
        }
        else {
            CompletableFuture<LoanProductListVo> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanProductList;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (LoanProductListVo) getResultFromFuture(future);
        }
    }

    public synchronized RequiredDataPointVo[] getRequiredUserData() {
        if(isLinkConfigCached()) {
            return mLinkConfig.userRequiredData.data;
        }
        else {
            CompletableFuture<RequiredDataPointVo[]> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.userRequiredData.data;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (RequiredDataPointVo[]) getResultFromFuture(future);
        }
    }

    public synchronized void setRequiredUserData(RequiredDataPointVo[] requiredDataPoints) {
        // TODO: UserDataCollector should not read this from here, it should be an input to the module
        mLinkConfig.userRequiredData.data = requiredDataPoints;
    }

    public synchronized boolean getPOSMode() {
        if(isLinkConfigCached()) {
            return mLinkConfig.posMode;
        }
        else {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.posMode;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (boolean) getResultFromFuture(future);
        }
    }

    public synchronized double getMinLoanAmount() {
        if(isLinkConfigCached()) {
            return mLinkConfig.loanAmountMin;
        }
        else {
            CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountMin;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (double) getResultFromFuture(future);
        }
    }

    public synchronized double getMaxLoanAmount() {
        if(isLinkConfigCached()) {
            return mLinkConfig.loanAmountMax;
        }
        else {
            CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountMax;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (double) getResultFromFuture(future);
        }
    }

    public synchronized double getLoanAmountIncrements() {
        if(isLinkConfigCached()) {
            return mLinkConfig.loanAmountIncrements;
        }
        else {
            CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountIncrements;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (double) getResultFromFuture(future);
        }
    }

    public synchronized double getLoanAmountDefault() {
        if(isLinkConfigCached()) {
            return mLinkConfig.loanAmountDefault;
        }
        else {
            CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountDefault;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (double) getResultFromFuture(future);
        }
    }

    public synchronized boolean getSkipLinkDisclaimer() {
        if(isLinkConfigCached()) {
            return mLinkConfig.skipLinkDisclaimer;
        }
        else {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {

                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.skipLinkDisclaimer;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (boolean) getResultFromFuture(future);
        }
    }

    public synchronized boolean isStrictAddressValidationEnabled() {
        if(isLinkConfigCached()) {
            return mLinkConfig.isStrictAddressValidationEnabled;
        }
        else {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.isStrictAddressValidationEnabled;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (boolean) getResultFromFuture(future);
        }
    }

    public synchronized OffersListStyle getOffersListStyle() {
        if(isLinkConfigCached()) {
            return OffersListStyle.valueOf(mLinkConfig.offerListStyle);
        }
        else {
            CompletableFuture<OffersListStyle> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return OffersListStyle.valueOf(mLinkConfig.offerListStyle);
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (OffersListStyle) getResultFromFuture(future);
        }
    }

    public synchronized boolean getSkipLoanAmount() {
        if(isLinkConfigCached()) {
            return mLinkConfig.skipLoanAmount;
        }
        else {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.skipLoanAmount;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (boolean) getResultFromFuture(future);
        }
    }

    public synchronized boolean getSkipLoanPurpose() {
        if(isLinkConfigCached()) {
            return mLinkConfig.skipLoanPurpose;
        }
        else {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mLinkConfig = ShiftLinkSdk.getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.skipLoanPurpose;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (boolean) getResultFromFuture(future);
        }
    }

    private Object getResultFromFuture(CompletableFuture future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
            throw new CompletionException(e);
        }
    }

    private boolean isLinkConfigCached() {
        return mLinkConfig != null;
    }

    private boolean isCardConfigCached() {
        return mCardConfig != null;
    }

    public void setCoinbaseKeys(String coinbaseClientId, String coinbaseClientSecret) {
        mCoinbaseClientId = coinbaseClientId;
        mCoinbaseClientSecret = coinbaseClientSecret;
    }

    public String getCoinbaseClientId() {
        return mCoinbaseClientId;
    }

    public String getCoinbaseClientSecret() {
        return mCoinbaseClientSecret;
    }

    public synchronized CardConfigResponseVo getCardConfig() {
        if(isCardConfigCached()) {
            return mCardConfig;
        }
        else {
            CompletableFuture<CardConfigResponseVo> future = CompletableFuture.supplyAsync(() -> {
                try {
                    mCardConfig = ShiftLinkSdk.getApiWrapper().getCardConfig(new UnauthorizedRequestVo());
                    return mCardConfig;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return (CardConfigResponseVo) getResultFromFuture(future);
        }
    }

    public synchronized void setCardConfig(CardConfigResponseVo cardConfig) {
        mCardConfig = cardConfig;
    }

    public synchronized UserDataCollectorConfigurationVo getUserDataCollectorConfig() {
        return mUserDataCollectorConfig;
    }

    public synchronized void setUserDataCollectorConfig(UserDataCollectorConfigurationVo userDataCollectorConfig) {
        mUserDataCollectorConfig = userDataCollectorConfig;
    }
}
