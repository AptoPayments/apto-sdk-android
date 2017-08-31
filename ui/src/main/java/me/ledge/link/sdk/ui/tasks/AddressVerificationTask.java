package me.ledge.link.sdk.ui.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;
import me.ledge.link.sdk.ui.presenters.userdata.GeoApiCallback;

/**
 * An {@link AsyncTask} to verify the address using Google Maps API
 *
 */
public class AddressVerificationTask extends AsyncTask<String, Void, GeocodingResult> {

    private Context mContext;
    private AddressModel mModel;
    private GeoApiCallback mCallback;

    /**
     * Creates a new {@link AddressVerificationTask} instance.
     * @param context The context of the caller
     * @param model The model to set the result
     * @param callback The callback to be executed onPostExecute
     */
    public AddressVerificationTask(Context context, AddressModel model, GeoApiCallback callback) {
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
        String address = String.format("%s %s, %s %s",
                params[0], mModel.getCity(), mModel.getState(), mModel.getZip());

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(mContext.getString(R.string.google_maps_api_key))
                .build();
        GeocodingApiRequest req = GeocodingApi.newRequest(context).address(address);

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
        mModel.setIsAddressValid(isValidAddress(result));
        mCallback.execute(null);
    }

    private boolean isValidAddress(GeocodingResult result) {
        return result != null && !result.formattedAddress.isEmpty();
    }
}
