package me.ledge.link.sdk.ui.geocoding.handlers;

import android.content.Context;
import me.ledge.link.sdk.ui.R;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import me.ledge.link.imageloaders.volley.VolleySingleton;
import me.ledge.link.sdk.ui.geocoding.vos.GeocodingResultVo;

/**
 * Created by adrian on 01/09/2017.
 */

public class GeocodingHandler {

    public static void reverseGeocode(Context context, String address, String country, GeocodingOnSuccessCallback onSuccess, GeocodingOnErrorCallback onError) {
        String url = context.getString(R.string.google_maps_api_url);
        url+="?address="+address;

        if(country != null) {
            url+="&components=country:"+country;
        } else {
            url+="&components=country:US";
        }
        url+="&key="+context.getString(R.string.google_maps_api_key);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Gson gson = new Gson();
                    GeocodingResultVo geocodingResponse = gson.fromJson(response.toString(), GeocodingResultVo.class);
                    onSuccess.execute(geocodingResponse);
                }, onError::execute);

        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
