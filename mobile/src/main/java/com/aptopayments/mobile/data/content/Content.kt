package com.aptopayments.mobile.data.content

import java.io.Serializable
import java.net.URL

sealed class Content : Serializable {
    class PlainText(val text: String) : Content()
    class Markdown(val markdown: String) : Content()
    class Web(val url: URL) : Content()
    class Native(val backgroundColor: Int?, val backgroundImage: URL?, val asset: URL?) : Content()
}
