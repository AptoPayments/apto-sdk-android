package me.ledge.link.sdk.ui.utils;

import android.content.Context;

import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.config.CreditScoreVo;
import me.ledge.link.sdk.ui.storages.UIStorage;

/**
 * Credit score helper.
 * @author Adrian
 */
public class CreditScoreUtil {

    /**
     * @param context The {@link Context} used get the string.
     * @param creditScoreRange The creditScoreRange to look up the value for.
     * @return The corresponding string value.
     */
    public static String getCreditScoreDescription(Context context, int creditScoreRange) {
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        CreditScoreVo[] creditScoreValues = config.creditScoreOpts.data;
        return creditScoreValues[creditScoreRange].description;

    }
}
