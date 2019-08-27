package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.content.Content
import com.aptopayments.core.extension.ColorParser
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NativeValueEntityTest : UnitTest() {
    // Collaborators
    @Mock private lateinit var colorParser: ColorParser
    private val url = "https://aptopayments.com"
    private val colorString = "FFFFFF"

    @Before
    override fun setUp() {
        super.setUp()
        given(colorParser.fromHexString(colorString, colorString)).willReturn(0x000000)
    }

    @Test
    fun `all values set return a well configured content object`() {
        // Given
        val sut = NativeValueEntity(backgroundColor = colorString, backgroundImage = url, asset = url,
                colorParser = colorParser)

        // When
        val content = sut.toContent()

        // Then
        content shouldBeInstanceOf Content.Native::class.java
        val nativeContent = content as Content.Native
        assertNotNull(nativeContent.backgroundColor)
        assertNotNull(nativeContent.backgroundImage)
        assertNotNull(nativeContent.asset)
    }

    @Test
    fun `null fields set return a well configured content object`() {
        // Given
        val sut = NativeValueEntity(backgroundColor = null, backgroundImage = null, asset = null,
                colorParser = colorParser)

        // When
        val content = sut.toContent()

        // Then
        content shouldBeInstanceOf Content.Native::class.java
        val nativeContent = content as Content.Native
        assertNull(nativeContent.backgroundColor)
        assertNull(nativeContent.backgroundImage)
        assertNull(nativeContent.asset)
    }

    @Test
    fun `empty urls fields set return a well configured content object`() {
        // Given
        val sut = NativeValueEntity(backgroundColor = null, backgroundImage = "", asset = "",
                colorParser = colorParser)

        // When
        val content = sut.toContent()

        // Then
        content shouldBeInstanceOf Content.Native::class.java
        val nativeContent = content as Content.Native
        assertNull(nativeContent.backgroundColor)
        assertNull(nativeContent.backgroundImage)
        assertNull(nativeContent.asset)
    }
}
