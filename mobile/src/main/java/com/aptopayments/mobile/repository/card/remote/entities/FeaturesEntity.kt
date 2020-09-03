package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.network.ApiKeyProvider
import com.aptopayments.mobile.platform.AptoSdkEnvironment
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
    val ivrSupportEntity: IvrEntity? = null,

    @SerializedName("add_funds")
    val fundingFeatureEntity: FundingFeatureEntity? = null

) {
    fun toFeatures() = Features(
        getPin = getPinEntity?.toGetPin(),
        setPin = setPinEntity?.toSetPin(),
        selectBalanceStore = selectBalanceStoreEntity?.toSelectBalanceStore(),
        activation = activationEntity?.toActivation(),
        ivrSupport = ivrSupportEntity?.toIvr(),
        funding = getFundingSource() // fundingFeatureEntity?.toFundingFeature() TODO - REPLACE
    )

    private fun getFundingSource(): FundingFeature? {
        // TODO REMOVE when API is defined
        val money = Money("USD", 2000.0)
        val limit = FundingSingleLimit(money, money)

        return FundingFeature(
            true,
            getCardNetworkList(),
            FundingLimits(limit, limit),
            "Soft descriptor"
        )
    }

    private fun getCardNetworkList(): List<Card.CardNetwork> {
        val list = mutableListOf(Card.CardNetwork.VISA, Card.CardNetwork.MASTERCARD)
        if (ApiKeyProvider.environment != AptoSdkEnvironment.PRD) {
            list.add(Card.CardNetwork.TEST)
        }
        return list
    }

    companion object {
        fun from(features: Features?) = features?.let {
            FeaturesEntity(
                getPinEntity = GetPinEntity.from(features.getPin),
                setPinEntity = SetPinEntity.from(features.setPin),
                selectBalanceStoreEntity = SelectBalanceStoreEntity.from(features.selectBalanceStore),
                activationEntity = ActivationEntity.from(features.activation),
                ivrSupportEntity = IvrEntity.from(features.ivrSupport),
                fundingFeatureEntity = FundingFeatureEntity.from(features.funding)
            )
        }
    }
}
