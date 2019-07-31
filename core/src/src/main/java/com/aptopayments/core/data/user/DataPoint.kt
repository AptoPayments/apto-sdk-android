package com.aptopayments.core.data.user

import java.io.Serializable

abstract class DataPoint : Serializable {
    abstract val verification: Verification?
    abstract val verified: Boolean?
    abstract val notSpecified: Boolean?
    abstract fun getType(): Type

    enum class Type { NAME, PHONE, EMAIL, BIRTHDATE, ADDRESS }
}
