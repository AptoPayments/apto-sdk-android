package com.shiftpayments.link.sdk.ui.presenters.card;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceListVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.DisableFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.EnableFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.CardSettingsActivity;
import com.shiftpayments.link.sdk.ui.adapters.fundingsources.FundingSourcesListRecyclerAdapter;
import com.shiftpayments.link.sdk.ui.models.card.BalanceModel;
import com.shiftpayments.link.sdk.ui.models.card.CardSettingsModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.AlertDialogUtil;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.FingerprintAuthenticationDialogFragment;
import com.shiftpayments.link.sdk.ui.utils.FingerprintDelegate;
import com.shiftpayments.link.sdk.ui.utils.FingerprintHandler;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.utils.SendEmailUtil;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.card.CardSettingsView;
import com.shiftpayments.link.sdk.ui.views.card.FundingSourceView;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.PinSupportFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.shiftpayments.link.sdk.ui.activities.DisplayContentActivity.getDisplayContentIntent;

/**
 * Concrete {@link Presenter} for the card settings screen.
 * @author Adrian
 */
public class CardSettingsPresenter
        extends BasePresenter<CardSettingsModel, CardSettingsView>
        implements FundingSourceView.ViewListener, CardSettingsView.ViewListener,
        FingerprintDelegate {

    private static final String DIALOG_FRAGMENT_TAG = "fingerprintFragment";

    private CardSettingsActivity mActivity;
    private FundingSourcesListRecyclerAdapter mAdapter;
    private CardSettingsDelegate mDelegate;
    private FragmentManager mFragmentManager;
    private FingerprintHandler mFingerprintHandler;
    private boolean mIsUserAuthenticated;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    public CardSettingsPresenter(CardSettingsActivity activity, CardSettingsDelegate delegate, FragmentManager fragmentManager) {
        mActivity = activity;
        mDelegate = delegate;
        mFragmentManager = fragmentManager;
        mIsUserAuthenticated = false;
        mFingerprintHandler = new FingerprintHandler(mActivity);
        mAdapter = null;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(CardSettingsView view) {
        super.attachView(view);
        view.setViewListener(this);
        mActivity.setSupportActionBar(mView.getToolbar());
        mActivity.getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.card_settings_title));
        mView.showAddFundingSourceButton(UIStorage.getInstance().showAddFundingSourceButton());
        mView.setShowCardInfoSwitch(CardStorage.getInstance().showCardInfo);
        mView.setEnableCardSwitch(!CardStorage.getInstance().getCard().isCardActivated());
        mView.showFaq(ConfigStorage.getInstance().getCardConfig().cardProduct.faq != null);
        mView.showCardholderAgreement(ConfigStorage.getInstance().getCardConfig().cardProduct.cardholderAgreement != null);
        mView.showTermsAndConditions(ConfigStorage.getInstance().getCardConfig().cardProduct.termsOfService != null);
        mView.showPrivacyPolicy(ConfigStorage.getInstance().getCardConfig().cardProduct.privacyPolicy != null);
        mView.showSetPinButton(CardStorage.getInstance().getCard().isSetPinEnabled());
        mView.showGetPinButton(CardStorage.getInstance().getCard().isGetPinEnabled());
        mResponseHandler.subscribe(this);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(true, LoadingView.Position.TOP, false);
        ShiftPlatform.getUserFundingSources(CardStorage.getInstance().getCard().mAccountId);
    }

    @Override
    public CardSettingsModel createModel() {
        return new CardSettingsModel(CardStorage.getInstance().getCard());
    }

    @Override
    public void balanceClickHandler(BalanceModel selectedBalance) {
        mLoadingSpinnerManager.showLoading(true, LoadingView.Position.TOP, false);
        List<BalanceModel> balances = mModel.getBalances().getList();
        for(BalanceModel balance : balances) {
            balance.setIsSelected(balance.equals(selectedBalance));
        }
        mAdapter.updateList(mModel.getBalances());
        mResponseHandler.subscribe(this);
        ShiftPlatform.setAccountFundingSource(CardStorage.getInstance().getCard().mAccountId, selectedBalance.getBalanceId());
    }


    @Override
    public void addFundingSource() {
        mDelegate.addFundingSource(()->Toast.makeText(mActivity, R.string.account_management_funding_source_added, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void changePinClickHandler() {
        PinFragmentConfiguration config =
                new PinFragmentConfiguration(mActivity)
                        .pinSaver(pin -> {
                            mView.showPinFragment(false);
                            updateCardPin(pin);
                        });

        Fragment pinFragment = PinSupportFragment.newInstanceForCreation(config);
        mFragmentManager.beginTransaction()
                .replace(R.id.pin_fragment, pinFragment)
                .commit();
        mView.showPinFragment(true);
    }

    @Override
    public void getPinClickHandler() {
        mDelegate.onGetPinClickHandler();
    }

    @Override
    public void reportStolenCardClickHandler() {
        final String alertTitle = mActivity.getString(R.string.contact_support_alert_title);
        final String alertMessage = mActivity.getString(R.string.contact_support_alert_message);
        final AlertDialogUtil dialogUtil = new AlertDialogUtil(mActivity);
        final AlertDialog dialog = dialogUtil.getAlertDialog(alertTitle, alertMessage,
                ()-> {
                    changeCardState(false);
                    new SendEmailUtil(UIStorage.getInstance().getContextConfig().supportEmailAddress).execute(mActivity);
                },
                ()-> {

                });
        dialog.show();
    }

    @Override
    public void showCardInfoClickHandler(boolean show) {
        if(!show) {
            CardStorage.getInstance().showCardInfo = false;
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
                CardStorage.getInstance().showCardInfo = true;
            }
        }
    }

    @Override
    public void disableCardClickHandler(boolean disable) {
        if(!disable) {
            changeCardState(true);
        }
        else {
            showCardStateChangeConfirmationDialog();
        }
    }

    @Override
    public void faqClickHandler() {
        ContentVo content = ConfigStorage.getInstance().getCardConfig().cardProduct.faq;
        mActivity.startActivity(getDisplayContentIntent(mActivity, content));
    }

    @Override
    public void cardholderAgreementClickHandler() {
        ContentVo content = ConfigStorage.getInstance().getCardConfig().cardProduct.cardholderAgreement;
        mActivity.startActivity(getDisplayContentIntent(mActivity, content));
    }

    @Override
    public void termsAndConditionsClickHandler() {
        ContentVo content = ConfigStorage.getInstance().getCardConfig().cardProduct.termsOfService;
        mActivity.startActivity(getDisplayContentIntent(mActivity, content));
    }

    @Override
    public void privacyPolicyClickHandler() {
        ContentVo content = ConfigStorage.getInstance().getCardConfig().cardProduct.privacyPolicy;
        mActivity.startActivity(getDisplayContentIntent(mActivity, content));
    }

    @Override
    public void onUserAuthenticated() {
        mIsUserAuthenticated = true;
        CardStorage.getInstance().showCardInfo = true;
        mView.setShowCardInfoSwitch(true);
    }

    @Override
    public void onAuthenticationFailed(String error) {
        mIsUserAuthenticated = false;
        CardStorage.getInstance().showCardInfo = false;
        ApiErrorUtil.showErrorMessage(error, mActivity);
        mView.setShowCardInfoSwitch(false);
    }

    @Subscribe
    public void handleResponse(BalanceListVo response) {
        mResponseHandler.unsubscribe(this);
        if(mAdapter==null) {
            mAdapter = new FundingSourcesListRecyclerAdapter();
            mAdapter.setViewListener(this);
            mView.setAdapter(mAdapter);
        }
        mModel.addBalances(response.data);
        mView.showFundingSourceLabel(true);
        mAdapter.updateList(mModel.getBalances());
        mLoadingSpinnerManager.showLoading(false);
    }

    @Subscribe
    public void handleResponse(BalanceVo balance) {
        mResponseHandler.unsubscribe(this);
        CardStorage.getInstance().setBalance(balance);
        mAdapter.updateList(mModel.getBalances());
        mLoadingSpinnerManager.showLoading(false);
        Toast.makeText(mActivity, R.string.account_management_funding_source_changed, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the updatePin response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(UpdateFinancialAccountPinResponseVo card) {
        mResponseHandler.unsubscribe(this);
        mLoadingSpinnerManager.showLoading(false);
        Toast.makeText(mActivity, mActivity.getString(R.string.card_management_pin_changed), Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the enable card response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(EnableFinancialAccountResponseVo card) {
        mResponseHandler.unsubscribe(this);
        mLoadingSpinnerManager.showLoading(false);
        showToastAndUpdateCard(card, mActivity.getString(R.string.card_enabled));
        mView.setEnableCardSwitch(!CardStorage.getInstance().getCard().isCardActivated());
    }

    /**
     * Called when the disable card response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(DisableFinancialAccountResponseVo card) {
        mResponseHandler.unsubscribe(this);
        mLoadingSpinnerManager.showLoading(false);
        showToastAndUpdateCard(card, mActivity.getString(R.string.card_disabled));
        mView.setEnableCardSwitch(!CardStorage.getInstance().getCard().isCardActivated());
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        mResponseHandler.unsubscribe(this);
        mLoadingSpinnerManager.showLoading(false);
        if(error.statusCode==404) {
            // User has no funding source
            mView.showFundingSourceLabel(false);
            mAdapter.updateList(mModel.getBalances());
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
        mResponseHandler.unsubscribe(this);
        mActivity.finish();
        mDelegate.onSessionExpired(error);
    }

    private void updateCardPin(String pin) {
        mLoadingSpinnerManager.showLoading(true);
        mResponseHandler.subscribe(this);
        UpdateFinancialAccountPinRequestVo request = new UpdateFinancialAccountPinRequestVo();
        request.pin = pin;
        request.accountId = mModel.getAccountId();
        ShiftPlatform.updateFinancialAccountPin(request);
    }

    private void showCardStateChangeConfirmationDialog() {
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

        String alertMessage = mActivity.getString(R.string.disable_card_message);
        foregroundColorSpan = new ForegroundColorSpan(UIStorage.getInstance().getTextSecondaryColor());
        spannableStringBuilder = new SpannableStringBuilder(alertMessage);
        spannableStringBuilder.setSpan(
                foregroundColorSpan,
                0,
                alertMessage.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setMessage(spannableStringBuilder);

        builder.setPositiveButton("YES", (dialog, id) -> changeCardState(false));
        builder.setNegativeButton("NO", (dialog, id) -> {
            mView.setEnableCardSwitch(!CardStorage.getInstance().getCard().isCardActivated());
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(UIStorage.getInstance().getTextPrimaryColor());
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(UIStorage.getInstance().getUiPrimaryColor());
        });
        dialog.show();
    }

    private void changeCardState(boolean enable) {
        mLoadingSpinnerManager.showLoading(true);
        mResponseHandler.subscribe(this);
        if(enable) {
            ShiftPlatform.enableFinancialAccount(mModel.getAccountId());
        }
        else {
            ShiftPlatform.disableFinancialAccount(mModel.getAccountId());
        }
    }

    private void showToastAndUpdateCard(Card card, String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        CardStorage.getInstance().setCard(card);
    }
}
