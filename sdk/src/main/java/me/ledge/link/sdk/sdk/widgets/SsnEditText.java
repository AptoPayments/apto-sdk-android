package me.ledge.link.sdk.sdk.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom {@link AppCompatEditText} to input a social security number (SSN).
 * @author Wijnand
 */
public class SsnEditText extends AppCompatEditText {

    /**
     * Shows all characters hidden, except the hyphens and the last char.<br />
     * Example: "123-45-67" --> "***-**-*7".
     */
    private class SsnPasswordTransformation extends PasswordTransformationMethod {

        /**
         * Password character sequence.
         */
        private class SsnPasswordCharSequence implements CharSequence {

            private static final char DOT = '\u2022';

            private CharSequence mSource;

            /**
             * Creates a new {@link SsnPasswordCharSequence} instance.
             * @param source Source sequence.
             */
            public SsnPasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            /** {@inheritDoc} */
            @Override
            public char charAt(int index) {
                char c = DOT;

                if (index == length() - 1
                        || index == FIRST_HYPHEN_POSITION
                        || index == SECOND_HYPHEN_POSITION) {

                    c = mSource.charAt(index);
                }

                return c;
            }

            /** {@inheritDoc} */
            @Override
            public int length() {
                return mSource.length();
            }

            /** {@inheritDoc} */
            @Override
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }

        private static final int FIRST_HYPHEN_POSITION = 3;
        private static final int SECOND_HYPHEN_POSITION = 6;

        private boolean mDeleting;
        private boolean mIgnoreNextChange;

        /** {@inheritDoc} */
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new SsnPasswordCharSequence(source);
        }

        /** {@inheritDoc} */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mDeleting = after <= 0;
        }

        /** {@inheritDoc} */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { /* Do nothing. */ }

        /** {@inheritDoc} */
        @Override
        public void afterTextChanged(Editable s) {
            if (mIgnoreNextChange) {
                mIgnoreNextChange = false;
                return;
            }

            String source = s.toString();

            if (mDeleting) {
                if (source.length() == FIRST_HYPHEN_POSITION + 1 || source.length() == SECOND_HYPHEN_POSITION + 1) {
                    mIgnoreNextChange = true;
                    int end = source.length() - 1;

                    SsnEditText.this.setText(source.substring(0, end));
                    SsnEditText.this.setSelection(end);
                }
            } else {
                if (source.length() == FIRST_HYPHEN_POSITION + 1 || source.length() == SECOND_HYPHEN_POSITION + 1) {
                    mIgnoreNextChange = true;

                    String lastDigit = source.substring(source.length() - 1);
                    String replacement = HYPHEN + lastDigit;
                    String newText = source.substring(0, source.length() - 1) + replacement;

                    SsnEditText.this.setText(newText);
                    SsnEditText.this.setSelection(newText.length());
                }
            }
        }
    }

    private static final String HYPHEN = "-";

    /* 9 SSN digits plus 2 hyphens. */
    private static final int MAX_LENGTH = 11;

    /**
     * @see AppCompatEditText#AppCompatEditText
     * @param context See {@link AppCompatEditText}.
     */
    public SsnEditText(Context context) {
        this(context, null);
    }

    /**
     * @see AppCompatEditText#AppCompatEditText
     * @param context See {@link AppCompatEditText}.
     * @param attrs See {@link AppCompatEditText}.
     */
    public SsnEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    /**
     * @see AppCompatEditText#AppCompatEditText
     * @param context See {@link AppCompatEditText}.
     * @param attrs See {@link AppCompatEditText}.
     * @param defStyleAttr See {@link AppCompatEditText}.
     */
    public SsnEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        // Numbers only.
        setRawInputType(InputType.TYPE_CLASS_NUMBER);
        // Positive numbers only.
        setKeyListener(DigitsKeyListener.getInstance());
        // Single line.
        setSingleLine(true);
        // Password and hyphenation.
        setTransformationMethod(new SsnPasswordTransformation());
        // Max length.
        setFilters(new InputFilter[]{ new InputFilter.LengthFilter(MAX_LENGTH) });
    }

    /**
     * @return Social security number without hyphens.
     */
    public String getSsn() {
        Editable editable = super.getText();
        String text = editable.toString();
        text = text.replace(HYPHEN, "");

        return text;
    }
}
