package com.aptopayments.mobile.extension

import androidx.annotation.RestrictTo
import com.aptopayments.mobile.repository.LiteralsRepository
import java.net.URL
import java.util.Locale

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun String.localized(): String = LiteralsRepository.localized(this)

internal fun String.toUrl(): URL? = if (this.isNotEmpty()) URL(this) else null

fun String.toUpperCaseDefault(): String {
    return this.toUpperCase(Locale.US)
}
