package com.aptopayments.mobile.data.card

enum class KycStatus {
    UNKNOWN,
    RESUBMIT_DETAILS,
    UPLOAD_FILE,
    UNDER_REVIEW,
    PASSED,
    REJECTED,
    TEMPORARY_ERROR
}
