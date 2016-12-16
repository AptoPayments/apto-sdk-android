package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.vos.LoanDataVo;

/**
 * User data input related Model.
 * @author Wijnand
 */
public interface LoanDataModel extends ActivityModel {

    /**
     * @return Whether all data has been set.
     */
    boolean hasAllData();

    /**
     * @return Base data for this Model.
     */
    LoanDataVo getBaseData();

    /**
     * Stores the Model's base data.
     * @param base Base data for this Model.
     */
    void setBaseData(LoanDataVo base);
}
