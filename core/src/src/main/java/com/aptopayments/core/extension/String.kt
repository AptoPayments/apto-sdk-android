package com.aptopayments.core.extension

import androidx.annotation.RestrictTo
import com.aptopayments.core.repository.LiteralsRepository
import java.net.URL

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun String.localized(): String = LiteralsRepository.localized(this)

internal fun String.toUrl(): URL? = if (this.isNotEmpty()) URL(this) else null
