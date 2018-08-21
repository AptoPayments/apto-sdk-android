package com.shiftpayments.link.sdk.ui.geocoding.handlers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.shiftpayments.link.imageloaders.volley.VolleySingleton;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.geocoding.vos.GeocodingResultVo;

/**
 * Created by adrian on 01/09/2017.
 */

public class GeocodingHandler {
    private boolean mIsCancelled;

    public GeocodingHandler() {
        mIsCancelled = false;
    }

    public void reverseGeocode(Context context, String address, String country, GeocodingOnSuccessCallback onSuccess, GeocodingOnErrorCallback onError) {
        if(mIsCancelled) {
            return;
        }
        String url = context.getString(R.string.geocoding_google_maps_api_url);
        url+="?address="+address;

        if(country != null) {
            url+="&components=country:"+country;
        } else {
            url+="&components=country:US";
        }
        url+="&key="+context.getString(R.string.geocoding_google_maps_api_key);

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
