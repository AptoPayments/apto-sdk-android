package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.*
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
    val fundingFeatureEntity: FundingFeatureEntity? = null,

    @SerializedName("passcode")
    val passcodeEntity: CardPasscodeFeatureEntity? = null,

    @SerializedName("ach")
    val achAccountFeatureEntity: AchAccountFeatureEntity? = null,

    @SerializedName("in_app_provisioning")
    val inAppProvisioningEntity: GenericFeatureEntity? = null,

    @SerializedName("supports_p2p_transfers")
    val transferMoneyP2pEntity: GenericFeatureEntity? = null,

) {
    fun toFeatures() = Features(
        getPin = getPinEntity?.toGetPin(),
        setPin = setPinEntity?.toSetPin(),
        selectBalanceStore = selectBalanceStoreEntity?.toSelectBalanceStore(),
        activation = activationEntity?.toActivation(),
        ivrSupport = ivrSupportEntity?.toIvr(),
        funding = fundingFeatureEntity?.toFundingFeature(),
        passcode = passcodeEntity?.toCardPasscode(),
        achAccount = achAccountFeatureEntity?.toAchFeature(),
        inAppProvisioning = inAppProvisioningEntity?.toFeature(),
        transferMoneyP2p = transferMoneyP2pEntity?.toFeature()
    )

    companion object {
        fun from(features: Features?) = features?.let {
            FeaturesEntity(
                getPinEntity = GetPinEntity.from(features.getPin),
                setPinEntity = SetPinEntity.from(features.setPin),
                selectBalanceStoreEntity = SelectBalanceStoreEntity.from(features.selectBalanceStore),
                activationEntity = ActivationEntity.from(features.activation),
                ivrSupportEntity = IvrEntity.from(features.ivrSupport),
                fundingFeatureEntity = FundingFeatureEntity.from(features.funding),
                passcodeEntity = CardPasscodeFeatureEntity.from(features.passcode),
                achAccountFeatureEntity = AchAccountFeatureEntity.from(features.achAccount),
                inAppProvisioningEntity = GenericFeatureEntity.from(features.inAppProvisioning),
                transferMoneyP2pEntity = GenericFeatureEntity.from(features.transferMoneyP2p)
            )
        }
    }
}
