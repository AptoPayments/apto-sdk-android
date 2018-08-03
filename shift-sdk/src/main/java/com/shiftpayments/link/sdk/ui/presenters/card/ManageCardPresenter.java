package com.shiftpayments.link.sdk.ui.presenters.card;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.ActivateFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.DisableFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.EnableFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.FingerprintAuthenticationDialogFragment;
import com.shiftpayments.link.sdk.ui.utils.FingerprintDelegate;
import com.shiftpayments.link.sdk.ui.utils.FingerprintHandler;
import com.shiftpayments.link.sdk.ui.utils.SendEmailUtil;
import com.shiftpayments.link.sdk.ui.views.card.EndlessRecyclerViewScrollListener;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardBottomSheet;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardView;
import com.shiftpayments.link.sdk.ui.views.card.TransactionsAdapter;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.PinSupportFragment;

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
        ManageCardBottomSheet.ViewListener, FingerprintDelegate, TransactionsAdapter.ViewListener {

    private static final String DIALOG_FRAGMENT_TAG = "fingerprintFragment";
    private static final int ROWS = 20;

    private FragmentManager mFragmentManager;
    private ManageCardBottomSheet mManageCardBottomSheet;
    private FingerprintHandler mFingerprintHandler;
    private ManageCardActivity mActivity;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private TransactionsAdapter mTransactionsAdapter;
    private ArrayList mTransactionsList;
    private String mLastTransactionId;
    private boolean mIsUserAuthenticated;
    private ManageCardDelegate mDelegate;
    private Semaphore mSemaphore;
    private static final int NUMBER_OF_CONCURRENT_CALLS = 2;

    public ManageCardPresenter(FragmentManager fragmentManager, ManageCardActivity activity, ManageCardDelegate delegate) {
        mFragmentManager = fragmentManager;
        mActivity = activity;
        mFingerprintHandler = new FingerprintHandler(mActivity);
        mDelegate = delegate;
        mIsUserAuthenticated = false;
        mLastTransactionId = null;
        mSemaphore = new Semaphore(NUMBER_OF_CONCURRENT_CALLS);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(ManageCardView view) {
        super.attachView(view);
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
        mManageCardBottomSheet = new ManageCardBottomSheet();
        mManageCardBottomSheet.isCardEnabled = mModel.isCardActivated();
        mManageCardBottomSheet.showCardInfo = mModel.showCardInfo;
        mManageCardBottomSheet.setViewListener(this);
        mManageCardBottomSheet.show(mFragmentManager, mManageCardBottomSheet.getTag());
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

    @Override
    public void enableCardClickHandler(boolean enable) {
        hideBottomSheet();
        showCardStateChangeConfirmationDialog(enable);
    }

    @Override
    public void showCardInfoClickHandler(boolean show) {
        hideBottomSheet();
        if(!show) {
            mModel.showCardInfo = false;
            mTransactionsAdapter.notifyItemChanged(0);
        }
        else {
            if(mIsUserAuthenticated) {
                onUserAuthenticated();
            }
            else if(mFingerprintHandler.isFingerprintAuthPossible()) {
                FingerprintAuthenticationDialogFragment fragment
                        = new FingerprintAuthenticationDialogFragment();
                fragment.setFingerprintDelegate(this);
                fragment.setFingerprintHandler(mFingerprintHandler);
                fragment.show(mFragmentManager, DIALOG_FRAGMENT_TAG);
            }
            else {
                // TODO: show card info without any authentication
                mModel.showCardInfo = true;
                mTransactionsAdapter.notifyItemChanged(0);
            }
        }
    }

    @Override
    public void changePinClickHandler() {
        PinFragmentConfiguration config =
                new PinFragmentConfiguration(mActivity)
                        .pinSaver(pin -> {
                            mView.showPinFragment(false);
                            hideBottomSheet();
                            mView.showLoading(mActivity, true);
                            updateCardPin(pin);
                        });

        Fragment pinFragment = PinSupportFragment.newInstanceForCreation(config);
        hideBottomSheet();
        mFragmentManager.beginTransaction()
                .replace(R.id.pin_fragment, pinFragment)
                .commit();
        mView.showPinFragment(true);
    }

    @Override
    public void contactSupportClickHandler() {
        new SendEmailUtil(UIStorage.getInstance().getContextConfig().supportEmailAddress).execute(mActivity);
    }

    @Override
    public void onUserAuthenticated() {
        mIsUserAuthenticated = true;
        mModel.showCardInfo = true;
        mTransactionsAdapter.notifyItemChanged(0);
        mManageCardBottomSheet.setShowCardInfoSwitch(mModel.showCardInfo);
    }

    @Override
    public void onAuthenticationFailed(String error) {
        mIsUserAuthenticated = false;
        mModel.showCardInfo = false;
        ApiErrorUtil.showErrorMessage(error, mActivity);
        mManageCardBottomSheet.setShowCardInfoSwitch(mModel.showCardInfo);
    }

    /**
     * Called when the updatePin response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(UpdateFinancialAccountPinResponseVo card) {
        mView.showLoading(mActivity, false);
        Toast.makeText(mActivity, mActivity.getString(R.string.card_management_pin_changed), Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the enable card response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(EnableFinancialAccountResponseVo card) {
        showToastAndUpdateCard(card, mActivity.getString(R.string.card_enabled));
    }

    /**
     * Called when the disable card response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(DisableFinancialAccountResponseVo card) {
        showToastAndUpdateCard(card, mActivity.getString(R.string.card_disabled));
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
            mTransactionsAdapter.notifyItemChanged(0);
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
        CardStorage.getInstance().setFundingSourceId(response.id);
        mTransactionsAdapter.notifyItemChanged(0);
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
    }

    public static Intent getTransactionDetailsIntent(Context context, TransactionVo transactionVo) {
        Intent intent = new Intent(context, TransactionDetailsActivity.class);
        intent.putExtra(EXTRA_TRANSACTION, transactionVo);
        return intent;
    }

    private void changeCardState(boolean enable) {
        if(enable) {
            ShiftPlatform.enableFinancialAccount(mModel.getAccountId());
        }
        else {
            ShiftPlatform.disableFinancialAccount(mModel.getAccountId());
        }
        mView.showLoading(mActivity, true);
    }

    private void activateCard() {
        ShiftPlatform.activateFinancialAccount(mModel.getAccountId());
        mView.showLoading(mActivity, true);
    }

    private void showCardStateChangeConfirmationDialog(boolean enable) {
        String text = enable ? mActivity.getString(R.string.enable_card_message) : mActivity.getString(R.string.disable_card_message);

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(text)
                .setTitle(mActivity.getString(R.string.card_management_dialog_title));
        builder.setPositiveButton("YES", (dialog, id) -> changeCardState(enable));
        builder.setNegativeButton("NO", (dialog, id) -> {
            if (mManageCardBottomSheet != null) {
                mManageCardBottomSheet.setEnableCardSwitch(!enable);
            }
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showActivateCardConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(mActivity.getString(R.string.enable_card_message))
                .setTitle(mActivity.getString(R.string.card_management_dialog_title));
        builder.setPositiveButton("YES", (dialog, id) -> activateCard());
        builder.setNegativeButton("NO", (dialog, id) -> {
            if (mManageCardBottomSheet != null) {
                mManageCardBottomSheet.setEnableCardSwitch(false);
            }
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void hideBottomSheet() {
        mFragmentManager.beginTransaction()
                .detach(mManageCardBottomSheet)
                .commit();
    }

    private void updateCardPin(String pin) {
        UpdateFinancialAccountPinRequestVo request = new UpdateFinancialAccountPinRequestVo();
        request.pin = pin;
        request.accountId = mModel.getAccountId();
        ShiftPlatform.updateFinancialAccountPin(request);
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
        mTransactionsAdapter.notifyItemChanged(0);
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
