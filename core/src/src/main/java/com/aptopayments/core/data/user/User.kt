package com.aptopayments.core.data.user

import java.io.Serializable

class User(
    val userId: String,
    val token: String,
    val userData: DataPointList?
) : Serializable
