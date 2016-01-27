package us.ledge.line.sdk.sdk.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;

/**
 * Displays the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountView
        extends UserDataView<NextButtonListener>
        implements ViewWithToolbar, View.OnClickListener {

    private TextInputLayout mAmountWrapper;
    private EditText mAmountField;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public LoanAmountView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public LoanAmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mAmountWrapper = (TextInputLayout) findViewById(R.id.til_loan_amount);
        mAmountField = (EditText) findViewById(R.id.et_loan_amount);
    }

    /**
     * @return Raw loan amount.
     */
    public String getAmount() {
        return mAmountField.getText().toString();
    }

    /**
     * Updates the amount field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateAmountError(boolean show, int errorMessageId) {
        updateErrorDisplay(mAmountWrapper, show, errorMessageId);
    }
}
