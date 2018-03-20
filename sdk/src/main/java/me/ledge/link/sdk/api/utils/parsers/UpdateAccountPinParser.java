package me.ledge.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import me.ledge.link.sdk.api.vos.datapoints.Card;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;

/**
 * Created by adrian on 13/03/2018.
 */

public class UpdateAccountPinParser extends FinancialAccountParser {
    @Override
    public UpdateFinancialAccountPinResponseVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        return new UpdateFinancialAccountPinResponseVo ((Card) super.deserialize(json, iType, context));
    }
}
