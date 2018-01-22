package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import java.util.LinkedList;
import java.util.Set;

import me.ledge.link.api.vos.datapoints.Address;
import me.ledge.link.api.vos.datapoints.CreditScore;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Housing;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.api.vos.datapoints.IncomeSource;
import me.ledge.link.api.vos.datapoints.PersonalName;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.utils.CreditScoreUtil;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the application summary.
 * @author Adrian
 */
public class LoanApplicationSummaryView
        extends RelativeLayout
        implements ViewWithToolbar, ViewWithIndeterminateLoading, View.OnClickListener {

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener extends ObservableScrollViewCallbacks {

        /**
         * Called when the "confirm" button has been pressed.
         */
        void confirmClickHandler();
    }

    private Toolbar mToolbar;
    private ObservableScrollView mScrollView;
    private LoadingView mLoadingView;

    private ImageView mLenderLogo;
    private TextView mLenderNameField;
    private TextView mLoanInterestField;
    private TextView mLoanAmountField;
    private TextView mLoanDurationField;
    private TextView mPaymentField;
    private TextView mConfirmButton;
    private TextView mDisclaimer;
    private TextView mDisclaimerHeader;

    private ViewListener mViewListener;
    private int mMaxScroll;
    private int mBackgroundColor;
    private int mForegroundColor;
    private String mScrollMoreCopy;
    private String mAgreeCopy;
    private LinearLayout mApplicationInfoLinearLayout;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public LoanApplicationSummaryView(Context context) {
        this(context, null);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public LoanApplicationSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mMaxScroll = -1;

        mForegroundColor = UIStorage.getInstance().getPrimaryContrastColor();
        mBackgroundColor = UIStorage.getInstance().getPrimaryColor();

        mScrollMoreCopy = getResources().getString(R.string.loan_agreement_button_scroll_down);
        mAgreeCopy = getResources().getString(R.string.loan_confirmation_button_agree);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mScrollView = (ObservableScrollView) findViewById(R.id.osv_scroll);
        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);

        mLenderLogo = (ImageView) findViewById(R.id.iv_lender_logo);
        mLenderNameField = (TextView) findViewById(R.id.tv_lender_name);
        mLoanInterestField = (TextView) findViewById(R.id.tv_loan_interest_rate);
        mLoanAmountField = (TextView) findViewById(R.id.tv_loan_amount);
        mLoanDurationField = (TextView) findViewById(R.id.tv_loan_duration);
        mPaymentField = (TextView) findViewById(R.id.tv_payment);
        mConfirmButton = (TextView) findViewById(R.id.tv_confirm_btn);
        mDisclaimer = (TextView) findViewById(R.id.tv_application_disclaimer);
        mDisclaimerHeader = (TextView) findViewById(R.id.tv_borrower_agreement_header);
        mApplicationInfoLinearLayout = (LinearLayout) findViewById(R.id.lv_loanSummary_applicationInfo);
    }

    /**
     * Sets up all required callback listeners.
     */
    private void setUpListeners() {
        mConfirmButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColors();
    }

    private void setColors() {
        mToolbar.setBackgroundDrawable(new ColorDrawable(mBackgroundColor));
        mToolbar.setTitleTextColor(mForegroundColor);
        ((TextView) findViewById(R.id.tv_terms_header)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.tv_borrower_agreement_header)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.tv_loan_confirmation_application_info_header)).setTextColor(Color.BLACK);
        mLenderNameField.setTextColor(Color.BLACK);
        mLoanInterestField.setTextColor(Color.BLACK);
        mLoanDurationField.setTextColor(Color.BLACK);
        mLoanAmountField.setTextColor(Color.BLACK);
        mPaymentField.setTextColor(Color.BLACK);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mViewListener != null) {
            mViewListener.confirmClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new reference to a {@link ViewListener} that will be invoked by this View.
     * @param listener {@link ViewListener} - The new {@link ViewListener} to store.
     */
    public void setViewListener(ViewListener listener) {
        mViewListener = listener;
        mScrollView.setScrollViewCallbacks(listener);
    }

    /**
     * Updates this View with the latest data.
     * @param data New data.
     */
    public void setData(LoanApplicationSummaryModel data) {
        if (data == null) {
            return;
        }

        mLenderLogo.setVisibility(GONE);
        mLenderNameField.setVisibility(GONE);

        if (data.hasImageLoader() && data.getLenderImage() != null) {
            data.getImageLoader().load(data.getLenderImage(), mLenderLogo);
            mLenderLogo.setVisibility(VISIBLE);
        } else {
            mLenderNameField.setText(data.getLenderName());
            mLenderNameField.setVisibility(VISIBLE);
        }

        mLoanInterestField.setText(data.getInterestRate(getResources()));
        mLoanAmountField.setText(data.getTotalAmount(getResources()));
        mLoanDurationField.setText(data.getTerm(getResources()));
        mPaymentField.setText(data.getPaymentAmount(getResources()));

        addKeyValueTextView(data.getLoanPurposeLabel(getResources()), data.getLoanPurpose());

        DataPointList dataPointList = data.getApplicationInfo();
        Set<DataPointVo.DataPointType> dataPointTypes = dataPointList.getDataPoints().keySet();
        LinkedList<RequiredDataPointVo> requiredData = data.getRequiredData();

        for (RequiredDataPointVo requiredDataPointVo : requiredData) {
            for(DataPointVo.DataPointType currentType : dataPointTypes) {
                if (requiredDataPointVo.type.equals(currentType)) {
                    DataPointVo dataPoint = dataPointList.getUniqueDataPoint(currentType, null);
                    setTextViewValues(data, dataPoint);
                }
            }
        }
    }

    private void setTextViewValues(LoanApplicationSummaryModel data, DataPointVo dataPoint) {
        switch(dataPoint.getType()) {
            case PersonalName:
                addKeyValueTextView(data.getFirstNameLabel(getResources()), ((PersonalName) dataPoint).firstName);
                addKeyValueTextView(data.getLastNameLabel(getResources()), ((PersonalName) dataPoint).lastName);
                break;
            case Phone:
                addKeyValueTextView(data.getPhoneLabel(getResources()), dataPoint.toString());
                break;
            case Email:
                addKeyValueTextView(data.getEmailLabel(getResources()), dataPoint.toString());
                break;
            case BirthDate:
                addKeyValueTextView(data.getBirthdayLabel(getResources()), dataPoint.toString());
                break;
            case SSN:
                addKeyValueTextView(data.getSsnLabel(getResources()), dataPoint.toString());
                break;
            case Address:
                addKeyValueTextView(data.getAddressLabel(getResources()), ((Address) dataPoint).address);
                addKeyValueTextView(data.getAptUnitLabel(getResources()), ((Address) dataPoint).apUnit);
                addKeyValueTextView(data.getZipCodeLabel(getResources()), ((Address) dataPoint).zip);
                addKeyValueTextView(data.getCityLabel(getResources()), ((Address) dataPoint).city);
                addKeyValueTextView(data.getStateLabel(getResources()), ((Address) dataPoint).stateCode);
                break;
            case Housing:
                addKeyValueTextView(data.getHousingStatusLabel(getResources()), ((Housing) dataPoint).housingType.toString());
                break;
            case IncomeSource:
                addKeyValueTextView(data.getIncomeTypeLabel(getResources()), ((IncomeSource) dataPoint).incomeType.toString());
                addKeyValueTextView(data.getSalaryFrequencyLabel(getResources()), ((IncomeSource) dataPoint).salaryFrequency.toString());
                break;
            case Income:
                addKeyValueTextView(data.getAnnualIncomeLabel(getResources()), String.valueOf(((Income) dataPoint).annualGrossIncome));
                addKeyValueTextView(data.getMonthlyIncomeLabel(getResources()), String.valueOf(((Income) dataPoint).monthlyNetIncome));
                break;
            case CreditScore:
                String creditScore = CreditScoreUtil.getCreditScoreDescription(getContext(), ((CreditScore) dataPoint).creditScoreRange);
                addKeyValueTextView(data.getCreditScoreLabel(getResources()), creditScore);
                break;
        }
    }

    private void addKeyValueTextView(String key, String value) {
        if(key==null || value==null || key.isEmpty() || value.isEmpty()) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.lv_datapoint, mApplicationInfoLinearLayout, false);
        TextView dataPointKeyField = (TextView) view.findViewById(R.id.tv_datapoint_key);
        TextView dataPointValueField = (TextView) view.findViewById(R.id.tv_datapoint_value);
        dataPointKeyField.setText(key);
        dataPointValueField.setText(value);
        mApplicationInfoLinearLayout.addView(view);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * @return int - The maximum amount the {@code mScrollView} can scroll.
     */
    public int getMaxScroll() {
        if (mMaxScroll <= 0) {
            mMaxScroll = mScrollView.getChildAt(0).getHeight() - mScrollView.getHeight();
        }

        return mMaxScroll;
    }

    /**
     * @return int - The current vertical scroll position of the {@code mScrollView}.
     */
    public int getCurrentScroll() {
        int scroll = -1;
        if (mScrollView != null) {
            scroll = mScrollView.getCurrentScrollY();
        }

        return scroll;
    }

    /**
     * Scrolls the TCs text to the bottom.
     */
    public void scrollToBottom() {
        mScrollView.smoothScrollTo(0, getMaxScroll());
    }

    /**
     * Updates the looks of the bottom button.
     * @param fullyScrolled boolean - Whether the View is fully scrolled.
     */
    public void updateBottomButton(boolean fullyScrolled) {
        if (mConfirmButton == null) {
            return;
        }

        if (fullyScrolled) {
            mConfirmButton.setBackgroundColor(mBackgroundColor);
            mConfirmButton.setTextColor(mForegroundColor);
            mConfirmButton.setText(mAgreeCopy);
        } else {
            mConfirmButton.setBackgroundColor(mForegroundColor);
            mConfirmButton.setTextColor(mBackgroundColor);
            mConfirmButton.setText(mScrollMoreCopy);
        }
    }

    public void setDisclaimer(String disclaimer) {
        mDisclaimerHeader.setVisibility(VISIBLE);
        mDisclaimer.setText(disclaimer);
    }

    public void displayErrorMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
