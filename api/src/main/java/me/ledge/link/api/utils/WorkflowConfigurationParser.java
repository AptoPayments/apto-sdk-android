package me.ledge.link.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import me.ledge.link.api.vos.responses.config.DisclaimerVo;
import me.ledge.link.api.vos.responses.workflow.CallToActionVo;
import me.ledge.link.api.vos.responses.workflow.ConfigurationVo;
import me.ledge.link.api.vos.responses.workflow.GenericMessageConfigurationVo;

/**
 * Created by adrian on 25/01/2017.
 */

public class WorkflowConfigurationParser implements JsonDeserializer<ConfigurationVo> {
    @Override
    public ConfigurationVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject config = json.getAsJsonObject();
        String type = config.get("type").getAsString();

        switch (type) {
            case "action_generic_message_config":
                return parseGenericMessageConfig(config);
        }
        return null;
    }

    private String getStringFromJson(JsonElement element) {
        if(element.isJsonNull()) {
            return null;
        }

        return element.getAsString();
    }

    private DisclaimerVo parseContent(JsonObject content) {
        String contentType = getStringFromJson(content.get("type"));
        String contentFormat = getStringFromJson(content.get("format"));
        String contentValue = getStringFromJson(content.get("value"));

        return new DisclaimerVo(contentType, contentFormat, contentValue);
    }

    private CallToActionVo parseCallToAction(JsonObject callToAction) {
        String type = getStringFromJson(callToAction.get("type"));
        String title = getStringFromJson(callToAction.get("title"));
        String actionType = getStringFromJson(callToAction.get("action_type"));
        String externalUrl = getStringFromJson(callToAction.get("external_url"));
        String trackerClickEventName = getStringFromJson(callToAction.get("tracker_click_event_name"));
        String trackerIncrementName = getStringFromJson(callToAction.get("tracker_increment_name"));

        return new CallToActionVo(type, title, actionType, externalUrl, trackerClickEventName, trackerIncrementName);
    }

    private GenericMessageConfigurationVo parseGenericMessageConfig(JsonObject config) {
        String type = config.get("type").getAsString();
        String title = getStringFromJson(config.get("title"));
        String image = getStringFromJson(config.get("image"));
        String trackerEventName = getStringFromJson(config.get("tracker_event_name"));
        String trackerIncrementName = getStringFromJson(config.get("tracker_increment_name"));

        DisclaimerVo content = parseContent(config.get("content").getAsJsonObject());
        CallToActionVo callToAction = parseCallToAction(config.get("call_to_action").getAsJsonObject());

        return new GenericMessageConfigurationVo(type, title, content, image, trackerEventName, trackerIncrementName, callToAction);
    }
}
