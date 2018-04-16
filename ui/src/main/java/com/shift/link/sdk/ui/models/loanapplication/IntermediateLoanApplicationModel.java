package com.shift.link.sdk.ui.models.loanapplication;

import android.content.res.Resources;

import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;

/**
 * Loan application intermediate state {@link Model}.
 * @author Wijnand
 */
public interface IntermediateLoanApplicationModel extends ActivityModel, Model {

    /**
     * @return Cloud image resource ID.
     */
    int getCloudImageResource();

    /**
     * @return Explanation text resource ID.
     */
    int getExplanationTextResource();

    /**
     * @param resources Resources to fetch String data from.
     * @return Formatted explanation text.
     */
    String getExplanationText(Resources resources);

    /**
     * @return Big button configuration.
     */
    BigButtonModel getBigButtonModel();

    /**
     * @return Whether the "get offers" button should be shown.
     */
    boolean showOffersButton();

}
