package com.aptopayments.mobile.data.user

import java.io.Serializable

class User(
    val userId: String,
    val token: String,
    val userData: DataPointList?
) : Serializable
