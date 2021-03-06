package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.SelectBalanceStore
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.AllowedBalanceTypeEntity
import com.google.gson.annotations.SerializedName

internal data class SelectBalanceStoreEntity(

    @SerializedName("allowed_balance_types")
    var allowedBalanceTypes: ListEntity<AllowedBalanceTypeEntity>?

) {
    fun toSelectBalanceStore() = SelectBalanceStore(
        allowedBalanceTypes = parseAllowedBalanceTypes()
    )

    private fun parseAllowedBalanceTypes(): List<AllowedBalanceType>? {
        return allowedBalanceTypes?.data?.let {
            it.map { allowedBalanceType -> allowedBalanceType.toAllowedBalanceType() }
        }
    }

    companion object {
        fun from(selectBalanceStore: SelectBalanceStore?): SelectBalanceStoreEntity? {
            return selectBalanceStore?.let {
                val list = selectBalanceStore.allowedBalanceTypes?.map { allowedBalanceType ->
                    AllowedBalanceTypeEntity.from(allowedBalanceType)
                }
                SelectBalanceStoreEntity(
                    allowedBalanceTypes = ListEntity(data = list)
                )
            }
        }
    }
}
