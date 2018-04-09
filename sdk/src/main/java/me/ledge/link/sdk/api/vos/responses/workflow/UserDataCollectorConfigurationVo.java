package me.ledge.link.sdk.api.vos.responses.workflow;

/**
 * Created by adrian on 18/10/2017.
 */

public class UserDataCollectorConfigurationVo extends ActionConfigurationVo {

    public String title;
    public CallToActionVo callToAction;

    public UserDataCollectorConfigurationVo(String title, CallToActionVo callToAction) {
        this.title = title;
        this.callToAction = callToAction;
    }
}
