package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class IdDocumentConfigurationVo extends DataPointConfigurationVo {
    @SerializedName("allowed_document_types")
    public HashMap<String, List<String>> allowedDocumentTypes;

    public IdDocumentConfigurationVo(String type, HashMap<String, List<String>> allowedDocumentTypes) {
        super(type);
        this.allowedDocumentTypes = allowedDocumentTypes;
    }
}
