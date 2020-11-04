package com.aptopayments.mobile.data.content

import java.io.Serializable
import java.net.URL

sealed class Content : Serializable {
    data class PlainText(val text: String) : Content()
    data class Markdown(val markdown: String) : Content()
    data class Web(val url: URL) : Content()
    data class Native(val backgroundColor: Int?, val backgroundImage: URL?, val asset: URL?) : Content()
}
