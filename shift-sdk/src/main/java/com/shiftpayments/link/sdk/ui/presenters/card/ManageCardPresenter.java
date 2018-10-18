package com.shiftpayments.link.sdk.ui.presenters.card;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.ActivateFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.CardSettingsActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.DateItem;
import com.shiftpayments.link.sdk.ui.models.card.HeaderItem;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.models.card.TransactionItem;
import com.shiftpayments.link.sdk.ui.models.card.TransactionListItem;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;
import com.shiftpayments.link.sdk.ui.views.card.EndlessRecyclerViewScrollListener;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardView;
import com.shiftpayments.link.sdk.ui.views.card.TransactionsAdapter;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

import static com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity.EXTRA_TRANSACTION;

/**
 * Concrete {@link Presenter} for the manage card screen.
 * @author Adrian
 */
public class ManageCardPresenter
        extends BasePresenter<ManageCardModel, ManageCardView>
        implements Presenter<ManageCardModel, ManageCardView>, ManageCardView.ViewListener,
        TransactionsAdapter.ViewListener {

    private static final int ROWS = 20;

    private ActionBar mActionBar;
    private ManageCardActivity mActivity;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private TransactionsAdapter mTransactionsAdapter;
    private ArrayList<TransactionListItem> mTransactionsList;
    private String mLastTransactionId;
    private ManageCardDelegate mDelegate;
    private Semaphore mSemaphore;
    private static final int NUMBER_OF_CONCURRENT_CALLS = 3;
    private String mTransactionCurrentMonth = "";
    private String mTransactionCurrentYear = "";
    private int mMostRecentCounter = 0;

    public ManageCardPresenter(ManageCardActivity activity, ManageCardDelegate delegate) {
        mActivity = activity;
        mDelegate = delegate;
        mLastTransactionId = null;
        mSemaphore = new Semaphore(NUMBER_OF_CONCURRENT_CALLS);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ManageCardView view) {
        super.attachView(view);
        setupToolbar();
        view.setViewListener(this);
        view.showLoading(mActivity, false);

        if(UIStorage.getInstance().isEmbeddedMode()) {
            mView.showCloseButton();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Retain an instance so that we can call `resetState()` for fresh searches
        mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getTransactions();
            }
        };
        mTransactionsList = new ArrayList<>();
        mTransactionsAdapter = new TransactionsAdapter(mActivity, mTransactionsList, mModel);
        mTransactionsAdapter.setViewListener(this);
        view.configureTransactionsView(linearLayoutManager, mScrollListener, mTransactionsAdapter);
        getCard();
        getFundingSource();
        getTransactions();
    }

    @Override
    public ManageCardModel createModel() {
        return new ManageCardModel(CardStorage.getInstance().getCard());
    }

    @Override
    public void manageCardClickHandler() {
        mActivity.startActivity(new Intent(mActivity, CardSettingsActivity.class));
    }

    @Override
    public void activateCardBySecondaryBtnClickHandler() {
        showActivateCardConfirmationDialog();
    }

    @Override
    public void accountClickHandler() {
        mActivity.startActivity(new Intent(mActivity, ManageAccountActivity.class));
    }

    @Override
    public void cardNumberClickHandler(String cardNumber) {
        if(!mModel.cardNumberShown()) {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("card number", cardNumber.replaceAll("\\s+",""));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mActivity, mActivity.getString(R.string.card_management_number_copied), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void transactionClickHandler(int transactionId) {
        TransactionItem transactionItem = (TransactionItem) mTransactionsList.get(transactionId);
        TransactionVo transaction = transactionItem.transaction;
        mActivity.startActivity(getTransactionDetailsIntent(mActivity, transaction));
    }

    @Override
    public void bannerAcceptButtonClickHandler() {
        if(CardStorage.getInstance().hasBalance()) {
            manageCardClickHandler();
        }
        else {
            mDelegate.addFundingSource(()->Toast.makeText(mActivity, R.string.account_management_funding_source_added, Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public void pullToRefreshHandler() {
        mLastTransactionId = null;
        mMostRecentCounter=0;
        mTransactionCurrentMonth="";
        mTransactionCurrentYear="";
        getFundingSource();
        getTransactions();
        getCard();
        mTransactionsAdapter.clear();
        mMostRecentCounter = 0;
        mScrollListener.resetState();
    }

    /**
     * Called when the activated card response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(ActivateFinancialAccountResponseVo card) {
        showToastAndUpdateCard(card, mActivity.getString(R.string.card_activated));
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        mView.showLoading(mActivity, false);
        mView.setRefreshing(false);
        if(error.request_path.equals(ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH) || error.request_path.equals(ShiftApiWrapper.FINANCIAL_ACCOUNT_BALANCE_PATH)) {
            mSemaphore.release();
        }
        if(error.statusCode==404) {
            // Card has no funding source
            mModel.setBalance(null);
            refreshCard();
        }
        else {
            ApiErrorUtil.showErrorMessage(error, mActivity);
        }
    }

    /**
     * Called when a session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        mView.showLoading(mActivity, false);
        mView.setRefreshing(false);
        mActivity.finish();
        mDelegate.onSessionExpired(error);
    }

    @Subscribe
    public void handleResponse(TransactionListResponseVo response) {
        mSemaphore.release();
        int currentSize = mTransactionsAdapter.getItemCount();
        if(currentSize==0) {
            mTransactionsList.add(new HeaderItem());
        }

        if(response.total_count <= 0) {
            mView.showNoTransactionsImage(true);
            mTransactionsAdapter.notifyItemChanged(0);
        }
        else {
            if(response.data.length>0) {
                mLastTransactionId = response.data[response.data.length-1].id;
                List<TransactionVo> transactionVoList = Arrays.asList(response.data);
                for(TransactionVo transactionVo : transactionVoList) {
                    if(mMostRecentCounter < 3) {
                        if(mMostRecentCounter==0) {
                            mTransactionsList.add(new DateItem(mActivity.getString(R.string.card_management_transactions_most_recent)));
                        }
                        mMostRecentCounter++;
                    }
                    else {
                        String transactionMonth = DateUtil.getMonthFromTimeStamp(transactionVo.creationTime);
                        String transactionYear = DateUtil.getYearFromTimeStamp(transactionVo.creationTime);
                        if(!mTransactionCurrentYear.equals(transactionYear)) {
                            mTransactionsList.add(new DateItem(DateUtil.getSimpleTransactionDate(transactionVo.creationTime)));
                            mTransactionCurrentMonth = transactionMonth;
                            mTransactionCurrentYear = transactionYear;
                        }
                        else if(!mTransactionCurrentMonth.equals(transactionMonth)) {
                            mTransactionsList.add(new DateItem(transactionMonth));
                            mTransactionCurrentMonth = transactionMonth;
                        }
                    }
                    mTransactionsList.add(new TransactionItem(transactionVo));
                }
            }
            mView.showNoTransactionsImage(false);
            mTransactionsAdapter.notifyItemRangeInserted(currentSize, response.total_count);
        }
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
    }

    @Subscribe
    public void handleResponse(BalanceVo response) {
        mSemaphore.release();
        CardStorage.getInstance().setBalance(response);
        setBalanceInModel(response);
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
    }

    @Subscribe
    public void handleResponse(FinancialAccountVo response) {
        mSemaphore.release();
        CardStorage.getInstance().setCard((Card) response);
        refreshCard();
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
    }

    public void refreshCard() {
        mModel.setCard(CardStorage.getInstance().getCard());
        mTransactionsAdapter.notifyItemChanged(0);
    }

    public void refreshView() {
        mModel.setCard(CardStorage.getInstance().getCard());
        BalanceVo balance = CardStorage.getInstance().getBalance();
        if(balance != null) {
            setBalanceInModel(balance);
            mTransactionsAdapter.notifyDataSetChanged();
        }
    }

    protected void setupToolbar() {
        mActivity.setSupportActionBar(mView.getToolbar());
        mActionBar = mActivity.getSupportActionBar();
        mActionBar.setTitle(mActivity.getString(R.string.card_management_title));
    }

    public static Intent getTransactionDetailsIntent(Context context, TransactionVo transactionVo) {
        Intent intent = new Intent(context, TransactionDetailsActivity.class);
        intent.putExtra(EXTRA_TRANSACTION, transactionVo);
        return intent;
    }

    private void activateCard() {
        ShiftPlatform.activateFinancialAccount(mModel.getAccountId());
        mView.showLoading(mActivity, true);
    }

    private void showActivateCardConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        String alertTitle = mActivity.getString(R.string.card_settings_dialog_title);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(UIStorage.getInstance().getTextPrimaryColor());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(alertTitle);
        spannableStringBuilder.setSpan(
                foregroundColorSpan,
                0,
                alertTitle.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setTitle(spannableStringBuilder);

        String alertMessage = mActivity.getString(R.string.enable_card_message);
        foregroundColorSpan = new ForegroundColorSpan(UIStorage.getInstance().getTextSecondaryColor());
        spannableStringBuilder = new SpannableStringBuilder(alertMessage);
        spannableStringBuilder.setSpan(
                foregroundColorSpan,
                0,
                alertMessage.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setMessage(spannableStringBuilder);

        builder.setPositiveButton("YES", (dialog, id) -> activateCard());
        builder.setNegativeButton("NO", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private boolean isViewReady() {
        return mView!=null && mSemaphore.availablePermits()==NUMBER_OF_CONCURRENT_CALLS;
    }

    private void showToastAndUpdateCard(Card card, String message) {
        if(isViewReady()) {
            mView.showLoading(mActivity, false);
        }
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        mModel.setCard(card);
        refreshCard();
    }

    private void getFundingSource() {
        try {
            mSemaphore.acquire();
            ShiftPlatform.getFinancialAccountFundingSource(mModel.getAccountId());
        } catch (InterruptedException e) {
            ApiErrorUtil.showErrorMessage(e.getMessage(), mActivity);
        }
    }

    private void getTransactions() {
        try {
            mSemaphore.acquire();
            ShiftPlatform.getFinancialAccountTransactions(mModel.getAccountId(), ROWS, mLastTransactionId);
        } catch (InterruptedException e) {
            ApiErrorUtil.showErrorMessage(e.getMessage(), mActivity);
        }
    }

    private void getCard() {
        try {
            mSemaphore.acquire();
            ShiftPlatform.getFinancialAccount(mModel.getAccountId());
        } catch (InterruptedException e) {
            ApiErrorUtil.showErrorMessage(e.getMessage(), mActivity);
        }
    }

    public void subscribeToEvents(boolean subscribe) {
        if(subscribe) {
            mResponseHandler.subscribe(this);
        }
        else {
            mResponseHandler.unsubscribe(this);
        }
    }

    private void setBalanceInModel(BalanceVo balanceVo) {
        if(balanceVo.balance!=null && balanceVo.balance.hasAmount()) {
            mModel.setBalance(new AmountVo(balanceVo.balance.amount, balanceVo.balance.currency));
            mModel.setBalanceState(balanceVo.state);
        }
        if(balanceVo.amountSpendable!=null && balanceVo.amountSpendable.hasAmount()) {
            mModel.setSpendableAmount(new AmountVo(balanceVo.amountSpendable.amount, balanceVo.amountSpendable.currency));
        }
        if(balanceVo.custodianWallet!=null && balanceVo.custodianWallet != null && balanceVo.custodianWallet.balance.hasAmount()) {
            mModel.setNativeBalance(new AmountVo(balanceVo.custodianWallet.balance.amount, balanceVo.custodianWallet.balance.currency));
        }
    }
}
