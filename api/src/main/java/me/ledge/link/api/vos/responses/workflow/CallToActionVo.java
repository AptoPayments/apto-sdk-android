package me.ledge.link.api.vos.responses.workflow;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 18/10/2017.
 */

public class CallToActionVo {
    public CallToActionVo(String type, String title, String actionType, String externalUrl, String trackerClickEventName, String trackerIncrementName) {
        this.type = type;
        this.title = title;
        this.actionType = actionType;
        this.externalUrl = externalUrl;
        this.trackerClickEventName = trackerClickEventName;
        this.trackerIncrementName = trackerIncrementName;
    }

    @SerializedName("type")
    public String type;

    @SerializedName("title")
    public String title;

    @SerializedName("action_type")
    public String actionType;

    @SerializedName("external_url")
    public String externalUrl;

    @SerializedName("tracker_click_event_name")
    public String trackerClickEventName;

    @SerializedName("tracker_increment_name")
    public String trackerIncrementName;
}
