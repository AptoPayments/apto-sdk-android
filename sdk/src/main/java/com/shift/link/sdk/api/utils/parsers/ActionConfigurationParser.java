package com.shift.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shift.link.sdk.api.utils.workflow.WorkflowConfigType;
import com.shift.link.sdk.api.vos.responses.config.ContentVo;
import com.shift.link.sdk.api.vos.responses.workflow.ActionConfigurationVo;
import com.shift.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shift.link.sdk.api.vos.responses.workflow.GenericMessageConfigurationVo;
import com.shift.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;

import java.lang.reflect.Type;

/**
 * Created by adrian on 25/01/2017.
 */

public class ActionConfigurationParser implements JsonDeserializer<ActionConfigurationVo> {
    @Override
    public ActionConfigurationVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject config = json.getAsJsonObject();
        if(config == null) {
            return null;
        }

        String type = ParsingUtils.getStringFromJson(config.get("type"));
        if (type != null) {
            switch (type) {
                case WorkflowConfigType.GENERIC_MESSAGE_CONFIG:
                    return parseGenericMessageConfig(config);
                case WorkflowConfigType.SELECT_FUNDING_ACCOUNT_CONFIG:
                    return parseSelectFundingAccountConfig(config);
            }
        }
        return null;
    }

    private static ContentVo parseContent(JsonObject content) {
        String contentType = ParsingUtils.getStringFromJson(content.get("type"));
        String contentFormat = ParsingUtils.getStringFromJson(content.get("format"));
        String contentValue = ParsingUtils.getStringFromJson(content.get("value"));

        return new ContentVo(contentType, contentFormat, contentValue);
    }

    private static CallToActionVo parseCallToAction(JsonObject callToAction) {
        String type = ParsingUtils.getStringFromJson(callToAction.get("type"));
        String title = ParsingUtils.getStringFromJson(callToAction.get("title"));
        String actionType = ParsingUtils.getStringFromJson(callToAction.get("action_type"));
        String externalUrl = ParsingUtils.getStringFromJson(callToAction.get("external_url"));
        String trackerClickEventName = ParsingUtils.getStringFromJson(callToAction.get("tracker_click_event_name"));
        String trackerIncrementName = ParsingUtils.getStringFromJson(callToAction.get("tracker_increment_name"));

        return new CallToActionVo(type, title, actionType, externalUrl, trackerClickEventName, trackerIncrementName);
    }

    private GenericMessageConfigurationVo parseGenericMessageConfig(JsonObject config) {
        String type = config.get("type").getAsString();
        String title = ParsingUtils.getStringFromJson(config.get("title"));
        String image = ParsingUtils.getStringFromJson(config.get("image"));
        String trackerEventName = ParsingUtils.getStringFromJson(config.get("tracker_event_name"));
        String trackerIncrementName = ParsingUtils.getStringFromJson(config.get("tracker_increment_name"));

        ContentVo content = null;
        JsonElement contentJson = config.get("content");
        if(!contentJson.isJsonNull()) {
            content = parseContent(contentJson.getAsJsonObject());
        }

        JsonElement callToActionJson = config.get("call_to_action");
        CallToActionVo callToAction = null;
        if(!callToActionJson.isJsonNull()) {
            callToAction = parseCallToAction(callToActionJson.getAsJsonObject());
        }

        return new GenericMessageConfigurationVo(type, title, content, image, trackerEventName, trackerIncrementName, callToAction);
    }


    private ActionConfigurationVo parseSelectFundingAccountConfig(JsonObject config) {
        boolean isAchEnabled = config.get("ach_enabled").getAsBoolean();
        boolean isCardEnabled = config.get("card_enabled").getAsBoolean();
        boolean isVirtualCardEnabled = config.get("virtual_card_enabled").getAsBoolean();
        return new SelectFundingAccountConfigurationVo(isAchEnabled, isCardEnabled, isVirtualCardEnabled);
    }
}
