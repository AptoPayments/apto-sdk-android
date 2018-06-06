package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.EnableFinancialAccountResponseVo;

import java.lang.reflect.Type;

/**
 * Created by adrian on 21/05/2018.
 */

public class EnableAccountParser extends FinancialAccountParser {
    @Override
    public EnableFinancialAccountResponseVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        return new EnableFinancialAccountResponseVo((Card) super.deserialize(json, iType, context));
    }
}