package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.models.ActivityModel;

/**
 * User data input related Model.
 * @author Wijnand
 */
public interface UserDataModel extends ActivityModel {

    /**
     * @return Whether all data has been set.
     */
    boolean hasAllData();
}
