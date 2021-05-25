package com.aptopayments.mobile.repository.statements.usecases

import com.aptopayments.mobile.data.statements.MonthlyStatement
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.flatMap
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepository

internal class GetMonthlyStatementUseCase(
    private val repository: MonthlyStatementRepository,
    networkHandler: NetworkHandler
) : UseCase<MonthlyStatement, GetMonthlyStatementUseCase.Params>(networkHandler) {
    override fun run(params: Params): Either<Failure, MonthlyStatement> {
        return repository.getMonthlyStatement(
            params.month, params.year
        ).flatMap {
            if (it.canDownload()) {
                it.right()
            } else {
                StatementExpiredFailure().left()
            }
        }
    }

    data class Params(
        val month: Int,
        val year: Int
    )
}

internal class StatementExpiredFailure : Failure.FeatureFailure("monthly_statements_report_error_url_expired_message")
