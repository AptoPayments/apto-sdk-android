package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.aptopayments.mobile.data.TestDataProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IssueCardDesignRequestTest {

    @Test
    fun `given IssueCardDesign when from then all data is copied correctly`() {
        val origin = TestDataProvider.provideIssueCardDesign()

        val result = IssueCardDesignRequest.from(origin)

        assertEquals(origin.designKey, result.designKey)
        assertEquals(origin.qrCode, result.qrCode)
        assertEquals(origin.extraEmbossingLine, result.extraEmbossingLine)
        assertEquals(origin.imageUrl, result.imageUrl)
        assertEquals(origin.additionalImageUrl, result.additionalImageUrl)
    }
}
