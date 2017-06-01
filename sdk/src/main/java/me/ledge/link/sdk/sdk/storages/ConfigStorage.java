package me.ledge.link.sdk.sdk.storages;

import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;
import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.api.vos.responses.config.DisclaimerVo;
import me.ledge.link.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointsListResponseVo;

import static me.ledge.link.sdk.sdk.LedgeLinkSdk.getApiWrapper;

/**
 * Stores config data.
 * @author Adrian
 */
public class ConfigStorage {

    private static ConfigStorage mInstance;
    private LinkConfigResponseVo mLinkConfig;
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
        CompletableFuture<LoanPurposesResponseVo> future = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.loanPurposesList;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanPurposesList;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (LoanPurposesResponseVo) getResultFromFuture(future);
    }

    public synchronized DisclaimerVo getLinkDisclaimer() {
        CompletableFuture<DisclaimerVo> future = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.linkDisclaimer;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.linkDisclaimer;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (DisclaimerVo) getResultFromFuture(future);
    }

    public synchronized DisclaimerVo getPrequalificationDisclaimer() {
        CompletableFuture<DisclaimerVo> future = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.preQualificationDisclaimer;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.preQualificationDisclaimer;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (DisclaimerVo) getResultFromFuture(future);
    }

    public synchronized LoanProductListVo getLoanProducts() {
        CompletableFuture<LoanProductListVo> future = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.loanProductList;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanProductList;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (LoanProductListVo) getResultFromFuture(future);
    }

    public synchronized RequiredDataPointsListResponseVo getRequiredUserData() {
        CompletableFuture<RequiredDataPointsListResponseVo> future = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.userRequiredData;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.userRequiredData;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (RequiredDataPointsListResponseVo) getResultFromFuture(future);
    }

    public synchronized boolean getPOSMode() {
        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.posMode;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.posMode;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (boolean) getResultFromFuture(f);
    }

    public synchronized double getMinLoanAmount() {
        CompletableFuture<Double> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.loanAmountMin;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountMin;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (double) getResultFromFuture(f);
    }

    public synchronized double getMaxLoanAmount() {
        CompletableFuture<Double> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.loanAmountMax;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountMax;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (double) getResultFromFuture(f);
    }

    public synchronized double getLoanAmountIncrements() {
        CompletableFuture<Double> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.loanAmountIncrements;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountIncrements;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (double) getResultFromFuture(f);
    }

    public synchronized double getLoanAmountDefault() {
        CompletableFuture<Double> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.loanAmountDefault;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.loanAmountDefault;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (double) getResultFromFuture(f);
    }

    public synchronized boolean getSkipLinkDisclaimer() {
        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.skipLinkDisclaimer;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.skipLinkDisclaimer;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (boolean) getResultFromFuture(f);
    }

    public synchronized boolean isStrictAddressValidationEnabled() {
        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.isStrictAddressValidationEnabled;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.isStrictAddressValidationEnabled;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (boolean) getResultFromFuture(f);
    }

    public synchronized OffersListStyle getOffersListStyle() {
        CompletableFuture<OffersListStyle> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return OffersListStyle.valueOf(mLinkConfig.offerListStyle);
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return OffersListStyle.valueOf(mLinkConfig.offerListStyle);
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (OffersListStyle) getResultFromFuture(f);
    }

    public synchronized boolean getSkipLoanAmount() {
        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.skipLoanAmount;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.skipLoanAmount;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (boolean) getResultFromFuture(f);
    }

    public synchronized boolean getSkipLoanPurpose() {
        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            if(isConfigCached()) {
                return mLinkConfig.skipLoanPurpose;
            }
            else {
                try {
                    mLinkConfig = getApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
                    return mLinkConfig.skipLoanPurpose;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            }
        });

        return (boolean) getResultFromFuture(f);
    }

    private Object getResultFromFuture(CompletableFuture future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
            throw new CompletionException(e);
        }
    }

    private boolean isConfigCached() {
        return mLinkConfig != null;
    }
}
