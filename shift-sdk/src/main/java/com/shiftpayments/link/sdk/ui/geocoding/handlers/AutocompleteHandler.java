package com.shiftpayments.link.sdk.ui.geocoding.handlers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.shiftpayments.link.imageloaders.volley.VolleySingleton;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.geocoding.vos.AutocompleteResponseVo;

import java.util.ArrayList;

/**
 * Created by adrian on 04/10/2018.
 */

public class AutocompleteHandler {
    private boolean mIsCancelled;

    public AutocompleteHandler() {
        mIsCancelled = false;
    }

    public void getPredictions(Context context, String input, ArrayList<String> allowedCountries,
                               GetPredictionsOnSuccessCallback onSuccess, GetPredictionsOnErrorCallback onError) {
        if(mIsCancelled) {
            return;
        }
        String url = context.getString(R.string.google_places_autocomplete_api_url);
        url+="?input="+input;

        if(allowedCountries != null) {
            StringBuilder urlBuilder = new StringBuilder(url);
            urlBuilder.append("&components=");
            for (String country : allowedCountries) {
                urlBuilder.append("country:").append(country).append("|");
            }
            urlBuilder.deleteCharAt(urlBuilder.length()-1);
            url = urlBuilder.toString();
        } else {
            url+="&components=country:US";
        }
        url+="&key="+context.getString(R.string.geocoding_google_maps_api_key);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Gson gson = new Gson();
                    AutocompleteResponseVo autocompleteResponse = gson.fromJson(response.toString(), AutocompleteResponseVo.class);
                    if(!mIsCancelled) {
                        onSuccess.execute(autocompleteResponse);
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
