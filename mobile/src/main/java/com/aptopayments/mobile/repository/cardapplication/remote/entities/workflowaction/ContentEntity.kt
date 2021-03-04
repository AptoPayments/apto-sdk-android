package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content

internal const val FORMAT_PLAIN_TEXT = "plain_text"
internal const val FORMAT_MARKDOWN = "markdown"
internal const val FORMAT_EXTERNAL_URL = "external_url"
internal const val FORMAT_NATIVE_CONTENT = "native_content"

internal interface ContentEntity {
    fun toContent(): Content

    companion object {
        fun from(content: Content): ContentEntity {
            return when (content) {
                is Content.PlainText, is Content.Markdown, is Content.Web -> PlainTextContentEntity.from(content)
                is Content.Native -> NativeContentEntity.from(content)
                else -> throw RuntimeException()
            }
        }
    }
}
