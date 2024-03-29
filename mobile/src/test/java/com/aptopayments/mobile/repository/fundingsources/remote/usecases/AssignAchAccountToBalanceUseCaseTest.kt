package com.aptopayments.mobile.repository.fundingsources.remote.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject

private const val BALANCE_ID = "id_1234"

internal class AssignAchAccountToBalanceUseCaseTest : UseCaseTest() {

    private val details = TestDataProvider.providerAchAccountDetails()
    private val service: FundingSourcesService = mock() {
        on { assignAchAccountToBalance(BALANCE_ID) } doReturn details.right()
    }

    private val sut: AssignAchAccountToBalanceUseCase by inject()

    override fun setUpKoin() {
        loadKoinModules(
            module {
                single(override = true) { service }
                single(override = true) { mock<BalanceLocalDao>() }
            }
        )
    }

    @Test
    fun `whenever run then service gets called`() {
        sut.run(BALANCE_ID)

        verify(service).assignAchAccountToBalance(BALANCE_ID)
    }

    @Test
    fun `whenever run then result is the one provided by the service`() {
        val result = sut.run(BALANCE_ID)

        result.shouldBeRightAndEqualTo(details)
    }
}
