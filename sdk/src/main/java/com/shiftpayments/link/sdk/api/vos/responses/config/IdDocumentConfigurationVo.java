package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.datapoints.IdDocument;

import java.util.HashMap;
import java.util.List;

public class IdDocumentConfigurationVo extends DataPointConfigurationVo {
    @SerializedName("allowed_document_types")
    public HashMap<String, List<IdDocument.IdDocumentType>> allowedDocumentTypes;

    public IdDocumentConfigurationVo(String type, HashMap<String, List<IdDocument.IdDocumentType>> allowedDocumentTypes) {
        super(type);
        this.allowedDocumentTypes = allowedDocumentTypes;
    }
}
