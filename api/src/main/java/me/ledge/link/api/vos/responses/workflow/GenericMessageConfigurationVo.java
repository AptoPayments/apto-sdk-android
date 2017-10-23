package me.ledge.link.api.vos.responses.workflow;

/**
 * Created by adrian on 18/10/2017.
 */

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.responses.config.ContentVo;

public class GenericMessageConfigurationVo extends ActionConfigurationVo {

    @SerializedName("type")
    public String type;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public ContentVo content;

    @SerializedName("image")
    public String image;

    @SerializedName("tracker_event_name")
    public String trackerEventName;

    @SerializedName("tracker_increment_name")
    public String trackerIncrementName;

    @SerializedName("call_to_action")
    public CallToActionVo callToAction;

    public GenericMessageConfigurationVo(String type, String title, ContentVo content, String image, String trackerEventName, String trackerIncrementName, CallToActionVo callToAction) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.image = image;
        this.trackerEventName = trackerEventName;
        this.trackerIncrementName = trackerIncrementName;
        this.callToAction = callToAction;
    }
}
