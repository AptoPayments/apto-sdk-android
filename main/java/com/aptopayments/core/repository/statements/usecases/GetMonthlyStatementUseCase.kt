package com.aptopayments.core.repository.statements.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.statements.MonthlyStatement
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.statements.MonthlyStatementRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetMonthlyStatementUseCase constructor(
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
