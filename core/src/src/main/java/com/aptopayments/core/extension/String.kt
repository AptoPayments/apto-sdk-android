package com.aptopayments.core.extension

import android.annotation.SuppressLint
import com.aptopayments.core.repository.LiteralsRepository
import java.net.URL
import java.util.Locale

fun String.localized(): String = LiteralsRepository.localized(this)

@SuppressLint("DefaultLocale")
fun String.toCapitalized(): String =
        this.toLowerCase(Locale.getDefault()).split(' ').joinToString(" ") { it.capitalize() }

fun String.toUrl(): URL? = if (this.isNotEmpty()) URL(this) else null
