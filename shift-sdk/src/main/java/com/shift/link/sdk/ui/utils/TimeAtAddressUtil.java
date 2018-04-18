package com.shift.link.sdk.ui.utils;

import com.shift.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.TimeAtAddressVo;
import com.shift.link.sdk.ui.storages.UIStorage;

/**
 * Time at Address helper.
 * @author Adrian
 */
public class TimeAtAddressUtil {

    /**
     * @param timeAtAddressRange The time at address range to look up the value for.
     * @return The corresponding string value.
     */
    public static String getTimeAtAddressDescription(int timeAtAddressRange) {
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        TimeAtAddressVo[] timeAtAddressValues = config.timeAtAddressOpts.data;
        return timeAtAddressValues[timeAtAddressRange-1].description;
    }
}
