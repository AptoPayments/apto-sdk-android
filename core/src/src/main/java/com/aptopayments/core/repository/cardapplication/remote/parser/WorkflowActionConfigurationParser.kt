package com.aptopayments.core.repository.cardapplication.remote.parser

import com.aptopayments.core.extension.safeStringFromJson
import com.aptopayments.core.network.GsonProvider
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationIssueCardEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationSelectBalanceStoreEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationShowDisclaimerEntity
import com.aptopayments.core.repository.user.remote.entities.CollectUserDataActionConfigurationEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

private const val SELECT_BALANCE_STORE_CONFIG = "select_balance_store_configuration"
private const val SHOW_DISCLAIMER_CONFIG = "action_disclaimer_config"
private const val ISSUE_CARD_CONFIG = "action_issue_card_config"
private const val COLLECT_USER_DATA = "action_collect_user_data_config"

internal class WorkflowActionConfigurationParser : JsonDeserializer<WorkflowActionConfigurationEntity?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WorkflowActionConfigurationEntity? {
        val configJson = json?.asJsonObject ?: return null
        return when (safeStringFromJson(configJson.get("type"))) {
            SELECT_BALANCE_STORE_CONFIG -> parseSelectBalanceStoreConfig(configJson)
            SHOW_DISCLAIMER_CONFIG -> parseShowDisclaimerConfig(configJson)
            ISSUE_CARD_CONFIG -> parseIssueCardConfig(configJson)
            COLLECT_USER_DATA -> parseCollectUserDataConfig(configJson)
            else -> null
        }
    }

    private fun parseSelectBalanceStoreConfig(configJson: JsonObject) =
        GsonProvider.provide().fromJson(configJson, WorkflowActionConfigurationSelectBalanceStoreEntity::class.java)

    private fun parseShowDisclaimerConfig(configJson: JsonObject) =
        GsonProvider.provide().fromJson(configJson, WorkflowActionConfigurationShowDisclaimerEntity::class.java)

    private fun parseIssueCardConfig(configJson: JsonObject) =
        GsonProvider.provide().fromJson(configJson, WorkflowActionConfigurationIssueCardEntity::class.java)

    private fun parseCollectUserDataConfig(configJson: JsonObject) =
        GsonProvider.provide().fromJson(configJson, CollectUserDataActionConfigurationEntity::class.java)
}
