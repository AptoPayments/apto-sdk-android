package me.ledge.link.sdk.sdk.storages;

import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;
import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.api.vos.responses.config.LinkDisclaimerVo;
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

    public synchronized LinkDisclaimerVo getLinkDisclaimer() {
        CompletableFuture<LinkDisclaimerVo> future = CompletableFuture.supplyAsync(() -> {
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

        return (LinkDisclaimerVo) getResultFromFuture(future);
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
