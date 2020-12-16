package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import com.google.gson.annotations.SerializedName
import java.net.URL

internal class PlainTextContentEntity(

    @SerializedName("format")
    val format: String,

    @SerializedName("value")
    val value: String

) : ContentEntity {
    override fun toContent(): Content {
        return when (format) {
            FORMAT_PLAIN_TEXT -> Content.PlainText(value)
            FORMAT_MARKDOWN -> Content.Markdown(value)
            FORMAT_EXTERNAL_URL -> Content.Web(URL(value))
            else -> Content.PlainText("")
        }
    }
}
