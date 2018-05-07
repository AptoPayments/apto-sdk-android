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

import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.ManageAccountActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.FingerprintAuthenticationDialogFragment;
import com.shiftpayments.link.sdk.ui.utils.FingerprintDelegate;
import com.shiftpayments.link.sdk.ui.utils.FingerprintHandler;
import com.shiftpayments.link.sdk.ui.utils.SendEmailUtil;
import com.shiftpayments.link.sdk.ui.views.card.EndlessRecyclerViewScrollListener;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardBottomSheet;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardView;
import com.shiftpayments.link.sdk.ui.views.card.TransactionsAdapter;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.PinSupportFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import static com.shiftpayments.link.sdk.api.vos.Card.FinancialAccountState.ACTIVE;

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
    private boolean mHasTransactionListArrived = false;
    private boolean mHasFundingSourceArrived = false;
    private ManageCardDelegate mDelegate;

    public ManageCardPresenter(FragmentManager fragmentManager, ManageCardActivity activity, ManageCardDelegate delegate) {
        mFragmentManager = fragmentManager;
        mActivity = activity;
        mFingerprintHandler = new FingerprintHandler(mActivity);
        mDelegate = delegate;
        mIsUserAuthenticated = false;
        mLastTransactionId = null;
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
        ShiftPlatform.getFinancialAccountFundingSource(mModel.getAccountId());
        ShiftPlatform.getFinancialAccountTransactions(mModel.getAccountId(), ROWS, mLastTransactionId);
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
        showCardStateChangeConfirmationDialog(true);
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
    public void pullToRefreshHandler() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        mLastTransactionId = null;
        ShiftPlatform.getFinancialAccountTransactions(mModel.getAccountId(), ROWS, mLastTransactionId);
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
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
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
     * Called when the updateStatus response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(UpdateFinancialAccountResponseVo card) {
        if(isViewReady()) {
            mView.showLoading(mActivity, false);
        }
        String message = card.state.equals(ACTIVE) ? mActivity.getString(R.string.card_activated) : mActivity.getString(R.string.card_deactivated);
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        mModel.setCard(card);
        mTransactionsAdapter.notifyItemChanged(0);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        mView.showLoading(mActivity, false);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        if(error.statusCode==404) {
            // Card has no funding source
            mHasFundingSourceArrived = true;
            mModel.setBalance("");
            mTransactionsAdapter.notifyItemChanged(0);
        }
        else {
            Toast.makeText(mActivity, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when a session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        mView.showLoading(mActivity, false);
        mActivity.finish();
        mDelegate.onSessionExpired(error);
    }

    @Subscribe
    public void handleResponse(TransactionListResponseVo response) {
        mHasTransactionListArrived = true;
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
        if (response.data.length > 0) {
            mLastTransactionId = response.data[response.data.length-1].id;
            mTransactionsList.addAll(Arrays.asList(response.data));
            int currentSize = mTransactionsAdapter.getItemCount();
            mTransactionsAdapter.notifyItemRangeInserted(currentSize, response.total_count -1);
        }
    }

    @Subscribe
    public void handleResponse(FundingSourceVo response) {
        mHasFundingSourceArrived = true;
        if(isViewReady()) {
            mView.setRefreshing(false);
        }
        mModel.setBalance(String.valueOf(response.balance.amount));
        mTransactionsAdapter.notifyItemChanged(0);
    }

    private void changeCardState(boolean enable) {

        UpdateFinancialAccountRequestVo request = new UpdateFinancialAccountRequestVo();
        request.state = enable ? "active" : "inactive";

        ShiftPlatform.updateFinancialAccount(request, mModel.getAccountId());
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

    private void hideBottomSheet() {
        mFragmentManager.beginTransaction()
                .detach(mManageCardBottomSheet)
                .commit();
    }

    private void updateCardPin(String pin) {
        UpdateFinancialAccountPinRequestVo request = new UpdateFinancialAccountPinRequestVo();
        request.pin = pin;
        ShiftPlatform.updateFinancialAccountPin(request, mModel.getAccountId());
    }

    private boolean isViewReady() {
        return mView!=null && mHasFundingSourceArrived && mHasTransactionListArrived;
    }
}
