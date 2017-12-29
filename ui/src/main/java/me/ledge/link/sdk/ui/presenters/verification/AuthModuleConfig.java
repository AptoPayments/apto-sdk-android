package me.ledge.link.sdk.ui.presenters.verification;

import me.ledge.link.api.vos.datapoints.DataPointVo;

public class AuthModuleConfig {

    DataPointVo.DataPointType primaryCredentialType;
    DataPointVo.DataPointType secondaryCredentialType;

    public AuthModuleConfig(String primaryCredential, String secondaryCredential) {
        this.primaryCredentialType = DataPointVo.DataPointType.fromString(primaryCredential);
        this.secondaryCredentialType = DataPointVo.DataPointType.fromString(secondaryCredential);
    }
}
