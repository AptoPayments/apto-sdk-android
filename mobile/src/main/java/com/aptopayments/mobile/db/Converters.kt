package com.aptopayments.mobile.db

import androidx.room.TypeConverter
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity

internal class Converters {

    @TypeConverter
    fun stringToMoney(value: String?): Money? =
        GsonProvider.provide().fromJson(value, MoneyEntity::class.java)?.toMoney()

    @TypeConverter
    fun moneyToString(money: Money?): String? =
        GsonProvider.provide().toJson(MoneyEntity.from(money))
}
