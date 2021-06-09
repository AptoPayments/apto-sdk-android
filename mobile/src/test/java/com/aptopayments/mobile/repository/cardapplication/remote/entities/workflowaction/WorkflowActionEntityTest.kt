package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.*
import com.aptopayments.mobile.repository.user.remote.entities.CollectUserDataActionConfigurationEntity
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val ACTION_ID = "id_1234"

internal class WorkflowActionEntityTest() {

    @Test
    fun `select_balance_store is transformed correctly`() {
        val config: WorkflowActionConfigurationSelectBalanceStore = mock()
        val configEntity: WorkflowActionConfigurationSelectBalanceStoreEntity = mock() {
            on { this.toWorkflowActionConfiguration() } doReturn config
        }
        val sut = WorkflowActionEntity(ACTION_ID, "select_balance_store", configEntity, null)

        val result = sut.toWorkflowAction()

        assertTrue { result is WorkflowAction.SelectBalanceStoreAction }
        assertEquals(ACTION_ID, result.actionId)
        assertEquals(config, result.configuration)
    }

    @Test
    fun `ISSUE_CARD is transformed correctly`() {
        val config: WorkflowActionConfigurationIssueCard = mock()
        val configEntity: WorkflowActionConfigurationIssueCardEntity = mock() {
            on { this.toWorkflowActionConfiguration() } doReturn config
        }
        val sut = WorkflowActionEntity(ACTION_ID, "ISSUE_CARD", configEntity, null)

        val result = sut.toWorkflowAction()

        assertTrue { result is WorkflowAction.IssueCardAction }
        assertEquals(ACTION_ID, result.actionId)
        assertEquals(config, result.configuration)
    }

    @Test
    fun `SHOW_DISCLAIMER is transformed correctly`() {
        val config: WorkflowActionConfigurationShowDisclaimer = mock()
        val configEntity: WorkflowActionConfigurationShowDisclaimerEntity = mock() {
            on { this.toWorkflowActionConfiguration() } doReturn config
        }
        val sut = WorkflowActionEntity(ACTION_ID, "SHOW_DISCLAIMER", configEntity, null)

        val result = sut.toWorkflowAction()

        assertTrue { result is WorkflowAction.ShowDisclaimerAction }
        assertEquals(ACTION_ID, result.actionId)
        assertEquals(config, result.configuration)
    }

    @Test
    fun `COLLECT_USER_DATA is transformed correctly`() {
        val config: WorkflowActionConfigurationCollectUserData = mock()
        val configEntity: CollectUserDataActionConfigurationEntity = mock() {
            on { this.toWorkflowActionConfiguration() } doReturn config
        }
        val sut = WorkflowActionEntity(ACTION_ID, "COLLECT_USER_DATA", configEntity, null)

        val result = sut.toWorkflowAction()

        assertTrue { result is WorkflowAction.CollectUserDataAction }
        assertEquals(ACTION_ID, result.actionId)
        assertEquals(config, result.configuration)
    }

    @Test
    fun `UNKNWOWN is transformed correctly`() {

        val sut = WorkflowActionEntity(ACTION_ID, "NOBODY_KNOWS", null, null)

        val result = sut.toWorkflowAction()

        assertTrue { result is WorkflowAction.UnsupportedActionType }
        assertEquals(ACTION_ID, result.actionId)
        assertEquals(null, result.configuration)
    }
}
