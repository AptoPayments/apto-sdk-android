package com.aptopayments.core.extension

import android.content.Context
import com.aptopayments.core.repository.LiteralsRepository
import java.net.URL

fun String.localized(context: Context): String = LiteralsRepository.localized(context,this)

fun String.toCapitalized(): String =
        this.toLowerCase().split(' ').joinToString(" ") { it.capitalize() }

fun String.toUrl(): URL? = if (this.isNotEmpty()) URL(this) else null
