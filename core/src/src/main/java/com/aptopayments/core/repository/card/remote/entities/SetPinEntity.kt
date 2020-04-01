package com.aptopayments.core.repository.card.remote.entities

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.FeatureStatus
import com.aptopayments.core.data.card.SetPin
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Modifier
import java.util.Locale

internal data class SetPinEntity(

    @SerializedName("status")
    val status: String = ""

) {
    fun toSetPin() = SetPin(
        status = parseFeatureStatus(status)
    )

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun parseFeatureStatus(state: String): FeatureStatus {
        return try {
            FeatureStatus.valueOf(state.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            FeatureStatus.DISABLED
        }
    }

    companion object {
        fun from(setPin: SetPin?): SetPinEntity? {
            return setPin?.let { SetPinEntity(status = it.status.toString().toLowerCase(Locale.US)) }
        }
    }
}
