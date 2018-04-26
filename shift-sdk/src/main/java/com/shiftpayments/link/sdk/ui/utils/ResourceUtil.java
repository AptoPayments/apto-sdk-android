package com.shiftpayments.link.sdk.ui.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

/**
 * Android resource helper.
 * @author Wijnand
 */
public class ResourceUtil {

    /**
     * @param context The {@link Context} containing the attribute values.
     * @param attribute The attribute ID to look up the value for.
     * @return Attribute value.
     */
    public int getResourceIdForAttribute(Context context, int attribute) {
        int id;

        int[] attributesList = new int[] { attribute };
        try {
            TypedArray array = context.obtainStyledAttributes(new TypedValue().data, attributesList);
            id = array.getResourceId(0, 0);
            array.recycle();
        } catch (RuntimeException rte) {
            id = 0;
        }

        return id;
    }

}
