package com.aptopayments.core.network

import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.ContentEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationEntity
import com.aptopayments.core.repository.cardapplication.remote.parser.ContentParser
import com.aptopayments.core.repository.cardapplication.remote.parser.WorkflowActionConfigurationParser
import com.aptopayments.core.repository.cardapplication.remote.parser.ZonedDateTimeConverter
import com.aptopayments.core.repository.user.remote.entities.DataPointConfigurationEntity
import com.aptopayments.core.repository.user.remote.entities.DataPointEntity
import com.aptopayments.core.repository.user.remote.parser.DataPointParser
import com.aptopayments.core.repository.user.remote.parser.RequiredDatapointEntityParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.threeten.bp.ZonedDateTime

internal object GsonProvider {

    private var gson: Gson? = null

    fun provide(): Gson {
        return gson ?: createGsonInstance()
    }

    private fun createGsonInstance(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(DataPointEntity::class.java, DataPointParser())
        gsonBuilder.registerTypeAdapter(DataPointConfigurationEntity::class.java, RequiredDatapointEntityParser())
        gsonBuilder.registerTypeAdapter(
            WorkflowActionConfigurationEntity::class.java,
            WorkflowActionConfigurationParser()
        )
        gsonBuilder.registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeConverter())
        gsonBuilder.registerTypeAdapter(ContentEntity::class.java, ContentParser())
        this.gson = gsonBuilder.serializeNulls().create()
        return gson!!
    }
}
