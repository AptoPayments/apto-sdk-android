package com.aptopayments.core.repository.statements.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.statements.MonthlyStatementPeriod
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.statements.MonthlyStatementRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetMonthlyStatementPeriodUseCase constructor(
    private val repository: MonthlyStatementRepository,
    networkHandler: NetworkHandler
) : UseCase<MonthlyStatementPeriod, Unit>(networkHandler) {

    override fun run(params: Unit) = repository.getMonthlyStatementPeriod()

}
