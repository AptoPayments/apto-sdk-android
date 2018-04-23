package com.shift.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shift.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.card.ManageCardModel;
import com.shift.link.sdk.ui.storages.UIStorage;

import java.util.HashMap;
import java.util.List;

public class TransactionsAdapter extends
        RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private Context mContext;
    private List<TransactionVo> mTransactions;
    private HashMap<String, Integer> mIconMap;
    private ViewListener mListener;
    private ManageCardModel mModel;

    TransactionsAdapter() {
        mContext = null;
        mTransactions = null;
        mIconMap = null;
        mModel = null;
    }

    public TransactionsAdapter(Context context, List<TransactionVo> transactions, ManageCardModel model) {
        mContext = context;
        mTransactions = transactions;
        mModel = model;
        mIconMap = new HashMap<>();
        mIconMap.put("plane", R.drawable.flights);
        mIconMap.put("car", R.drawable.car);
        mIconMap.put("glass", R.drawable.alcohol);
        mIconMap.put("finance", R.drawable.withdraw);
        mIconMap.put("food", R.drawable.food);
        mIconMap.put("gas", R.drawable.fuel);
        mIconMap.put("bed", R.drawable.hotel);
        mIconMap.put("medical", R.drawable.medicine);
        mIconMap.put("camera", R.drawable.other);
        mIconMap.put("card", R.drawable.bank_card);
        mIconMap.put("cart", R.drawable.purchases);
        mIconMap.put("road", R.drawable.toll_road);
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void manageCardClickHandler();
        void activateCardBySecondaryBtnClickHandler();
        void accountClickHandler();
        void cardNumberClickHandler(String cardNumber);
    }
    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Header
        public CreditCardView mCreditCardView;
        public TextView mCardBalance;
        public TextView mPrimaryButton;
        public TextView mSecondaryButton;
        public ImageView mCustodianLogo;
        public ImageButton mAccountButton;

        // Transaction
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView iconImageView;

        public ViewHolder(View itemView, int viewType) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            if (viewType == 0) {
                mCustodianLogo = (ImageView) itemView.findViewById(R.id.custodian_logo);
                mCreditCardView = (CreditCardView) itemView.findViewById(R.id.credit_card_view);
                mCardBalance = (TextView) itemView.findViewById(R.id.tv_card_balance);
                mPrimaryButton = (TextView) itemView.findViewById(R.id.tv_display_card_primary_bttn);
                mSecondaryButton = (TextView) itemView.findViewById(R.id.tv_display_card_secondary_bttn);
                mAccountButton = (ImageButton) itemView.findViewById(R.id.ib_account);
            } else if (viewType == 1) {
                titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
                descriptionTextView = (TextView) itemView.findViewById(R.id.tv_description);
                iconImageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            }
        }
    }

    // Overriden to display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if (position == 0) {
            // Header view
            viewType = 0;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0: // Header view
                View headerView = inflater.inflate(R.layout.include_card_management, parent, false);
                return new ViewHolder(headerView, viewType);
            default: // Transaction list
                View transactionView = inflater.inflate(R.layout.cv_transaction, parent, false);
                return new ViewHolder(transactionView, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (position == 0) {
            viewHolder.mCreditCardView.setExpiryDate(mModel.getExpirationDate());
            viewHolder.mCreditCardView.setCardNumber(mModel.getCardNumber());
            viewHolder.mCreditCardView.setCardName(mModel.getCardHolderName());
            viewHolder.mCreditCardView.setCVV(mModel.getCVV());
            viewHolder.mCreditCardView.setCardLogo(mModel.getCardNetwork());
            viewHolder.mCardBalance.setText(mModel.getCardBalance());
            viewHolder.mCardBalance.setTextColor(UIStorage.getInstance().getPrimaryColor());
            viewHolder.mCustodianLogo.setImageResource(R.drawable.coinbase_logo);
            if (mModel.isCardActivated()) {
                viewHolder.mCreditCardView.setCardEnabled(true);
                viewHolder.mSecondaryButton.setVisibility(View.GONE);
            } else {
                viewHolder.mCreditCardView.setCardEnabled(false);
                viewHolder.mSecondaryButton.setBackgroundColor(UIStorage.getInstance().getPrimaryColor());
                viewHolder.mSecondaryButton.setTextColor(UIStorage.getInstance().getPrimaryContrastColor());
                viewHolder.mSecondaryButton.setVisibility(View.VISIBLE);
            }

            viewHolder.mPrimaryButton.setOnClickListener(v -> mListener.manageCardClickHandler());
            viewHolder.mCreditCardView.setOnClickListener(v -> mListener.manageCardClickHandler());
            viewHolder.mCreditCardView.getCardNumberView().setOnClickListener(v -> mListener.cardNumberClickHandler(((EditText)v).getText().toString()));
            viewHolder.mSecondaryButton.setOnClickListener(v -> mListener.activateCardBySecondaryBtnClickHandler());
            viewHolder.mAccountButton.setOnClickListener(v -> mListener.accountClickHandler());
        } else if (position > 0) {
            TransactionVo transaction = mTransactions.get(position-1);

            TextView titleTextView = viewHolder.titleTextView;
            titleTextView.setText(String.valueOf(transaction.usdAmount));

            TextView descriptionTextView = viewHolder.descriptionTextView;
            descriptionTextView.setText(transaction.description);

            ImageView iconImageView = viewHolder.iconImageView;
            iconImageView.setImageDrawable(mContext.getDrawable(mIconMap.get(transaction.merchantCategoryIcon)));
            iconImageView.setColorFilter(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        if(mTransactions == null) {
            return 0;
        }
        return mTransactions.size()+1;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTransactions.clear();
        notifyDataSetChanged();
    }
}