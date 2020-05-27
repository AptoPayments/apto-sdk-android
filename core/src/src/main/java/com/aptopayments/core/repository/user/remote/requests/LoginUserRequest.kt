package com.aptopayments.core.repository.user.remote.requests

import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class LoginUserRequest(

    @SerializedName("verifications")
    val verifications: ListEntity<VerificationEntity>

) : Serializable {
    companion object {
        fun from(verificationList: List<Verification>): LoginUserRequest {
            val verificationEntityList: ArrayList<VerificationEntity> = ArrayList()
            for (verification in verificationList) {
                verificationEntityList.add(
                    VerificationEntity(
                        verification.verificationType,
                        verification.verificationId,
                        verification.status.name
                    )
                )
            }

            val listEntity: ListEntity<VerificationEntity> = ListEntity(
                type = "list",
                page = 0,
                totalCount = verificationEntityList.size,
                rows = verificationEntityList.size,
                data = verificationEntityList
            )
            return LoginUserRequest(listEntity)
        }
    }
}
