package com.shiftpayments.link.sdk.ui.geocoding.handlers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.shiftpayments.link.imageloaders.volley.VolleySingleton;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.geocoding.vos.GeocodingResultVo;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;

/**
 * Created by adrian on 01/09/2017.
 */

public class GeocodingHandler {
    private boolean mIsCancelled;

    public GeocodingHandler() {
        mIsCancelled = false;
    }

    public void reverseGeocode(Context context, String placeId, GeocodingOnSuccessCallback onSuccess, GetPredictionsOnErrorCallback onError) {
        if(mIsCancelled) {
            return;
        }
        String url = context.getString(R.string.geocoding_google_maps_api_url);
        url+="?placeid="+placeId;
        url+="&fields=address_component";
        url+="&key="+context.getString(R.string.google_places_autocomplete_api_key);
        // https://developers.google.com/places/web-service/autocomplete#session_tokens
        url+="&sessiontoken="+ UserStorage.getInstance().getSessionToken();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Gson gson = new Gson();
                    GeocodingResultVo geocodingResponse = gson.fromJson(response.toString(), GeocodingResultVo.class);
                    if(!mIsCancelled) {
                        onSuccess.execute(geocodingResponse);
                    }
                }, (e) -> {
                    if(!mIsCancelled) {
                        onError.execute(e);
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public void cancel() {
        mIsCancelled = true;
    }
}
