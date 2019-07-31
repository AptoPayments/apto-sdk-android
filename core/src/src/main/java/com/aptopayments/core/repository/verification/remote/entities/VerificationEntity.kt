package com.aptopayments.core.repository.verification.remote.entities

import com.aptopayments.core.data.user.Verification
import com.google.gson.annotations.SerializedName

internal data class VerificationEntity(

        @SerializedName("verification_type")
        override val verificationType: String = "",

        @SerializedName("verification_id")
        override val verificationId: String = "",

        @SerializedName("status")
        override val status: String = "",

        @SerializedName("secondary_credential")
        val secondaryCredentialEntity: VerificationEntity? = null

) : BaseVerificationEntity {
    fun toVerification(): Verification {
        return Verification(
                verificationId = verificationId,
                verificationType = verificationType,
                status = parseStatus(status),
                secondaryCredential = secondaryCredentialEntity?.toVerification()
        )
    }

    companion object {
        fun from(verification: Verification?): VerificationEntity? {
            if (verification == null) return null
            return VerificationEntity(
                    verificationType = verification.verificationType,
                    verificationId = verification.verificationId,
                    status = verification.status.toString().toLowerCase()
            )
        }
    }
}
