package com.aptopayments.core.extension

import android.content.Context
import com.aptopayments.sdk.core.repository.LiteralsRepository

fun String.Companion.empty() = ""

fun String.localized(context: Context): String = LiteralsRepository.localized(context,this)

fun String.toCapitalized(): String =
        this.toLowerCase().split(' ').joinToString(" ") { it.capitalize() }
