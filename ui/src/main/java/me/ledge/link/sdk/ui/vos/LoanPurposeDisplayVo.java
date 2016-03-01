package me.ledge.link.sdk.ui.vos;

import me.ledge.link.api.vos.responses.config.LoanPurposeVo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Custom {@link LoanPurposeVo} that can be displayed by a Spinner.
 * @author wijnand
 */
public class LoanPurposeDisplayVo extends LoanPurposeVo implements Serializable {

    private static final long serialVersionUID = 0L;

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

    /**
     * Writes the state of this object to the {@link ObjectOutputStream}.
     * @param stream The {@link ObjectOutputStream} to write to.
     * @throws IOException
     * @see Serializable
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(description);
        stream.writeInt(loan_purpose_id);
    }

    /**
     * Restores class fields.
     * @param stream The source {@link ObjectInputStream} to read the field information from.
     * @throws IOException
     * @throws ClassNotFoundException
     * @see Serializable
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        description = (String) stream.readObject();
        loan_purpose_id = stream.readInt();
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
