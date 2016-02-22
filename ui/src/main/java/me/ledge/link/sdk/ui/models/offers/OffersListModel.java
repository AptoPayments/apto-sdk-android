package me.ledge.link.sdk.ui.models.offers;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class OffersListModel extends AbstractActivityModel implements ActivityModel, Model {

    private String mBearerToken;

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.offers_list_label;
    }

    /**
     * @return The bearer token.
     */
    public String getBearerToken() {
        return mBearerToken;
    }

    /**
     * Stores a new bearer token.
     * @param bearerToken Token.
     */
    public void setBearerToken(String bearerToken) {
        mBearerToken = bearerToken;
    }
}
