package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import com.shift.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to add a bank account.
 * @author Adrian
 */
public class AddBankAccountTask extends ShiftApiTask<Void, Void, VerificationStatusResponseVo, AddBankAccountRequestVo> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public AddBankAccountTask(AddBankAccountRequestVo requestData, ShiftApiWrapper apiWrapper,
                              ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected VerificationStatusResponseVo callApi() throws ApiException {
        return getApiWrapper().addBankAccount(getRequestData());
    }
}
