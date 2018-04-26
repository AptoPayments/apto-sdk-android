package com.shiftpayments.link.sdk.ui.views.text;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 * A {@link TextWatcher} that formats a (US) phone number.
 * @author Wijnand
 */
public class PhoneNumberTextWatcher implements TextWatcher {

    private static final InputFilter[] EMPTY_FILTERS = new InputFilter[]{};

    private boolean mInternalChange;
    private AsYouTypeFormatter mFormatter;
    private String mNonDigitsPattern;

    /**
     * Creates a new {@link PhoneNumberTextWatcher} instance.
     */
    public PhoneNumberTextWatcher() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        mFormatter = phoneUtil.getAsYouTypeFormatter("US");
        mNonDigitsPattern = VerbalExpression.regex()
                .nonDigit()
                .build()
                .toString();
    }

    /** {@inheritDoc} */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void afterTextChanged(Editable source) {
        if (mInternalChange) {
            return;
        }

        if(source.length() > 0){
            mInternalChange = true;

            String unformatted = source.toString().replaceAll(mNonDigitsPattern, "");
            String formatted = "";

            for(int i = 0; i < unformatted.length(); i++){
                formatted = mFormatter.inputDigit(unformatted.charAt(i));
            }

            mFormatter.clear();

            // Removing and then adding filters to allow a numbers only field
            // to be updated with a formatted phone number.
            InputFilter[] filters = source.getFilters();
            source.setFilters(EMPTY_FILTERS);
            source.replace(0, source.length(), formatted);
            source.setFilters(filters);

            mInternalChange = false;
        }

    }
}
