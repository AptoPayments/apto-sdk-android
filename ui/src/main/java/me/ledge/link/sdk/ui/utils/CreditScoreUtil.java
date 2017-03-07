package me.ledge.link.sdk.ui.utils;

import android.content.Context;
import android.util.SparseArray;

import me.ledge.link.api.utils.CreditScoreRange;
import me.ledge.link.sdk.ui.R;

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
        SparseArray<String> scoreToStringMap = new SparseArray<>(4);
        scoreToStringMap.put(CreditScoreRange.EXCELLENT, context.getString(R.string.credit_score_excellent));
        scoreToStringMap.put(CreditScoreRange.GOOD, context.getString(R.string.credit_score_good));
        scoreToStringMap.put(CreditScoreRange.FAIR, context.getString(R.string.credit_score_fair));
        scoreToStringMap.put(CreditScoreRange.POOR, context.getString(R.string.credit_score_poor));

        return scoreToStringMap.get(creditScoreRange);
    }
}
