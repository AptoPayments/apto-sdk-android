package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.net.URL

internal data class PlainTextContentEntity(
    @SerializedName("format")
    val format: String,

    @SerializedName("value")
    val value: String

) : ContentEntity, Serializable {
    override fun toContent(): Content {
        return when (format) {
            FORMAT_PLAIN_TEXT -> Content.PlainText(value)
            FORMAT_MARKDOWN -> Content.Markdown(value)
            FORMAT_EXTERNAL_URL -> Content.Web(URL(value))
            else -> Content.PlainText("")
        }
    }

    companion object {
        fun from(content: Content): ContentEntity {
            return when (content) {
                is Content.PlainText -> PlainTextContentEntity(FORMAT_PLAIN_TEXT, content.text)
                is Content.Markdown -> PlainTextContentEntity(FORMAT_MARKDOWN, content.markdown)
                is Content.Web -> PlainTextContentEntity(FORMAT_EXTERNAL_URL, content.url.toExternalForm())
                else -> throw RuntimeException()
            }
        }
    }
}
