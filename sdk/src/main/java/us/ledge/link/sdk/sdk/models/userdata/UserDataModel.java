package us.ledge.link.sdk.sdk.models.userdata;

import us.ledge.link.sdk.sdk.models.ActivityModel;
import us.ledge.link.sdk.sdk.vos.UserDataVo;

/**
 * User data input related Model.
 * @author Wijnand
 */
public interface UserDataModel extends ActivityModel {

    /**
     * @return Whether all data has been set.
     */
    boolean hasAllData();

    /**
     * @return Base data for this Model.
     */
    UserDataVo getBaseData();

    /**
     * Stores the Model's base data.
     * @param base Base data for this Model.
     */
    void setBaseData(UserDataVo base);
}
