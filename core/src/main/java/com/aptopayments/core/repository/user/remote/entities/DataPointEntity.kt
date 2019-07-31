package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.DataPoint
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity

internal interface DataPointEntity {
    val dataType: String
    val verification: VerificationEntity?
    val verified: Boolean?
    val notSpecified: Boolean?
    fun toDataPoint(): DataPoint
}
