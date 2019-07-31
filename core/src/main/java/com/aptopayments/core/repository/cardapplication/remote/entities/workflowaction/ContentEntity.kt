package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.content.Content

internal const val FORMAT_PLAIN_TEXT = "plain_text"
internal const val FORMAT_MARKDOWN = "markdown"
internal const val FORMAT_EXTERNAL_URL = "external_url"
internal const val FORMAT_NATIVE_CONTENT = "native_content"

interface ContentEntity {
    fun toContent(): Content
}
