package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.Features
import com.google.gson.annotations.SerializedName

internal data class FeaturesEntity(

        @SerializedName("get_pin")
        val getPinEntity: GetPinEntity? = null,

        @SerializedName("set_pin")
        val setPinEntity: SetPinEntity? = null,

        @SerializedName("select_balance_store")
        val selectBalanceStoreEntity: SelectBalanceStoreEntity? = null,

        @SerializedName("activation")
        val activationEntity: ActivationEntity? = null,

        @SerializedName("support")
        val ivrSupportEntity: IvrEntity? = null

) {
    fun toFeatures() = Features(
            getPin = getPinEntity?.toGetPin(),
            setPin = setPinEntity?.toSetPin(),
            selectBalanceStore = selectBalanceStoreEntity?.toSelectBalanceStore(),
            activation = activationEntity?.toActivation(),
            ivrSupport = ivrSupportEntity?.toIvr()
    )

    companion object {
        fun from(features: Features?) = features?.let {
            FeaturesEntity(
                    getPinEntity = GetPinEntity.from(features.getPin),
                    setPinEntity = SetPinEntity.from(features.setPin),
                    selectBalanceStoreEntity = SelectBalanceStoreEntity.from(features.selectBalanceStore),
                    activationEntity = ActivationEntity.from(features.activation),
                    ivrSupportEntity = IvrEntity.from(features.ivrSupport)
            )
        }
    }
}
