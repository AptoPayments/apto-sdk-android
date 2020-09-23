package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.utils.FileReader
import org.junit.Test
import kotlin.test.assertNotNull

class FeaturesEntityTest {

    @Test
    fun `Entity parses without errors`() {
        val json = FileReader().readFile("featuresentity.json", "jsons")

        val result = GsonProvider.provide().fromJson(json, FeaturesEntity::class.java)

        assertNotNull(result)
    }
}
