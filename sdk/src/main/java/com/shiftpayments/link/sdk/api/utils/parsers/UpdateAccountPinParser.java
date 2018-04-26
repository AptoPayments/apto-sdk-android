package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shiftpayments.link.sdk.api.vos.Card;

import java.lang.reflect.Type;

/**
 * Created by adrian on 13/03/2018.
 */

public class UpdateAccountPinParser extends FinancialAccountParser {
    @Override
    public UpdateFinancialAccountPinResponseVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        return new UpdateFinancialAccountPinResponseVo ((Card) super.deserialize(json, iType, context));
    }
}
