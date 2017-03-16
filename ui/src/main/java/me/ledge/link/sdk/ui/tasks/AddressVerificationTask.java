package me.ledge.link.sdk.ui.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.us_street.Client;
import com.smartystreets.api.us_street.Lookup;

import java.io.IOException;
import java.util.ArrayList;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;
import me.ledge.link.sdk.ui.presenters.userdata.VerifyAddressCallback;

/**
 * An {@link AsyncTask} to verify the address using SmartyStreets
 *
 */
public class AddressVerificationTask extends AsyncTask<String, Void, ArrayList<Candidate>> {

    private Context mContext;
    private AddressModel mModel;
    private VerifyAddressCallback mCallback;

    /**
     * Creates a new {@link AddressVerificationTask} instance.
     * @param context The context of the caller
     * @param model The model to set the result
     * @param callback The callback to be executed onPostExecute
     */
    public AddressVerificationTask(Context context, AddressModel model, VerifyAddressCallback callback) {
        mContext = context;
        mModel = model;
        mCallback = callback;
    }

    /**
     * Makes the requests.
     * {@inheritDoc}
     */
    @Override
    protected ArrayList<Candidate> doInBackground(String... params) {
        String address = params[0];
        Client client = new ClientBuilder(mContext.getString(R.string.smarty_streets_auth_id), mContext.getString(R.string.smarty_streets_auth_token))
                .buildUsStreetApiClient();

        Lookup lookup = new Lookup();
        lookup.setStreet(address);
        lookup.setCity(mModel.getCity());
        lookup.setState(mModel.getState());

        try {
            client.send(lookup);
        } catch (SmartyException | IOException e) {
            mCallback.execute(e);
        }

        return lookup.getResult();
    }

    /**
     * Publishes the results.
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(ArrayList<Candidate> result) {
        mModel.setIsAddressValid(!result.isEmpty());
        mCallback.execute(null);
    }
}
