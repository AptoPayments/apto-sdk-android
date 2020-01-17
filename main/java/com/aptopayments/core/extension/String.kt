package com.aptopayments.core.extension

import com.aptopayments.core.repository.LiteralsRepository
import java.net.URL

fun String.localized(): String = LiteralsRepository.localized(this)

fun String.toCapitalized(): String =
        this.toLowerCase().split(' ').joinToString(" ") { it.capitalize() }

fun String.toUrl(): URL? = if (this.isNotEmpty()) URL(this) else null
