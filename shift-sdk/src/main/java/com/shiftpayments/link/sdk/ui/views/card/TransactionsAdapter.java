package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.card.DateItem;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.models.card.TransactionItem;
import com.shiftpayments.link.sdk.ui.models.card.TransactionListItem;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.util.List;

import static com.shiftpayments.link.sdk.ui.models.card.TransactionListItem.TYPE_DATE;
import static com.shiftpayments.link.sdk.ui.models.card.TransactionListItem.TYPE_HEADER;
import static com.shiftpayments.link.sdk.ui.models.card.TransactionListItem.TYPE_TRANSACTION;

public class TransactionsAdapter extends
        RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private Context mContext;
    private List<TransactionListItem> mTransactionListItems;
    private ViewListener mListener;
    private ManageCardModel mModel;

    TransactionsAdapter() {
        mContext = null;
        mTransactionListItems = null;
        mModel = null;
    }

    public TransactionsAdapter(Context context, List<TransactionListItem> transactions, ManageCardModel model) {
        mContext = context;
        mTransactionListItems = transactions;
        mModel = model;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void manageCardClickHandler();
        void accountClickHandler();
        void cardNumberClickHandler(String cardNumber);
        void transactionClickHandler(int transactionId);
        void bannerAcceptButtonClickHandler();
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
        TextView cardNativeBalance;
        TextView spendableAmount;
        TextView spendableAmountLabel;
        TextView spendableNativeAmount;
        TextView primaryButton;

        // Invalid Funding Source Banner
        RelativeLayout invalidFundingSourceBanner;
        TextView bannerAcceptButton;
        TextView bannerCancelButton;
        TextView bannerTitle;
        TextView bannerBody;

        // Transaction
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView iconImageView;
        TextView amountTextView;
        RelativeLayout transactionHolder;

        // Date
        TextView dateTextView;

        public ViewHolder(View itemView, int viewType) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            if (viewType == TYPE_HEADER) {
                creditCardView = itemView.findViewById(R.id.credit_card_view);
                cardBalance = itemView.findViewById(R.id.tv_current_balance);
                cardBalanceLabel = itemView.findViewById(R.id.tv_current_balance_label);
                cardNativeBalance = itemView.findViewById(R.id.tv_current_native_balance);
                spendableAmount = itemView.findViewById(R.id.tv_spendable_balance);
                spendableAmountLabel = itemView.findViewById(R.id.tv_card_spendable_balance_label);
                spendableNativeAmount = itemView.findViewById(R.id.tv_spendable_native_balance);
                primaryButton = itemView.findViewById(R.id.tv_display_card_primary_bttn);
                invalidFundingSourceBanner = itemView.findViewById(R.id.rl_invalid_funding_source_banner);
                bannerAcceptButton = itemView.findViewById(R.id.tv_banner_accept);
                bannerCancelButton = itemView.findViewById(R.id.tv_banner_cancel);
                bannerTitle = itemView.findViewById(R.id.tv_banner_title);
                bannerBody = itemView.findViewById(R.id.tv_banner_body);
            } else if (viewType == TYPE_TRANSACTION) {
                titleTextView = itemView.findViewById(R.id.tv_title);
                descriptionTextView = itemView.findViewById(R.id.tv_description);
                iconImageView = itemView.findViewById(R.id.iv_icon);
                transactionHolder = itemView.findViewById(R.id.rl_transaction_holder);
                amountTextView = itemView.findViewById(R.id.tv_transaction_amount);
            }
            else if (viewType == TYPE_DATE) {
                dateTextView = itemView.findViewById(R.id.tv_date_header_title);
            }
        }
    }

    // Overridden to display custom rows in the RecyclerView
    @Override
    public int getItemViewType(int position) {
        return mTransactionListItems.get(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TransactionsAdapter.ViewHolder viewHolder = null;
        switch (viewType) {
            case TransactionListItem.TYPE_HEADER:
                View headerView = inflater.inflate(R.layout.include_card_management, parent, false);
                viewHolder = new ViewHolder(headerView, viewType);
                break;
            case TransactionListItem.TYPE_DATE:
                View dateHeaderView = inflater.inflate(R.layout.cv_date_header, parent, false);
                viewHolder = new ViewHolder(dateHeaderView, viewType);
                break;
            case TYPE_TRANSACTION:
                View transactionView = inflater.inflate(R.layout.cv_transaction, parent, false);
                viewHolder = new ViewHolder(transactionView, viewType);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        setListeners(viewHolder, position);
        switch (viewHolder.getItemViewType()) {
            case TransactionListItem.TYPE_HEADER:
                viewHolder.creditCardView.setExpiryDate(mModel.getExpirationDate());
                viewHolder.creditCardView.setCardNumber(mModel.getCardNumber());
                viewHolder.creditCardView.setCardName(mModel.getCardHolderName());
                viewHolder.creditCardView.setCVV(mModel.getCVV());
                viewHolder.creditCardView.setCardLogo(mModel.getCardNetwork());
                viewHolder.creditCardView.setCardEnabled(true);

                if(!mModel.hasBalance()) {
                    setBalanceBannerTextToNoBalance(viewHolder);
                    viewHolder.creditCardView.showCardError();
                }
                else if(!mModel.isBalanceValid()) {
                    setBalanceBannerTextToInvalidBalance(viewHolder);
                    viewHolder.creditCardView.showCardError();
                }
                else if(mModel.isPhysicalCardActivationRequired()) {
                    setBalanceBannerTextToEnablePhysicalCard(viewHolder);
                    viewHolder.creditCardView.setCardEnabled(mModel.isCardActivated());
                }
                else {
                    showBalanceErrorBanner(false, viewHolder);
                    showCardBalance(mModel.hasBalance(), viewHolder);
                    showSpendableAmount(!mModel.getSpendableAmount().isEmpty(), viewHolder);
                    viewHolder.creditCardView.setCardEnabled(mModel.isCardActivated());
                }

                if(mModel.cardNumberShown()) {
                    setCopyCardNumberLabelText(viewHolder, mContext.getString(R.string.card_management_primary_button_full));
                }
                else {
                    setCopyCardNumberLabelText(viewHolder, mContext.getString(R.string.card_management_primary_button));
                }
                break;
            case TransactionListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) mTransactionListItems.get(position);
                viewHolder.dateTextView.setText(dateItem.date);
                break;
            case TYPE_TRANSACTION:
                TransactionItem transactionItem = (TransactionItem) mTransactionListItems.get(position);
                TransactionVo transaction = transactionItem.transaction;

                TextView titleTextView = viewHolder.titleTextView;
                titleTextView.setText(transaction.description);

                TextView descriptionTextView = viewHolder.descriptionTextView;
                String date = DateUtil.getFormattedTransactionDate(transaction.creationTime);
                descriptionTextView.setText(date);

                ImageView iconImageView = viewHolder.iconImageView;
                iconImageView.setImageDrawable(mContext.getDrawable(UIStorage.getInstance().getIcon(
                        transaction.merchant.mcc.merchantCategoryIcon)));
                iconImageView.setColorFilter(UIStorage.getInstance().getIconSecondaryColor());

                TextView amountTextView = viewHolder.amountTextView;
                amountTextView.setText(new AmountVo(transaction.localAmount.amount,
                        transaction.localAmount.currency).toString());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(mTransactionListItems == null) {
            return 0;
        }
        return mTransactionListItems.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mTransactionListItems.clear();
        notifyDataSetChanged();
    }

    private void setListeners(ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case TransactionListItem.TYPE_HEADER:
                viewHolder.primaryButton.setOnClickListener(v -> mListener.manageCardClickHandler());
                viewHolder.creditCardView.setOnClickListener(v -> mListener.manageCardClickHandler());
                viewHolder.creditCardView.getCardNumberView().setOnClickListener(
                        v -> mListener.cardNumberClickHandler(((EditText) v).getText().toString()));
                viewHolder.bannerAcceptButton.setOnClickListener(
                        v -> mListener.bannerAcceptButtonClickHandler());
                viewHolder.bannerCancelButton.setOnClickListener(
                        v -> {
                            showBalanceErrorBanner(false, viewHolder);
                            boolean showBalances = mModel.hasBalance() && mModel.isBalanceValid();
                            showCardBalance(showBalances, viewHolder);
                            showSpendableAmount(showBalances, viewHolder);
                        });
                break;
            case TYPE_TRANSACTION:
                viewHolder.transactionHolder.setOnClickListener(
                        v -> mListener.transactionClickHandler(position));
                break;
        }
    }

    private void showCardBalance(boolean show, ViewHolder viewHolder) {
        viewHolder.cardBalanceLabel.setVisibility(show ? View.VISIBLE : View.GONE);
        viewHolder.cardBalance.setText(mModel.getCardBalance());
        viewHolder.cardBalance.setVisibility(show ? View.VISIBLE : View.GONE);
        showCardNativeBalance(show && mModel.isNativeBalanceCurrencyDifferentFromLocalBalanceCurrency(), viewHolder);
    }

    private void showCardNativeBalance(boolean show, ViewHolder viewHolder) {
        viewHolder.cardNativeBalance.setText(mModel.getNativeBalance());
        viewHolder.cardNativeBalance.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showSpendableAmount(boolean show, ViewHolder viewHolder) {
        viewHolder.spendableAmountLabel.setVisibility(show ? View.VISIBLE : View.GONE);
        viewHolder.spendableAmount.setText(mModel.getSpendableAmount());
        viewHolder.spendableAmount.setVisibility(show ? View.VISIBLE : View.GONE);
        showNativeSpendableAmount(show && mModel.isSpendableAmountCurrencyDifferentFromNativeSpendableAmountCurrency(), viewHolder);
    }

    private void showNativeSpendableAmount(boolean show, ViewHolder viewHolder) {
        viewHolder.spendableNativeAmount.setText(mModel.getNativeSpendableAmount());
        viewHolder.spendableNativeAmount.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setCopyCardNumberLabelText(ViewHolder viewHolder, String text) {
        viewHolder.primaryButton.setText(text);
    }

    private void setBalanceBannerTextToInvalidBalance(ViewHolder viewHolder) {
        showBalanceErrorBanner(true, viewHolder);
        showCardBalance(false, viewHolder);
        showSpendableAmount(false, viewHolder);
        viewHolder.bannerTitle.setText(mContext.getString(R.string.invalid_funding_source_title));
        viewHolder.bannerBody.setText(mContext.getString(R.string.invalid_funding_source_body));
        viewHolder.bannerAcceptButton.setText(mContext.getString(R.string.invalid_funding_source_accept));
    }

    private void setBalanceBannerTextToNoBalance(ViewHolder viewHolder) {
        showBalanceErrorBanner(true, viewHolder);
        showCardBalance(false, viewHolder);
        showSpendableAmount(false, viewHolder);
        viewHolder.bannerTitle.setText(mContext.getString(R.string.no_funding_source_title));
        viewHolder.bannerBody.setText(mContext.getString(R.string.no_funding_source_body));
        viewHolder.bannerAcceptButton.setText(mContext.getString(R.string.no_funding_source_accept));
    }

    private void setBalanceBannerTextToEnablePhysicalCard(ViewHolder viewHolder) {
        showBalanceErrorBanner(true, viewHolder);
        showCardBalance(false, viewHolder);
        showSpendableAmount(false, viewHolder);
        viewHolder.bannerTitle.setText(mContext.getString(R.string.enable_physical_card_title));
        viewHolder.bannerBody.setText(mContext.getString(R.string.enable_physical_card_body));
        viewHolder.bannerAcceptButton.setText(mContext.getString(R.string.enable_physical_card_accept));
    }

    private void showBalanceErrorBanner(boolean show, ViewHolder viewHolder) {
        if(show) {
            viewHolder.bannerCancelButton.setTextColor(UIStorage.getInstance().getTextPrimaryColor());
            viewHolder.bannerAcceptButton.setTextColor(UIStorage.getInstance().getUiPrimaryColor());
            viewHolder.bannerTitle.setTextColor(UIStorage.getInstance().getTextSecondaryColor());
            viewHolder.bannerBody.setTextColor(UIStorage.getInstance().getTextPrimaryColor());
            viewHolder.invalidFundingSourceBanner.setBackgroundColor(UIStorage.getInstance().adjustColorAlpha(UIStorage.getInstance().getUiPrimaryColor(), 0.15f));
            viewHolder.invalidFundingSourceBanner.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.invalidFundingSourceBanner.setVisibility(View.GONE);
        }
    }
}