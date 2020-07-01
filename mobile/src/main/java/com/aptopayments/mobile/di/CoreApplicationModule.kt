package com.aptopayments.mobile.di

import com.aptopayments.mobile.db.DataBaseProvider
import com.aptopayments.mobile.db.LocalDB
import com.aptopayments.mobile.network.*
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.PushTokenRepository
import com.aptopayments.mobile.repository.UserPreferencesRepository
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.UserSessionRepositoryImpl
import com.aptopayments.mobile.repository.card.CardRepository
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.local.CardLocalRepositoryImpl
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository
import com.aptopayments.mobile.repository.cardapplication.remote.CardApplicationService
import com.aptopayments.mobile.repository.config.ConfigRepository
import com.aptopayments.mobile.repository.config.remote.ConfigService
import com.aptopayments.mobile.repository.fundingsources.FundingSourceRepository
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService
import com.aptopayments.mobile.repository.oauth.OAuthRepository
import com.aptopayments.mobile.repository.oauth.remote.OAuthService
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepository
import com.aptopayments.mobile.repository.statements.remote.MonthlyStatementService
import com.aptopayments.mobile.repository.stats.StatsRepository
import com.aptopayments.mobile.repository.stats.remote.StatsService
import com.aptopayments.mobile.repository.transaction.TransactionRepository
import com.aptopayments.mobile.repository.transaction.remote.TransactionService
import com.aptopayments.mobile.repository.user.UserRepository
import com.aptopayments.mobile.repository.user.remote.UserService
import com.aptopayments.mobile.repository.verification.VerificationRepository
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationService
import com.aptopayments.mobile.repository.voip.VoipRepository
import com.aptopayments.mobile.repository.voip.remote.VoipService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val applicationModule = module {
    single { DataBaseProvider.getInstance(context = androidContext()) }
    single { get<LocalDB>().balanceLocalDao() }
    single { get<LocalDB>().cardBalanceLocalDao() }
    single { get<LocalDB>().transactionLocalDao() }
    single<CardLocalRepository> { CardLocalRepositoryImpl(androidContext()) }
    factory<ConnectivityChecker> { ConnectivityCheckerImpl(androidContext()) }
    single { NetworkHandler(get()) }
    factory<OkHttpClientProvider> { OkHttpClientProviderImpl(ApiKeyProvider, get()) }
    factory<RetrofitFactory> { RetrofitFactoryImpl(GsonProvider, get()) }
    single { ApiCatalog(get(), ApiKeyProvider) }
}

internal val repositoryModule = module {
    single<UserSessionRepository> { UserSessionRepositoryImpl(androidContext(), get()) }
    single { UserPreferencesRepository(userSessionRepository = get(), context = get()) }
    single { PushTokenRepository(
            userSessionRepository = get(),
            registerPushDeviceUseCase = get(),
            unregisterPushDeviceUseCase = get(),
            context = androidContext()
    ) }
    single { VerificationService(apiCatalog = get()) }
    single<VerificationRepository> { VerificationRepository.Network(networkHandler = get(), service = get()) }
    single { OAuthService(apiCatalog = get()) }
    single<OAuthRepository> { OAuthRepository.Network(networkHandler = get(), service = get()) }
    single { CardApplicationService(apiCatalog = get()) }
    single<CardApplicationRepository> { CardApplicationRepository.Network(
            networkHandler = get(),
            cardApplicationService = get()
    ) }
    single { ConfigService(apiCatalog = get()) }
    single<ConfigRepository> { ConfigRepository.Network(networkHandler = get(), service = get()) }
    single { UserService(apiCatalog = get()) }
    single<UserRepository> { UserRepository.Network(networkHandler = get(), service = get()) }
    single { FundingSourcesService(apiCatalog = get()) }
    single<FundingSourceRepository> { FundingSourceRepository.Network(
            networkHandler = get(),
            service = get(),
            balanceLocalDao = get()
    ) }
    single { CardService(apiCatalog = get()) }
    single<CardRepository> { CardRepository.Network(get(), get(), get(), get(), get()) }
    single { TransactionService(apiCatalog = get()) }
    single<TransactionRepository> { TransactionRepository.Network(
            networkHandler = get(),
            service = get(),
            transactionLocalDao = get()
    ) }
    single { StatsService(apiCatalog = get()) }
    single<StatsRepository> { StatsRepository.Network(networkHandler = get(), service = get()) }
    single { MonthlyStatementService(apiCatalog = get()) }
    single<MonthlyStatementRepository> {
        MonthlyStatementRepository.Network(networkHandler = get(), service = get())
    }
    single { VoipService(apiCatalog = get()) }
    single<VoipRepository> { VoipRepository.Network(networkHandler = get(), service = get()) }
}
