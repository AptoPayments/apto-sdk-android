package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.util.List;

public class TransactionsAdapter extends
        RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private Context mContext;
    private List<TransactionVo> mTransactions;
    private ViewListener mListener;
    private ManageCardModel mModel;

    TransactionsAdapter() {
        mContext = null;
        mTransactions = null;
        mModel = null;
    }

    public TransactionsAdapter(Context context, List<TransactionVo> transactions, ManageCardModel model) {
        mContext = context;
        mTransactions = transactions;
        mModel = model;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void manageCardClickHandler();
        void activateCardBySecondaryBtnClickHandler();
        void accountClickHandler();
        void cardNumberClickHandler(String cardNumber);
        void transactionClickHandler(int transactionId);
    }
    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Header
        CreditCardView creditCardView;
        TextView cardBalance;
        TextView cardBalanceLabel;
        TextView primaryButton;
        TextView secondaryButton;
        ImageView custodianLogo;
        ImageButton accountButton;
        TextView transactionsTitle;
        ImageView noTransactionsImage;
        TextView noTransactionsText;

        // Transaction
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView iconImageView;
        RelativeLayout transactionHolder;

        public ViewHolder(View itemView, int viewType) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            if (viewType == 0) {
                custodianLogo = itemView.findViewById(R.id.custodian_logo);
                creditCardView = itemView.findViewById(R.id.credit_card_view);
                cardBalance = itemView.findViewById(R.id.tv_card_balance);
                cardBalanceLabel = itemView.findViewById(R.id.tv_card_balance_label);
                primaryButton = itemView.findViewById(R.id.tv_display_card_primary_bttn);
                secondaryButton = itemView.findViewById(R.id.tv_display_card_secondary_bttn);
                accountButton = itemView.findViewById(R.id.ib_account);
                transactionsTitle = itemView.findViewById(R.id.tv_transactions_title);
                noTransactionsImage = itemView.findViewById(R.id.iv_no_transactions);
                noTransactionsText = itemView.findViewById(R.id.tv_no_transactions);
            } else if (viewType == 1) {
                titleTextView = itemView.findViewById(R.id.tv_title);
                descriptionTextView = itemView.findViewById(R.id.tv_description);
                iconImageView = itemView.findViewById(R.id.iv_icon);
                transactionHolder = itemView.findViewById(R.id.rl_transaction_holder);
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
            viewHolder.creditCardView.setExpiryDate(mModel.getExpirationDate());
            viewHolder.creditCardView.setCardNumber(mModel.getCardNumber());
            viewHolder.creditCardView.setCardName(mModel.getCardHolderName());
            viewHolder.creditCardView.setCVV(mModel.getCVV());
            viewHolder.creditCardView.setCardLogo(mModel.getCardNetwork());
            viewHolder.cardBalance.setText(mModel.getCardBalance());
            viewHolder.cardBalance.setTextColor(UIStorage.getInstance().getPrimaryColor());
            if(mModel.getCardBalance().isEmpty()) {
                viewHolder.cardBalanceLabel.setVisibility(View.GONE);
                viewHolder.cardBalance.setVisibility(View.GONE);
            }
            else {
                viewHolder.cardBalance.setText(mModel.getCardBalance());
                viewHolder.cardBalance.setTextColor(UIStorage.getInstance().getPrimaryColor());
                viewHolder.cardBalance.setVisibility(View.VISIBLE);
                viewHolder.cardBalanceLabel.setVisibility(View.VISIBLE);
            }
            ShiftPlatform.getImageLoader().load(UIStorage.getInstance().getContextConfig().logoURL, viewHolder.custodianLogo);
            viewHolder.creditCardView.setCardEnabled(mModel.isCardActivated());
            showActivateCardButton(mModel.isCardCreated(), viewHolder);

            if(mTransactions.size() == 0 && !mModel.isCardCreated()) {
                viewHolder.transactionsTitle.setVisibility(View.GONE);
                viewHolder.noTransactionsImage.setVisibility(View.VISIBLE);
                viewHolder.noTransactionsText.setVisibility(View.VISIBLE);
            }
            else {
                viewHolder.transactionsTitle.setVisibility(View.VISIBLE);
                viewHolder.noTransactionsImage.setVisibility(View.GONE);
                viewHolder.noTransactionsText.setVisibility(View.GONE);
            }

            setListeners(viewHolder);
        } else if (position > 0) {
            TransactionVo transaction = mTransactions.get(position-1);

            TextView titleTextView = viewHolder.titleTextView;
            titleTextView.setText(new AmountVo(transaction.localAmount.amount, transaction.localAmount.currency).toString());

            TextView descriptionTextView = viewHolder.descriptionTextView;
            descriptionTextView.setText(transaction.description);

            ImageView iconImageView = viewHolder.iconImageView;
            iconImageView.setImageDrawable(mContext.getDrawable(UIStorage.getInstance().getIcon(transaction.merchant.mcc.merchantCategoryIcon)));
            iconImageView.setColorFilter(Color.BLACK);
            viewHolder.transactionHolder.setOnClickListener(v -> mListener.transactionClickHandler(position-1));
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

    private void setListeners(ViewHolder viewHolder) {
        viewHolder.primaryButton.setOnClickListener(v -> mListener.manageCardClickHandler());
        viewHolder.creditCardView.setOnClickListener(v -> mListener.manageCardClickHandler());
        viewHolder.creditCardView.getCardNumberView().setOnClickListener(v -> mListener.cardNumberClickHandler(((EditText)v).getText().toString()));
        viewHolder.secondaryButton.setOnClickListener(v -> mListener.activateCardBySecondaryBtnClickHandler());
        viewHolder.accountButton.setOnClickListener(v -> mListener.accountClickHandler());
    }

    private void showActivateCardButton(boolean show, ViewHolder viewHolder) {
        if(show) {
            viewHolder.secondaryButton.setBackgroundColor(UIStorage.getInstance().getPrimaryColor());
            viewHolder.secondaryButton.setTextColor(UIStorage.getInstance().getPrimaryContrastColor());
            viewHolder.secondaryButton.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.secondaryButton.setVisibility(View.GONE);
        }
    }
}