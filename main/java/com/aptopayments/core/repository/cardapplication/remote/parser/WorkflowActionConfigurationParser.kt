package com.aptopayments.core.repository.cardapplication.remote.parser

import com.aptopayments.core.extension.safeStringFromJson
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationIssueCardEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationSelectBalanceStoreEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationShowDisclaimerEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

const val SELECT_BALANCE_STORE_CONFIG = "select_balance_store_configuration"
const val SHOW_DISCLAIMER_CONFIG = "action_disclaimer_config"
const val ISSUE_CARD_CONFIG = "action_issue_card_config"

class WorkflowActionConfigurationParser: JsonDeserializer<WorkflowActionConfigurationEntity?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): WorkflowActionConfigurationEntity? {
        val configJson= json?.asJsonObject ?: return null
        return when (safeStringFromJson(configJson.get("type"))) {
            SELECT_BALANCE_STORE_CONFIG -> parseSelectBalanceStoreConfig(configJson)
            SHOW_DISCLAIMER_CONFIG -> parseShowDisclaimerConfig(configJson)
            ISSUE_CARD_CONFIG -> parseIssueCardConfig(configJson)
            else -> null
        }
    }

    private fun parseSelectBalanceStoreConfig(configJson: JsonObject): WorkflowActionConfigurationSelectBalanceStoreEntity? {
        return ApiCatalog.gson().fromJson<WorkflowActionConfigurationSelectBalanceStoreEntity>(configJson, WorkflowActionConfigurationSelectBalanceStoreEntity::class.java)
    }

    private fun parseShowDisclaimerConfig(configJson: JsonObject): WorkflowActionConfigurationShowDisclaimerEntity? {
        return ApiCatalog.gson().fromJson<WorkflowActionConfigurationShowDisclaimerEntity>(configJson, WorkflowActionConfigurationShowDisclaimerEntity::class.java)
    }

    private fun parseIssueCardConfig(configJson: JsonObject): WorkflowActionConfigurationIssueCardEntity? {
        return ApiCatalog.gson().fromJson<WorkflowActionConfigurationIssueCardEntity>(configJson, WorkflowActionConfigurationIssueCardEntity::class.java)
    }
}
