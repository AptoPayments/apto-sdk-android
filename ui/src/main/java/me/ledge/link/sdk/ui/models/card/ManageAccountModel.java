package me.ledge.link.sdk.ui.models.card;

import android.content.res.Resources;

import java.util.ArrayList;

import me.ledge.common.utils.PagedList;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import me.ledge.link.sdk.ui.models.Model;

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
            newFundingSources.add(new FundingSourceModel(fundingSource, resources));
        }

        mFundingSources.addAll(newFundingSources);
    }

    public PagedList<FundingSourceModel> getFundingSources() {
        return mFundingSources;
    }
}