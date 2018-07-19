package com.shiftpayments.link.sdk.sdk;

import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;

public interface RetryCallback {

    ShiftApiTask getNewApiTask();
}
