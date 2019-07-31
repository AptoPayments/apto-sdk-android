package com.aptopayments.core.repository.card.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class SetPinUseCase @Inject constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<Card, SetPinParams>(networkHandler) {
    override fun run(params: SetPinParams) = repository.setPin(params)
}
