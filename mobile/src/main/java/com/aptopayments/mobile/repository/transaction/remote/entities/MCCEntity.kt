package com.aptopayments.mobile.repository.transaction.remote.entities

import com.aptopayments.mobile.data.transaction.MCC
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Locale

internal data class MCCEntity(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("icon")
    val icon: String? = null

) : Serializable {

    fun toMCC() = MCC(
        name = name,
        icon = parseMCCIcon(icon)
    )

    private fun parseMCCIcon(icon: String?): MCC.Icon? {
        return icon?.let {
            try {
                MCC.Icon.valueOf(it.toUpperCase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                MCC.Icon.OTHER
            }
        }
    }

    companion object {
        fun from(mcc: MCC?): MCCEntity? {
            return mcc?.let {
                MCCEntity(name = it.name, icon = it.icon.toString())
            }
        }
    }
}
