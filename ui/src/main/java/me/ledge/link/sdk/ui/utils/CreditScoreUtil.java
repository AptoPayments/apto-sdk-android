package me.ledge.link.sdk.ui.utils;

import me.ledge.link.sdk.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.sdk.api.vos.responses.config.CreditScoreVo;
import me.ledge.link.sdk.ui.storages.UIStorage;

/**
 * Credit score helper.
 * @author Adrian
 */
public class CreditScoreUtil {

    /**
     * @param creditScoreRange The creditScoreRange to look up the value for.
     * @return The corresponding string value.
     */
    public static String getCreditScoreDescription(int creditScoreRange) {
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        CreditScoreVo[] creditScoreValues = config.creditScoreOpts.data;
        return creditScoreValues[creditScoreRange].description;
    }
}
