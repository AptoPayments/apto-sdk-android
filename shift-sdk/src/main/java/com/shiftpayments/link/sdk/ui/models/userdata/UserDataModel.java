
package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;

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

    DataPointList getBaseData();


/**
 * Stores the Model's base data.
 * @param base Base data for this Model.
 */

    void setBaseData(DataPointList base);
}

