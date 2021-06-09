package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.user.agreements.AgreementAction
import com.aptopayments.mobile.data.user.agreements.ReviewAgreementsInput
import com.aptopayments.mobile.repository.user.remote.UserService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject

private const val AGREEMENT = "APTO_CHA"

internal class ReviewAgreementsUseCaseTest : UseCaseTest() {

    private val userService: UserService = mock()

    private val userCase: ReviewAgreementsUseCase by inject()

    override fun setUpKoin() {
        loadKoinModules(
            module {
                single(override = true) { userService }
            }
        )
    }

    @Test
    fun `whenever apto_cha and accepted then service is called and has the correct parameters`() {
        userCase.run(ReviewAgreementsInput(listOf(AGREEMENT), AgreementAction.ACCEPTED))

        verify(userService).reviewAgreements(listOf(AGREEMENT), AgreementAction.ACCEPTED)
    }

    @Test
    fun `whenever apto_cha and declined then service is called and has the correct parameters`() {
        userCase.run(ReviewAgreementsInput(listOf(AGREEMENT), AgreementAction.DECLINED))

        verify(userService).reviewAgreements(listOf(AGREEMENT), AgreementAction.DECLINED)
    }
}
