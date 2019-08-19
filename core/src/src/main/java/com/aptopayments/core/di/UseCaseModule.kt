package com.aptopayments.core.di

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import com.aptopayments.core.repository.card.usecases.*
import com.aptopayments.core.repository.card.usecases.IssueCardUseCase
import com.aptopayments.core.repository.cardapplication.usecases.*
import com.aptopayments.core.repository.config.usecases.GetCardProductUseCase
import com.aptopayments.core.repository.config.usecases.GetCardProductsUseCase
import com.aptopayments.core.repository.config.usecases.GetContextConfigurationUseCase
import com.aptopayments.core.repository.fundingsources.remote.usecases.GetFundingSourcesUseCase
import com.aptopayments.core.repository.oauth.usecases.GetOAuthAttemptStatusUseCase
import com.aptopayments.core.repository.oauth.usecases.RetrieveOAuthUserDataUseCase
import com.aptopayments.core.repository.oauth.usecases.SaveOAuthUserDataUseCase
import com.aptopayments.core.repository.oauth.usecases.StartOAuthAuthenticationUseCase
import com.aptopayments.core.repository.stats.usecases.ClearMonthlySpendingCacheUseCase
import com.aptopayments.core.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.core.repository.transaction.usecases.GetTransactionsUseCase
import com.aptopayments.core.repository.user.usecases.*
import com.aptopayments.core.repository.verification.usecase.FinishVerificationUseCase
import com.aptopayments.core.repository.verification.usecase.RestartVerificationUseCase
import com.aptopayments.core.repository.verification.usecase.StartEmailVerificationUseCase
import com.aptopayments.core.repository.verification.usecase.StartPhoneVerificationUseCase
import com.aptopayments.core.repository.voip.usecases.SetupVoipCallUseCase
import org.koin.dsl.module
import java.lang.reflect.Modifier

@SuppressLint("VisibleForTests")
@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal val useCasesModule = module {
    factory { RegisterPushDeviceUseCase(repository = get(), networkHandler = get()) }
    factory { UnregisterPushDeviceUseCase(repository = get(), networkHandler = get()) }
    factory { GetContextConfigurationUseCase(repository = get(), networkHandler = get()) }
    factory { GetCardProductUseCase(repository = get(), networkHandler = get()) }
    factory { GetCardProductsUseCase(repository = get(), networkHandler = get()) }
    factory { CreateUserUseCase(userRepository = get(), networkHandler = get()) }
    factory { LoginUserUseCase(repository = get(), networkHandler = get()) }
    factory { UpdateUserDataUseCase(repository = get(), networkHandler = get()) }
    factory { StartOAuthAuthenticationUseCase(repository = get(), networkHandler = get()) }
    factory { GetOAuthAttemptStatusUseCase(repository = get(), networkHandler = get()) }
    factory { SaveOAuthUserDataUseCase(repository = get(), networkHandler = get()) }
    factory { RetrieveOAuthUserDataUseCase(repository = get(), networkHandler = get()) }
    factory { StartPhoneVerificationUseCase(repository = get(), networkHandler = get()) }
    factory { StartEmailVerificationUseCase(repository = get(), networkHandler = get()) }
    factory { FinishVerificationUseCase(repository = get(), networkHandler = get()) }
    factory { RestartVerificationUseCase(repository = get(), networkHandler = get()) }
    factory { StartCardApplicationUseCase(applicationRepository = get(), networkHandler = get()) }
    factory { GetCardApplicationUseCase(applicationRepository = get(), networkHandler = get()) }
    factory { SetBalanceStoreUseCase(applicationRepository = get(), networkHandler = get()) }
    factory { AcceptDisclaimerUseCase(applicationRepository = get(), networkHandler = get()) }
    factory { CancelCardApplicationUseCase(applicationRepository = get(), networkHandler = get()) }
    factory { com.aptopayments.core.repository.cardapplication.usecases.IssueCardUseCase(
            applicationRepository = get(),
            networkHandler = get()
    ) }
    factory { IssueCardUseCase(
            cardRepository = get(),
            networkHandler = get()
    ) }
    factory { GetCardsUseCase(repository = get(), networkHandler = get()) }
    factory { GetCardUseCase(repository = get(), networkHandler = get()) }
    factory { GetCardDetailsUseCase(repository = get(), networkHandler = get()) }
    factory { ActivatePhysicalCardUseCase(repository = get(), networkHandler = get()) }
    factory { UnlockCardUseCase(repository = get(), networkHandler = get()) }
    factory { LockCardUseCase(repository = get(), networkHandler = get()) }
    factory { SetPinUseCase(repository = get(), networkHandler = get()) }
    factory { GetTransactionsUseCase(repository = get(), networkHandler = get()) }
    factory { GetMonthlySpendingUseCase(repository = get(), networkHandler = get()) }
    factory { ClearMonthlySpendingCacheUseCase(repository = get(), networkHandler = get()) }
    factory { GetFundingSourcesUseCase(fundingSourceRepository = get(), networkHandler = get()) }
    factory { GetCardBalanceUseCase(repository = get(), networkHandler = get()) }
    factory { SetCardFundingSourceUseCase(repository = get(), networkHandler = get()) }
    factory { AddCardBalanceUseCase(repository = get(), networkHandler = get()) }
    factory { GetNotificationPreferencesUseCase(repository = get(), networkHandler = get()) }
    factory { UpdateNotificationPreferencesUseCase(repository = get(), networkHandler = get()) }
    factory { SetupVoipCallUseCase(repository = get(), networkHandler = get()) }
}
