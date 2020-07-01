package com.aptopayments.mobile.repository.statements.usecases

import com.aptopayments.mobile.data.statements.MonthlyStatementPeriod
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepository

internal class GetMonthlyStatementPeriodUseCase constructor(
    private val repository: MonthlyStatementRepository,
    networkHandler: NetworkHandler
) : UseCase<MonthlyStatementPeriod, Unit>(networkHandler) {

    override fun run(params: Unit) = repository.getMonthlyStatementPeriod()
}
