
package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.models.ActivityModel;

/**
 * User data input related Model.
 * @author Adrian
 */

public interface UserDataModel extends ActivityModel {


/**
 * @return Whether all data has been set.
 */

    boolean hasAllData();


/**
 * @return Base data for this Model.
 */

    DataPointVo.DataPointList getBaseData();


/**
 * Stores the Model's base data.
 * @param base Base data for this Model.
 */

    void setBaseData(DataPointVo.DataPointList base);
}

