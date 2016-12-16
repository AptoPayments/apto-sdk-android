package me.ledge.link.sdk.ui.vos;

/**
 * Loan related data.<br />
 *
 * @author Adrian
 */
public class LoanDataVo {

    public int loanAmount;
    public IdDescriptionPairDisplayVo loanPurpose;

    /**
     * Creates a new {@link LoanDataVo} instance.
     */
    public LoanDataVo() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        loanAmount = -1;
        loanPurpose = null;
    }
}
