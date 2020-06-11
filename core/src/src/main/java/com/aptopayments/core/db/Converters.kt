package com.aptopayments.core.db

import androidx.room.TypeConverter
import com.aptopayments.core.data.card.Money
import com.aptopayments.core.network.GsonProvider
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity

internal class Converters {

    @TypeConverter
    fun stringToMoney(value: String?): Money? =
        GsonProvider.provide().fromJson(value, MoneyEntity::class.java)?.toMoney()

    @TypeConverter
    fun moneyToString(money: Money?): String? =
        GsonProvider.provide().toJson(MoneyEntity.from(money))
}
