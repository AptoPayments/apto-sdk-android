package com.shiftpayments.link.sdk.ui.presenters.card;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.ActivateFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.CardSettingsActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.views.card.EndlessRecyclerViewScrollListener;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardView;
import com.shiftpayments.link.sdk.ui.views.card.TransactionsAdapter;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList mTransactionsList;
    private String mLastTransactionId;
    private ManageCardDelegate mDelegate;
    private Semaphore mSemaphore;
    private static final int NUMBER_OF_CONCURRENT_CALLS = 2;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Retain an instance so that we can call `resetState()` for fresh searches
        mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                ShiftPlatform.getFinancialAccountTransactions(mModel.getAccountId(), ROWS, mLastTransactionId);
            }
        };
        mTransactionsList = new ArrayList<TransactionVo>();
        mTransactionsAdapter = new TransactionsAdapter(mActivity, mTransactionsList, mModel);
        mTransactionsAdapter.setViewListener(this);
        view.configureTransactionsView(linearLayoutManager, mScrollListener, mTransactionsAdapter);
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        getFundingSource();
        getTransactions();
    }

    @Override
    public void detachView() {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        super.detachView();
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
        mActivity.overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void cardNumberClickHandler(String cardNumber) {
        ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("card number", cardNumber.replaceAll("\\s+",""));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mActivity, mActivity.getString(R.string.card_management_number_copied), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void transactionClickHandler(int transactionId) {
        TransactionVo transaction = (TransactionVo) mTransactionsList.get(transactionId);
        mActivity.startActivity(getTransactionDetailsIntent(mActivity, transaction));
    }

    @Override
    public void pullToRefreshHandler() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        mLastTransactionId = null;
        getFundingSource();
        getTransactions();
        mTransactionsAdapter.clear();
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
        if(error.request_path.equals(ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH) || error.request_path.equals(ShiftApiWrapper.FINANCIAL_ACCOUNT_FUNDING_SOURCE_PATH)) {
            mSemaphore.release();
        }
        if(error.statusCode==404) {
            // Card has no funding source
            mModel.setBalance(null);
            updateCard();
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
        mTransactionsList.addAll(Arrays.asList(response.data));
        int currentSize = mTransactionsAdapter.getItemCount();
        if(response.total_count <= 0) {
            mView.showNoTransactionsImage(true);
        }
        else {
            mLastTransactionId = response.data[response.data.length-1].id;
            mView.showNoTransactionsImage(false);
        }
        mTransactionsAdapter.notifyItemRangeInserted(currentSize, response.total_count -1);
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
    }

    @Subscribe
    public void handleResponse(FundingSourceVo response) {
        mSemaphore.release();
        if(response.balance.hasAmount()) {
            mModel.setBalance(new AmountVo(response.balance.amount, response.balance.currency));
        }
        if(response.amountSpendable.hasAmount()) {
            mModel.setSpendableAmount(new AmountVo(response.amountSpendable.amount, response.amountSpendable.currency));
        }
        if(response.custodianWallet != null && response.custodianWallet.balance.hasAmount()) {
            mModel.setNativeBalance(new AmountVo(response.custodianWallet.balance.amount, response.custodianWallet.balance.currency));
        }
        CardStorage.getInstance().setFundingSourceId(response.id);
        updateCard();
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
    }

    public void updateCard() {
        mModel.setCard(CardStorage.getInstance().getCard());
        mTransactionsAdapter.notifyItemChanged(0);
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
        builder.setMessage(mActivity.getString(R.string.enable_card_message))
                .setTitle(mActivity.getString(R.string.card_settings_dialog_title));
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
        updateCard();
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
}
