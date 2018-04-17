package com.shift.link.sdk.ui.models.card;

import android.content.res.Resources;

import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.storages.CardStorage;

import java.util.ArrayList;

import me.ledge.common.utils.PagedList;

/**
 * Concrete {@link Model} for managing an account.
 * @author Adrian
 */
public class ManageAccountModel implements Model {

    private PagedList<FundingSourceModel> mFundingSources;

    /**
     * Creates a new {@link ManageAccountModel} instance.
     */
    public ManageAccountModel() {
        mFundingSources = new PagedList<>();
    }

    /**
     * Adds a list of funding sources.
     * @param fundingSources List of funding sources.
     */
    public void addFundingSources(Resources resources, FundingSourceVo[] fundingSources) {
        ArrayList<FundingSourceModel> newFundingSources = new ArrayList<>(fundingSources.length);
        for(FundingSourceVo fundingSource : fundingSources) {
            boolean isSelected = false;
            if(CardStorage.getInstance().getCard().custodian != null && fundingSource.custodianWallet.custodian != null) {
                isSelected = fundingSource.custodianWallet.custodian.id.equals(CardStorage.getInstance().getCard().custodian.id);
            }
            newFundingSources.add(new FundingSourceModel(fundingSource, resources, isSelected));
        }

        mFundingSources.addAll(newFundingSources);
    }

    public PagedList<FundingSourceModel> getFundingSources() {
        return mFundingSources;
    }
}