package com.aptopayments.mobile.data.transaction

import com.aptopayments.mobile.extension.localized
import java.io.Serializable
import java.util.Locale

data class MCC(
    val name: String?,
    val icon: Icon?
) : Serializable {

    constructor(name: String) : this(name, Icon.valueOf(name.uppercase(Locale.US)))

    fun toLocalizedString(): String {
        return (mccDescriptions[icon] ?: "transaction_details_basic_info_category_unavailable").localized()
    }

    companion object {
        val mccDescriptions: Map<Icon, String> = hashMapOf(
            Icon.PLANE to "transaction_details_basic_info_category_plane_mcc",
            Icon.CAR to "transaction_details_basic_info_category_car_mcc",
            Icon.GLASS to "transaction_details_basic_info_category_glass_mcc",
            Icon.FINANCE to "transaction_details_basic_info_category_finance_mcc",
            Icon.FOOD to "transaction_details_basic_info_category_food_mcc",
            Icon.GAS to "transaction_details_basic_info_category_gas_mcc",
            Icon.BED to "transaction_details_basic_info_category_bed_mcc",
            Icon.MEDICAL to "transaction_details_basic_info_category_medical_mcc",
            Icon.CAMERA to "transaction_details_basic_info_category_camera_mcc",
            Icon.CARD to "transaction_details_basic_info_category_card_mcc",
            Icon.CART to "transaction_details_basic_info_category_cart_mcc",
            Icon.ROAD to "transaction_details_basic_info_category_road_mcc",
            Icon.OTHER to "transaction_details_basic_info_category_other_mcc"
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
