package com.shiftpayments.link.sdk.ui.presenters.card;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.AdjustmentVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.TransactionDetailsModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;
import com.shiftpayments.link.sdk.ui.views.card.AdjustmentsAdapter;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;

import java.util.List;

/**
 * Concrete {@link Presenter} for the transaction details screen.
 * @author Adrian
 */
public class TransactionDetailsPresenter
        extends BasePresenter<TransactionDetailsModel, TransactionDetailsView>
        implements Presenter<TransactionDetailsModel, TransactionDetailsView> {

    private TransactionDetailsActivity mActivity;

    public TransactionDetailsPresenter(TransactionDetailsActivity activity, TransactionVo transaction) {
        mActivity = activity;
        mModel.setTransaction(transaction);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(TransactionDetailsView view) {
        super.attachView(view);
        mActivity.setSupportActionBar(mView.getToolbar());
        mView.setTitle(mModel.getDescription());
        mView.setSubtitle(mModel.getLocalAmount());
        String transactionDate = mModel.getTransactionDate();
        if(transactionDate != null) {
            mView.setTransactionDate(DateUtil.getLocaleFormattedDate(transactionDate));
        }
        mView.setShiftId(mModel.getShiftId());
        mView.setType(mModel.getTransactionType().toString());
        mView.setTransactionAddress(mModel.getLocation());
        mView.setCategory(mModel.getCategory());
        if(mModel.hasHoldAmount()) {
            mView.setHoldAmount(mModel.getHoldAmount());
        }
        if(mModel.hasTransactionId()) {
            mView.setTransactionId(mModel.getTransactionId());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        List<AdjustmentVo> transferList = mModel.getTransferList();
        AdjustmentsAdapter adapter = new AdjustmentsAdapter(transferList, mActivity);
        mView.configureAdjustmentsAdapter(linearLayoutManager, adapter);
    }

    @Override
    public TransactionDetailsModel createModel() {
        return new TransactionDetailsModel();
    }

    public void setMap(GoogleMap map) {
        if(mModel.hasTransactionCoordinates()) {
            Double latitude = mModel.getLatitude();
            Double longitude = mModel.getLongitude();
            map.getUiSettings().setMapToolbarEnabled(false);
            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions marker = new MarkerOptions()
                    .position(latLng)
                    .icon(getMapMarker(UIStorage.getInstance().getIcon(mModel.getMerchantCagetoryIcon())));

            map.addMarker(marker);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
        }
        else {
            mView.disableExpandingToolbar();
        }
    }

    private BitmapDescriptor getMapMarker(@DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(mActivity, R.drawable.map_marker_icon);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        background.setColorFilter(UIStorage.getInstance().getUiSecondaryColor(), PorterDuff.Mode.SRC_ATOP);

        Drawable merchantIcon = ContextCompat.getDrawable(mActivity, vectorDrawableResourceId);
        int backgroundWidth = background.getIntrinsicWidth();
        int backgroundHeight = background.getIntrinsicHeight();
        int merchantIconWidth = merchantIcon.getIntrinsicWidth() * 2;
        int merchantIconHeight = merchantIcon.getIntrinsicHeight() * 2;
        int marginLeft = (int) (backgroundWidth * 0.5 - merchantIconWidth * 0.5);
        int marginTop = (int) (backgroundHeight * 0.4 - merchantIconHeight * 0.5);
        merchantIcon.setBounds(marginLeft, marginTop, merchantIconWidth + marginLeft, merchantIconHeight + marginTop);
        merchantIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        merchantIcon.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
