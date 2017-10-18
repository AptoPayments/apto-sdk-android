package me.ledge.link.api.vos.responses.workflow;

/**
 * Created by adrian on 18/10/2017.
 */

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.responses.config.DisclaimerVo;

public class GenericMessageConfigurationVo extends ConfigurationVo {

    @SerializedName("type")
    public String type;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public DisclaimerVo content;

    @SerializedName("image")
    public Object image;

    @SerializedName("tracker_event_name")
    public String trackerEventName;

    @SerializedName("tracker_increment_name")
    public String trackerIncrementName;

    @SerializedName("call_to_action")
    public CallToActionVo callToAction;


}
