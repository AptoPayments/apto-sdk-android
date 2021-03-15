package com.aptopayments.mobile.di

import com.aptopayments.mobile.db.DataBaseProvider
import com.aptopayments.mobile.db.LocalDB
import com.aptopayments.mobile.network.*
import com.aptopayments.mobile.platform.ErrorHandler
import com.aptopayments.mobile.platform.RequestExecutor
import com.aptopayments.mobile.repository.PushTokenRepository
import com.aptopayments.mobile.repository.UserPreferencesRepository
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.UserSessionRepositoryImpl
import com.aptopayments.mobile.repository.card.CardRepository
import com.aptopayments.mobile.repository.card.CardRepositoryImpl
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.local.CardLocalRepositoryImpl
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepositoryImpl
import com.aptopayments.mobile.repository.cardapplication.remote.CardApplicationService
import com.aptopayments.mobile.repository.config.ConfigRepository
import com.aptopayments.mobile.repository.config.ConfigRepositoryImpl
import com.aptopayments.mobile.repository.config.remote.ConfigService
import com.aptopayments.mobile.repository.fundingsources.FundingSourceRepository
import com.aptopayments.mobile.repository.fundingsources.FundingSourceRepositoryImpl
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService
import com.aptopayments.mobile.repository.oauth.OAuthRepository
import com.aptopayments.mobile.repository.oauth.OAuthRepositoryImpl
import com.aptopayments.mobile.repository.oauth.remote.OAuthService
import com.aptopayments.mobile.repository.payment.PaymentRepository
import com.aptopayments.mobile.repository.payment.PaymentRepositoryImpl
import com.aptopayments.mobile.repository.payment.PaymentService
import com.aptopayments.mobile.repository.paymentsources.PaymentSourcesRepository
import com.aptopayments.mobile.repository.paymentsources.PaymentSourcesRepositoryImpl
import com.aptopayments.mobile.repository.paymentsources.PaymentSourcesService
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepository
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepositoryImpl
import com.aptopayments.mobile.repository.statements.remote.MonthlyStatementService
import com.aptopayments.mobile.repository.stats.StatsRepository
import com.aptopayments.mobile.repository.stats.StatsRepositoryImpl
import com.aptopayments.mobile.repository.stats.remote.StatsService
import com.aptopayments.mobile.repository.transaction.TransactionListMerger
import com.aptopayments.mobile.repository.transaction.TransactionRepository
import com.aptopayments.mobile.repository.transaction.TransactionRepositoryImpl
import com.aptopayments.mobile.repository.transaction.remote.TransactionService
import com.aptopayments.mobile.repository.user.UserRepository
import com.aptopayments.mobile.repository.user.UserRepositoryImpl
import com.aptopayments.mobile.repository.user.remote.UserService
import com.aptopayments.mobile.repository.verification.VerificationRepository
import com.aptopayments.mobile.repository.verification.VerificationRepositoryImpl
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationService
import com.aptopayments.mobile.repository.voip.VoipRepository
import com.aptopayments.mobile.repository.voip.VoipRepositoryImpl
import com.aptopayments.mobile.repository.voip.remote.VoipService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val applicationModule = module {
    single { DataBaseProvider.getInstance(context = androidContext()) }
    single { get<LocalDB>().balanceLocalDao() }
    single { get<LocalDB>().cardBalanceLocalDao() }
    single { get<LocalDB>().transactionLocalDao() }
    single<CardLocalRepository> { CardLocalRepositoryImpl(androidApplication()) }
    factory<ConnectivityChecker> { ConnectivityCheckerImpl(androidContext()) }
    single { NetworkHandler(get()) }
    factory<OkHttpClientProvider> { OkHttpClientProviderImpl(ApiKeyProvider, get()) }
    factory<RetrofitFactory> { RetrofitFactoryImpl(GsonProvider, get()) }
    single { ApiCatalog(get(), ApiKeyProvider) }
    factory { RequestExecutor(get(), get()) }
    factory { ErrorHandler(get()) }
    factory { TransactionListMerger() }
}

internal val repositoryModule = module {
    single<UserSessionRepository> { UserSessionRepositoryImpl(androidContext(), get()) }
    single { UserPreferencesRepository(userSessionRepository = get(), context = get()) }
    single {
        PushTokenRepository(
            userSessionRepository = get(),
            registerPushDeviceUseCase = get(),
            unregisterPushDeviceUseCase = get(),
            context = androidContext()
        )
    }
    single { VerificationService(apiCatalog = get()) }
    single<VerificationRepository> { VerificationRepositoryImpl(service = get()) }
    single { OAuthService(apiCatalog = get()) }
    single<OAuthRepository> { OAuthRepositoryImpl(get()) }
    single { CardApplicationService(apiCatalog = get()) }
    single<CardApplicationRepository> { CardApplicationRepositoryImpl(get()) }
    single { ConfigService(apiCatalog = get()) }
    single<ConfigRepository> { ConfigRepositoryImpl(service = get()) }
    single { UserService(apiCatalog = get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { FundingSourcesService(apiCatalog = get()) }
    single<FundingSourceRepository> { FundingSourceRepositoryImpl(get(), get()) }
    single { CardService(apiCatalog = get()) }
    single<CardRepository> { CardRepositoryImpl(get(), get(), get(), get()) }
    single { TransactionService(get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(get(), get(), get(), get()) }
    single { StatsService(apiCatalog = get()) }
    single<StatsRepository> { StatsRepositoryImpl(get()) }
    single { MonthlyStatementService(get()) }
    single<MonthlyStatementRepository> { MonthlyStatementRepositoryImpl(get()) }
    single { VoipService(apiCatalog = get()) }
    single<VoipRepository> { VoipRepositoryImpl(service = get()) }
    single { PaymentSourcesService(get()) }
    single<PaymentSourcesRepository> { PaymentSourcesRepositoryImpl(get()) }
    single { PaymentService(get()) }
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
}
