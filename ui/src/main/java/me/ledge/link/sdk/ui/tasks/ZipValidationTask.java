package me.ledge.link.sdk.ui.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.HomeModel;
import me.ledge.link.sdk.ui.presenters.userdata.GeoApiCallback;

/**
 * An {@link AsyncTask} to pre-fill city and state given a zip code using Google Maps API
 *
 */
public class ZipValidationTask extends AsyncTask<String, Void, GeocodingResult> {

    private Context mContext;
    private HomeModel mModel;
    private GeoApiCallback mCallback;

    /**
     * Creates a new {@link ZipValidationTask} instance.
     * @param context The context of the caller
     * @param model The model to set the result
     * @param callback The callback to be executed onPostExecute
     */
    public ZipValidationTask(Context context, HomeModel model, GeoApiCallback callback) {
        mContext = context;
        mModel = model;
        mCallback = callback;
    }

    /**
     * Makes the requests.
     * {@inheritDoc}
     */
    @Override
    protected GeocodingResult doInBackground(String... params) {
        String zipCode = params[0];

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(mContext.getString(R.string.google_maps_api_key))
                .build();
        GeocodingApiRequest req = GeocodingApi.newRequest(context).address(zipCode);

        try {
            GeocodingResult[] results = req.await();
            return results[0];
        } catch (Exception e) {
            mCallback.execute(e);
        }

        return null;
    }

    /**
     * Publishes the results.
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(GeocodingResult result) {
        for (AddressComponent component : result.addressComponents) {
            if(component.types[0] == AddressComponentType.LOCALITY) {
                mModel.setCity(component.longName);
            }
            else if(component.types[0] == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1) {
                mModel.setState(component.shortName);
            }
        }
        mCallback.execute(null);
    }

}
