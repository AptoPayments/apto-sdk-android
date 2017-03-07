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

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import java.util.LinkedList;
import java.util.Set;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
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


        // TODO: move this call to model ie: data.getApplicationInfo();
        DataPointList dataPointList = UserStorage.getInstance().getUserData();
        Set<DataPointVo.DataPointType> dataPointTypes = dataPointList.getDataPoints().keySet();

        LinkedList<RequiredDataPointVo> requiredData = data.getRequiredData();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (RequiredDataPointVo requiredDataPointVo : requiredData) {
            for(DataPointVo.DataPointType currentType : dataPointTypes) {
                if (requiredDataPointVo.type == currentType.ordinal() + 1) {
                    DataPointVo dataPoint = dataPointList.getUniqueDataPoint(currentType, null);
                    setTextViewValues(inflater, dataPoint);
                }
            }

        }

    }

    private void setTextViewValues(LayoutInflater inflater, DataPointVo dataPoint) {
        // TODO: move hardcoded strings to strings.xml
        switch(dataPoint.getType()) {
            case PersonalName:
                addKeyValueTextView(inflater, "First Name", ((DataPointVo.PersonalName) dataPoint).firstName);
                addKeyValueTextView(inflater, "Last Name", ((DataPointVo.PersonalName) dataPoint).lastName);
                break;
            case PhoneNumber:
                addKeyValueTextView(inflater, "Phone Number", dataPoint.toString());
                break;
            case Email:
                addKeyValueTextView(inflater, "Email", dataPoint.toString());
                break;
            case BirthDate:
                addKeyValueTextView(inflater, "Birthday", dataPoint.toString());
                break;
            case SSN:
                addKeyValueTextView(inflater, "SSN", dataPoint.toString());
                break;
            case Address:
                addKeyValueTextView(inflater, "Address", ((DataPointVo.Address) dataPoint).address);
                addKeyValueTextView(inflater, "Apt/Unit", ((DataPointVo.Address) dataPoint).apUnit);
                addKeyValueTextView(inflater, "ZIP Code", ((DataPointVo.Address) dataPoint).zip);
                addKeyValueTextView(inflater, "City", ((DataPointVo.Address) dataPoint).city);
                addKeyValueTextView(inflater, "State", ((DataPointVo.Address) dataPoint).stateCode);
                break;
            case Housing:
                // TODO: store string value selected by user
                addKeyValueTextView(inflater, "Housing Status", String.valueOf(((DataPointVo.Housing) dataPoint).housingType));
                break;
            case Employment:
                // TODO: store string value selected by user
                addKeyValueTextView(inflater, "Employment Status", String.valueOf(((DataPointVo.Employment) dataPoint).employmentStatus));
                addKeyValueTextView(inflater, "Salary Frequency", String.valueOf(((DataPointVo.Employment) dataPoint).salaryFrequency));
                break;
            case Income:
                addKeyValueTextView(inflater, "Annual Pretax Income", String.valueOf(((DataPointVo.Income) dataPoint).annualGrossIncome));
                addKeyValueTextView(inflater, "Monthly Net Income", String.valueOf(((DataPointVo.Income) dataPoint).monthlyNetIncome));
                break;
            case CreditScore:
                // TODO: store string value selected by user
                addKeyValueTextView(inflater, "Credit Score", String.valueOf(((DataPointVo.CreditScore) dataPoint).creditScoreRange));
                break;
        }
    }

    private void addKeyValueTextView(LayoutInflater inflater, String key, String value) {
        if(!key.isEmpty() && !value.isEmpty()) {
            View view = inflater.inflate(R.layout.lv_datapoint, mApplicationInfoLinearLayout, false);
            TextView dataPointKeyField = (TextView) view.findViewById(R.id.tv_datapoint_key);
            TextView dataPointValueField = (TextView) view.findViewById(R.id.tv_datapoint_value);
            dataPointKeyField.setText(key);
            dataPointValueField.setText(value);
            mApplicationInfoLinearLayout.addView(view);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showLoading(boolean show) {
        mLoadingView.showLoading(show);
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
        mDisclaimer.setText(disclaimer);
    }
}
