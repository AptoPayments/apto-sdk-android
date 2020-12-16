package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.PhoneNumber
import java.io.Serializable
import java.util.Locale

enum class FeatureStatus {
    ENABLED, DISABLED;

    companion object {
        fun fromString(state: String): FeatureStatus {
            return try {
                valueOf(state.toUpperCase(Locale.US))
            } catch (exception: Throwable) {
                DISABLED
            }
        }

        fun fromBoolean(value: Boolean): FeatureStatus {
            return if (value) ENABLED else DISABLED
        }
    }

    fun toBoolean(): Boolean {
        return this == ENABLED
    }
}

sealed class FeatureType : Serializable {
    abstract val name: String

    data class Ivr(val ivrPhone: PhoneNumber?, override val name: String = "ivr") : FeatureType()
    data class Api(override val name: String = "api") : FeatureType()
    data class Voip(override val name: String = "voip") : FeatureType()
    data class Unknown(override val name: String = "unknown") : FeatureType()
}

data class Features(
    val getPin: GetPin?,
    val setPin: SetPin?,
    val selectBalanceStore: SelectBalanceStore?,
    val activation: Activation?,
    val ivrSupport: Ivr?,
    val funding: FundingFeature?,
    val passcode: CardPasscodeFeature?
) : Serializable
