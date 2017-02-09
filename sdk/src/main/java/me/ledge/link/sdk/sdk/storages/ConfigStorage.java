package me.ledge.link.sdk.sdk.storages;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;

/**
 * Stores config data.
 * @author Adrian
 */
public class ConfigStorage {

    private static ConfigStorage mInstance;
    private LinkConfigResponseVo mLinkConfig;
    private boolean isFetchingAPI = false;
    private List<LoanPurposesDelegate> loanPurposesDelegateList;
    private List<LinkDisclaimerDelegate> linkDisclaimerDelegateList;
    private List<PartnerDisclaimersDelegate> partnerDisclaimersDelegateList;
    private List<MaxLoanAmountDelegate> maxLoanAmountDelegateList;
    private List<LoanAmountIncrementsDelegate> loanAmountIncrementsDelegateList;

    /**
     * Creates a new {@link ConfigStorage} instance.
     */
    private ConfigStorage() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        loanPurposesDelegateList = new ArrayList<>();
        linkDisclaimerDelegateList = new ArrayList<>();
        partnerDisclaimersDelegateList = new ArrayList<>();
        maxLoanAmountDelegateList = new ArrayList<>();
        loanAmountIncrementsDelegateList = new ArrayList<>();
    }

    /**
     * @return The single instance of this class.
     */
    public static synchronized ConfigStorage getInstance() {
        if (mInstance == null) {
            mInstance = new ConfigStorage();
        }
        return mInstance;
    }

    public synchronized void getLoanPurposes(LoanPurposesDelegate delegate) {
        if(isConfigCached()) {
            delegate.loanPurposesListRetrieved(mLinkConfig.loanPurposesList);
        }
        else {
            loanPurposesDelegateList.add(delegate);
            getLinkConfig();
        }
    }

    public synchronized void getLinkDisclaimer(LinkDisclaimerDelegate delegate) {
        if(isConfigCached()) {
            delegate.linkDisclaimersRetrieved(mLinkConfig.linkDisclaimer);
        }
        else {
            linkDisclaimerDelegateList.add(delegate);
            getLinkConfig();
        }
    }

    public synchronized void getPartnerDisclaimersList(PartnerDisclaimersDelegate delegate) {
        if(isConfigCached()) {
            delegate.partnerDisclaimersListRetrieved(mLinkConfig.productDisclaimerList);
        }
        else {
            partnerDisclaimersDelegateList.add(delegate);
            getLinkConfig();
        }
    }

    public synchronized void getMaxLoanAmount(MaxLoanAmountDelegate delegate) {
        if(isConfigCached()) {
            delegate.maxLoanAmountRetrieved(mLinkConfig.loanAmountMax);
        }
        else {
            maxLoanAmountDelegateList.add(delegate);
            getLinkConfig();
        }
    }

    public synchronized void getLoanAmountIncrements(LoanAmountIncrementsDelegate delegate) {
        if(isConfigCached()) {
            delegate.loanAmountIncrementsRetrieved(mLinkConfig.loanAmountIncrements);
        }
        else {
            loanAmountIncrementsDelegateList.add(delegate);
            getLinkConfig();
        }
    }

    @Subscribe
    public void configHandler(LinkConfigResponseVo linkConfigResponse) {
        isFetchingAPI = false;
        setLinkConfig(linkConfigResponse);

        for(LoanPurposesDelegate delegate : loanPurposesDelegateList) {
            delegate.loanPurposesListRetrieved(mLinkConfig.loanPurposesList);
        }
        for(LinkDisclaimerDelegate delegate : linkDisclaimerDelegateList) {
            delegate.linkDisclaimersRetrieved(mLinkConfig.linkDisclaimer);
        }
        for(PartnerDisclaimersDelegate delegate : partnerDisclaimersDelegateList) {
            delegate.partnerDisclaimersListRetrieved(mLinkConfig.productDisclaimerList);
        }
        for(MaxLoanAmountDelegate delegate : maxLoanAmountDelegateList) {
            delegate.maxLoanAmountRetrieved(mLinkConfig.loanAmountMax);
        }
        for(LoanAmountIncrementsDelegate delegate : loanAmountIncrementsDelegateList) {
            delegate.loanAmountIncrementsRetrieved(mLinkConfig.loanAmountIncrements);
        }

        clearDelegateLists();
    }


    private void clearDelegateLists() {
        loanPurposesDelegateList.clear();
        linkDisclaimerDelegateList.clear();
        partnerDisclaimersDelegateList.clear();
        maxLoanAmountDelegateList.clear();
        loanAmountIncrementsDelegateList.clear();
    }

    /**
     * Called when an API error occurred.
     * @param response The error.
     */
    @Subscribe
    public void apiErrorHandler(ApiErrorVo response) {
        String errorMessage = response.toString();
        for(LoanPurposesDelegate delegate : loanPurposesDelegateList) {
            delegate.errorReceived(errorMessage);
        }
        for(LinkDisclaimerDelegate delegate : linkDisclaimerDelegateList) {
            delegate.errorReceived(errorMessage);
        }
        for(PartnerDisclaimersDelegate delegate : partnerDisclaimersDelegateList) {
            delegate.errorReceived(errorMessage);
        }
        for(MaxLoanAmountDelegate delegate : maxLoanAmountDelegateList) {
            delegate.errorReceived(errorMessage);
        }
        for(LoanAmountIncrementsDelegate delegate : loanAmountIncrementsDelegateList) {
            delegate.errorReceived(errorMessage);
        }
    }

    /**
     * Stores link config
     */
    private void setLinkConfig(LinkConfigResponseVo linkConfig) {
        mLinkConfig = linkConfig;
    }

    private synchronized void getLinkConfig() {
        if(!isFetchingAPI) {
            isFetchingAPI = true;
            LedgeLinkSdk.getLinkConfig();
        }
    }

    private boolean isConfigCached() {
        return mLinkConfig != null;
    }
}
