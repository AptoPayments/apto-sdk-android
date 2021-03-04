package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.content.Content
import java.io.Serializable

class Disclaimer(
    val keys: List<String> = emptyList(),
    val content: Content
) : Serializable
