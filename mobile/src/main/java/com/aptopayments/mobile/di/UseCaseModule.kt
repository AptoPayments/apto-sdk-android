package com.aptopayments.mobile.di

import com.aptopayments.mobile.repository.card.usecases.*
import com.aptopayments.mobile.repository.card.usecases.IssueCardUseCase
import com.aptopayments.mobile.repository.cardapplication.usecases.*
import com.aptopayments.mobile.repository.config.usecases.GetCardProductUseCase
import com.aptopayments.mobile.repository.config.usecases.GetCardProductsUseCase
import com.aptopayments.mobile.repository.config.usecases.GetContextConfigurationUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.GetFundingSourcesUseCase
import com.aptopayments.mobile.repository.oauth.usecases.GetOAuthAttemptStatusUseCase
import com.aptopayments.mobile.repository.oauth.usecases.RetrieveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.SaveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.StartOAuthAuthenticationUseCase
import com.aptopayments.mobile.repository.statements.usecases.GetMonthlyStatementPeriodUseCase
import com.aptopayments.mobile.repository.statements.usecases.GetMonthlyStatementUseCase
import com.aptopayments.mobile.repository.stats.usecases.ClearMonthlySpendingCacheUseCase
import com.aptopayments.mobile.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.mobile.repository.transaction.usecases.GetTransactionsUseCase
import com.aptopayments.mobile.repository.user.usecases.*
import com.aptopayments.mobile.repository.verification.usecase.FinishVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.RestartVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.StartEmailVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.StartPhoneVerificationUseCase
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallUseCase
import org.koin.dsl.module

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
    factory { com.aptopayments.mobile.repository.cardapplication.usecases.IssueCardUseCase(
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
    factory { GetMonthlyStatementUseCase(get(), get()) }
    factory { GetMonthlyStatementPeriodUseCase(get(), get()) }
    factory { GetProvisioningDataUseCase(get(), get()) }
}
