package com.shiftpayments.link.sdk.ui.activities.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.TransactionDetailsPresenter;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;


/**
 * Created by adrian on 27/11/2017.
 */

public class TransactionDetailsActivity extends FragmentMvpActivity implements OnMapReadyCallback {

    public static final String EXTRA_TRANSACTION = "com.shiftpayments.link.sdk.ui.presenters.card.TRANSACTION";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;
    private TransactionDetailsPresenter mPresenter;

    /** {@inheritDoc} */
    @Override
    protected TransactionDetailsView createView() {
        return (TransactionDetailsView) View.inflate(this, R.layout.act_transaction_details, null);
    }

    /** {@inheritDoc} */
    @Override
    protected TransactionDetailsPresenter createPresenter(BaseDelegate delegate) {
        Intent intent = getIntent();
        TransactionVo transactionVo = intent.getParcelableExtra(EXTRA_TRANSACTION);
        mPresenter = new TransactionDetailsPresenter(this, transactionVo);
        return mPresenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mv_header);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mPresenter.setMap(map);
    }
}
