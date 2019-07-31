package com.aptopayments.core.data.transaction

import android.content.Context
import com.aptopayments.core.R
import com.aptopayments.core.extension.localized
import java.io.Serializable

data class MCC (
        val name: String?,
        val icon: Icon?
) : Serializable {

    fun toString(context: Context): String {
        return (mccDescriptions[icon] ?: "transaction_details.basic_info.category.unavailable").localized(context)
    }

    companion object {
        val mccDescriptions: Map<Icon, String> = hashMapOf(
                Icon.PLANE to "transaction_details.basic_info.category.plane_mcc",
                Icon.CAR to "transaction_details.basic_info.category.car_mcc",
                Icon.GLASS to "transaction_details.basic_info.category.glass_mcc",
                Icon.FINANCE to "transaction_details.basic_info.category.finance_mcc",
                Icon.FOOD to "transaction_details.basic_info.category.food_mcc",
                Icon.GAS to "transaction_details.basic_info.category.gas_mcc",
                Icon.BED to "transaction_details.basic_info.category.bed_mcc",
                Icon.MEDICAL to "transaction_details.basic_info.category.medical_mcc",
                Icon.CAMERA to "transaction_details.basic_info.category.camera_mcc",
                Icon.CARD to "transaction_details.basic_info.category.card_mcc",
                Icon.CART to "transaction_details.basic_info.category.cart_mcc",
                Icon.ROAD to "transaction_details.basic_info.category.road_mcc",
                Icon.OTHER to "transaction_details.basic_info.category.other_mcc"
        )
    }

    enum class Icon {
        PLANE,
        CAR,
        GLASS,
        FINANCE,
        FOOD,
        GAS,
        BED,
        MEDICAL,
        CAMERA,
        CARD,
        CART,
        ROAD,
        OTHER
    }
}
