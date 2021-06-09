package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NativeValueEntityTest {
    // Collaborators
    private val url = "https://aptopayments.com"
    private val colorString = "FFFFFF"

    @Test
    fun `all values set return a well configured content object`() {
        // Given
        val sut = NativeValueEntity(backgroundColor = colorString, backgroundImage = url, asset = url)

        // When
        val content: Content.Native = sut.toContent()

        // Then
        assertNotNull(content.backgroundColor)
        assertNotNull(content.backgroundImage)
        assertNotNull(content.asset)
    }

    @Test
    fun `null fields set return a well configured content object`() {
        // Given
        val sut = NativeValueEntity(backgroundColor = null, backgroundImage = null, asset = null)

        // When
        val content: Content.Native = sut.toContent()

        // Then
        assertNull(content.backgroundColor)
        assertNull(content.backgroundImage)
        assertNull(content.asset)
    }

    @Test
    fun `empty urls fields set return a well configured content object`() {
        // Given
        val sut = NativeValueEntity(backgroundColor = null, backgroundImage = "", asset = "")

        // When
        val content: Content.Native = sut.toContent()

        // Then
        assertNull(content.backgroundColor)
        assertNull(content.backgroundImage)
        assertNull(content.asset)
    }
}
