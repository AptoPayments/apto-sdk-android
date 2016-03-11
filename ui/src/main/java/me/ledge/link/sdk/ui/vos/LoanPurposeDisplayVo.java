package me.ledge.link.sdk.ui.vos;

import me.ledge.link.api.vos.responses.config.LoanPurposeVo;

/**
 * Custom {@link LoanPurposeVo} that can be displayed by a Spinner.
 * @author wijnand
 */
public class LoanPurposeDisplayVo extends LoanPurposeVo {

    private LoanPurposeVo mSource;

    /**
     * Creates a new {@link LoanPurposeDisplayVo} instance.
     */
    public LoanPurposeDisplayVo() {
        mSource = null;
        init();
    }

    /**
     * Creates a new {@link LoanPurposeDisplayVo} instance.
     * @param source Source object to copy the data from.
     */
    public LoanPurposeDisplayVo(LoanPurposeVo source) {
        mSource = source;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        if (mSource != null) {
            description = mSource.description;
            loan_purpose_id = mSource.loan_purpose_id;
            mSource = null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return description;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof LoanPurposeDisplayVo
                && ((LoanPurposeDisplayVo) other).loan_purpose_id == loan_purpose_id;
    }
}
