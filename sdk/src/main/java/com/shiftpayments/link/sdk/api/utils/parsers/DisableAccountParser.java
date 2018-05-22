package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.DisableFinancialAccountResponseVo;

import java.lang.reflect.Type;

/**
 * Created by adrian on 21/05/2018.
 */

public class DisableAccountParser extends FinancialAccountParser {
    @Override
    public DisableFinancialAccountResponseVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        return new DisableFinancialAccountResponseVo((Card) super.deserialize(json, iType, context));
    }
}
