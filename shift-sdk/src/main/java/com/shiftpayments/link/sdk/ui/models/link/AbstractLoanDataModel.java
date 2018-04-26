package com.shiftpayments.link.sdk.ui.models.link;

import com.shiftpayments.link.sdk.ui.models.AbstractActivityModel;
import com.shiftpayments.link.sdk.ui.vos.LoanDataVo;

/**
 * Generic {@link LoanDataModel} implementation.
 * @author Wijnand
 */
public abstract class AbstractLoanDataModel extends AbstractActivityModel implements LoanDataModel {

    protected LoanDataVo mBase;

    /** {@inheritDoc} */
    @Override
    public LoanDataVo getBaseData() {
        return mBase;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(LoanDataVo base) {
        mBase = base;
    }
}
