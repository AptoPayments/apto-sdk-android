package me.ledge.link.sdk.ui.models.lenders;

import android.text.TextUtils;
import me.ledge.link.api.vos.responses.offers.LenderVo;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Lender details {@link Model}.
 * @author Wijnand
 */
public class LenderModel implements Model {

    private LenderVo mRawLender;

    /**
     * Creates a new {@link LenderModel} instance.
     * @param lender Raw lender details.
     */
    public LenderModel(LenderVo lender) {
        mRawLender = lender;
    }

    /**
     * @return Lender name.
     */
    public String getLenderName() {
        String name = "";

        if (mRawLender != null) {
            name = mRawLender.lender_name;
        }

        return name;
    }

    /**
     * @return Small lender image URL. If not found, will fall back to the large image URL.
     */
    public String getLenderImage() {
        String imageUrl = null;

        if (mRawLender != null) {
            if (!TextUtils.isEmpty(mRawLender.small_image)) {
                imageUrl = mRawLender.small_image;
            } else if (!TextUtils.isEmpty(mRawLender.large_image)) {
                imageUrl = mRawLender.large_image;
            }
        }

        return imageUrl;
    }
}
