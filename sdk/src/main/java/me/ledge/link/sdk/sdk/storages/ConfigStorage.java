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

    /**
     * Creates a new {@link ConfigStorage} instance.
     */
    private ConfigStorage() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        loanPurposesDelegateList = new ArrayList<>();
        linkDisclaimerDelegateList = new ArrayList<>();
        partnerDisclaimersDelegateList = new ArrayList<>();
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

        clearDelegateLists();
    }


    private void clearDelegateLists() {
        loanPurposesDelegateList.clear();
        linkDisclaimerDelegateList.clear();
        partnerDisclaimersDelegateList.clear();
    }

    /**
     * Called when an API error occurred.
     * @param response The error.
     */
    @Subscribe
    public void apiErrorHandler(ApiErrorVo response) {
        for(LoanPurposesDelegate delegate : loanPurposesDelegateList) {
            delegate.errorReceived(response.toString());
        }
        for(LinkDisclaimerDelegate delegate : linkDisclaimerDelegateList) {
            delegate.errorReceived(response.toString());
        }
        for(PartnerDisclaimersDelegate delegate : partnerDisclaimersDelegateList) {
            delegate.errorReceived(response.toString());
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
