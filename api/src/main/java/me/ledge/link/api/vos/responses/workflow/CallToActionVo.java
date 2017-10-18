package me.ledge.link.api.vos.responses.workflow;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 18/10/2017.
 */

public class CallToActionVo {

    @SerializedName("type")
    public String type;

    @SerializedName("title")
    public String title;

    @SerializedName("action_type")
    public String actionType;

    @SerializedName("external_url")
    public Object externalUrl;

    @SerializedName("tracker_click_event_name")
    public String trackerClickEventName;

    @SerializedName("tracker_increment_name")
    public String trackerIncrementName;
}
