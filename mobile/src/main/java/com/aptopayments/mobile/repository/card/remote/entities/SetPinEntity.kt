package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.SetPin
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class SetPinEntity(

    @SerializedName("status")
    val status: String = ""

) {
    fun toSetPin() = SetPin(
        status = FeatureStatus.fromString(status)
    )

    companion object {
        fun from(setPin: SetPin?): SetPinEntity? {
            return setPin?.let { SetPinEntity(status = it.status.toString().lowercase(Locale.US)) }
        }
    }
}
