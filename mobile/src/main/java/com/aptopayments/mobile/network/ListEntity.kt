package com.aptopayments.mobile.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class ListEntity<T>(

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("rows")
    val rows: Int = 0,

    @SerializedName("has_more")
    val hasMore: Boolean = false,

    @SerializedName("total_count")
    val totalCount: Int = 0,

    @SerializedName("data")
    val data: List<T>? = null

) : Serializable
