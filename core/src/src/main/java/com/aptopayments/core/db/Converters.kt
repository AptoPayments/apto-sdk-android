package com.aptopayments.core.db

import androidx.room.TypeConverter
import com.aptopayments.core.data.card.Money
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity

class Converters {

    @TypeConverter
    fun stringToMoney(value: String?): Money? =
            ApiCatalog.gson().fromJson(value, MoneyEntity::class.java)?.toMoney()

    @TypeConverter
    fun moneyToString(money: Money?): String? =
            ApiCatalog.gson().toJson(MoneyEntity.from(money))
}
