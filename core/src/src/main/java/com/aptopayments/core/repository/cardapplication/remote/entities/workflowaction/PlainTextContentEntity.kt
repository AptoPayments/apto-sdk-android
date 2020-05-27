package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.content.Content
import com.google.gson.annotations.SerializedName
import java.net.URL

class PlainTextContentEntity(

    @SerializedName("format")
    var format: String,

    @SerializedName("value")
    var value: String

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
