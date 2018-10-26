package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;

import java.util.ArrayList;

import me.ledge.common.utils.PagedList;

/**
 * Concrete {@link Model} for the card settings.
 * @author Adrian
 */
public class CardSettingsModel implements Model {

    private Card mCard;
    private PagedList<BalanceModel> mBalancesList;

    public CardSettingsModel(Card card) {
        mCard = card;
        mBalancesList = new PagedList<>();
    }

    /**
     * Adds a list of balances.
     * @param balances List of balances.
     */
    public void addBalances(BalanceVo[] balances) {
        ArrayList<BalanceModel> newBalances = new ArrayList<>(balances.length);
        for(BalanceVo balance : balances) {
            boolean isSelected = false;
            if(CardStorage.getInstance().hasBalanceId() && balance.id != null) {
                isSelected = CardStorage.getInstance().getBalanceId().equals(balance.id);
            }
            BalanceModel balanceModel = new BalanceModel(balance, isSelected);
            newBalances.add(balanceModel);
        }

        mBalancesList.addAll(newBalances);
    }

    public PagedList<BalanceModel> getBalances() {
        return mBalancesList;
    }

    public String getAccountId() {
        if(mCard != null) {
            return mCard.mAccountId;
        }
        return "";
    }
}