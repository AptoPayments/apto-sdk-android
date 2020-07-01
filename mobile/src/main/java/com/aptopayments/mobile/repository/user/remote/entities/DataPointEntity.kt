package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.user.DataPoint
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationEntity

internal interface DataPointEntity {
    val dataType: String
    val verification: VerificationEntity?
    val verified: Boolean?
    val notSpecified: Boolean?
    fun toDataPoint(): DataPoint
}
