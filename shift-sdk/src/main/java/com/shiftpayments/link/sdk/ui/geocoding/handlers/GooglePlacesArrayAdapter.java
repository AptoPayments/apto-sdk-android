package com.shiftpayments.link.sdk.ui.geocoding.handlers;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.shiftpayments.link.sdk.ui.geocoding.vos.AutocompleteResponseVo;
import com.shiftpayments.link.sdk.ui.geocoding.vos.PredictionVo;

import java.util.ArrayList;
import java.util.Iterator;

public class GooglePlacesArrayAdapter
        extends ArrayAdapter<GooglePlacesArrayAdapter.PlaceAutocomplete> implements Filterable {
    private static final String TAG = "GooglePlacesAdapter";
    private DataFilter mDataFilter;
    private ArrayList<PlaceAutocomplete> mResultList;
    private AutocompleteHandler mAutocompleteHandler;
    private ArrayList<String> mAllowedCountries;

    /**
     * Constructor
     *
     * @param context  Context
     * @param resource Layout resource
     * @param allowedCountries   Used to filter by results by country
     */
    public GooglePlacesArrayAdapter(Context context, int resource, ArrayList<String> allowedCountries) {
        super(context, resource);
        mDataFilter = new DataFilter();
        mAutocompleteHandler = null;
        mAllowedCountries = allowedCountries;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    private void getPredictions(CharSequence constraint) {
        if (mAutocompleteHandler != null) {
            mAutocompleteHandler.cancel();
        }
        mAutocompleteHandler = new AutocompleteHandler();
        mAutocompleteHandler.getPredictions(getContext(), constraint.toString(), mAllowedCountries,
                (AutocompleteResponseVo response) -> {
                    mAutocompleteHandler = null;
                    final String status = response.status;
                    if (!status.equals("OK")) {
                        Log.e(TAG, "Autocomplete status: " + status);
                        return;
                    }

                    Iterator<PredictionVo> iterator = response.predictions.iterator();
                    ArrayList resultList = new ArrayList<PlaceAutocomplete>(response.predictions.size());
                    while (iterator.hasNext()) {
                        PredictionVo prediction = iterator.next();
                        resultList.add(new PlaceAutocomplete(prediction.placeId,
                                prediction.description));
                    }
                    mResultList = resultList;
                },
                (Exception e) -> {
                    mAutocompleteHandler = null;
                    Log.e(TAG, "Autocomplete error: " + e.getMessage());
                });
    }

    @Override
    public Filter getFilter() {
        return mDataFilter;
    }

    private class DataFilter extends Filter {
        @Override
        protected synchronized FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null) {
                // Query the autocomplete API for the entered constraint
                getPredictions(constraint);
                if (mResultList != null) {
                    // Results
                    results.values = mResultList;
                    results.count = mResultList.size();
                }
                else {
                    results.count = 0;
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                // The API returned at least one result, update the data.
                mResultList = (ArrayList<PlaceAutocomplete>) results.values;
                notifyDataSetChanged();
            } else {
                // The API did not return any results, invalidate the data set.
                notifyDataSetInvalidated();
            }
        }
    }

    public class PlaceAutocomplete {

        public CharSequence placeId;
        public CharSequence description;

        PlaceAutocomplete(CharSequence placeId, CharSequence description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }
    }
}