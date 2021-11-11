package com.aptopayments.mobile.extension

import androidx.annotation.RestrictTo
import com.aptopayments.mobile.repository.LiteralsRepository
import java.net.URL
import java.util.Locale

internal const val VALUE_STRING = "<<VALUE>>"

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun String.localized(): String = LiteralsRepository.localized(this)

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun String.replaceFirstValue(value: String) = this.replaceFirst(VALUE_STRING, value)

internal fun String.toUrl(): URL? = if (this.isNotEmpty()) URL(this) else null

fun String.toUpperCaseDefault(): String {
    return this.uppercase(Locale.US)
}
