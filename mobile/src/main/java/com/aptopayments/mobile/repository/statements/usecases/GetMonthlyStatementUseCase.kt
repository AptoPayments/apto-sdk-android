package com.aptopayments.mobile.repository.statements.usecases

import com.aptopayments.mobile.data.statements.MonthlyStatement
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepository

internal class GetMonthlyStatementUseCase(
    private val repository: MonthlyStatementRepository,
    networkHandler: NetworkHandler
) : UseCase<MonthlyStatement, GetMonthlyStatementUseCase.Params>(networkHandler) {
    override fun run(params: Params) = repository.getMonthlyStatement(
        params.month, params.year
    )

    data class Params(
        val month: Int,
        val year: Int
    )
}
