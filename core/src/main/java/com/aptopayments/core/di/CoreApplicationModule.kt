package com.aptopayments.core.di

import android.app.Application
import android.content.Context
import com.aptopayments.core.db.DataBaseProvider
import com.aptopayments.core.db.LocalDB
import com.aptopayments.core.repository.UserPreferencesRepository
import com.aptopayments.core.repository.UserSessionRepository
import com.aptopayments.core.repository.card.CardRepository
import com.aptopayments.core.repository.card.local.CardBalanceLocalDao
import com.aptopayments.core.repository.card.local.CardLocalDao
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository
import com.aptopayments.core.repository.config.ConfigRepository
import com.aptopayments.core.repository.fundingsources.FundingSourceRepository
import com.aptopayments.core.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.core.repository.oauth.OAuthRepository
import com.aptopayments.core.repository.stats.StatsRepository
import com.aptopayments.core.repository.transaction.TransactionRepository
import com.aptopayments.core.repository.transaction.local.TransactionLocalDao
import com.aptopayments.core.repository.user.UserRepository
import com.aptopayments.core.repository.verification.VerificationRepository
import com.aptopayments.core.repository.voip.VoipRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class CoreApplicationModule(private val application: Application) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideInputPhoneRepository(dataSource: VerificationRepository.Network): VerificationRepository = dataSource

    @Provides @Singleton fun provideOAuthRepository(dataSource: OAuthRepository.Network): OAuthRepository = dataSource

    @Provides @Singleton fun provideNewCardRepository(dataSource: CardApplicationRepository.Network): CardApplicationRepository = dataSource

    @Provides @Singleton fun provideConfigRepository(dataSource: ConfigRepository.Network): ConfigRepository = dataSource

    @Provides @Singleton fun provideUserSessionRepository(): UserSessionRepository { return UserSessionRepository(provideApplicationContext()) }

    @Provides @Singleton fun provideUserRepository(dataSource: UserRepository.Network): UserRepository = dataSource

    @Provides @Singleton fun provideFundingSourceRepository(dataSource: FundingSourceRepository.Network): FundingSourceRepository = dataSource

    @Provides @Singleton fun provideBalanceLocalDao(): BalanceLocalDao = provideLocalDB().balanceLocalDao()

    @Provides @Singleton fun provideLocalDB(): LocalDB = DataBaseProvider.getInstance(provideApplicationContext())

    @Provides @Singleton fun provideCardRepository(dataSource: CardRepository.Network): CardRepository = dataSource

    @Provides @Singleton fun provideCardLocalDao(): CardLocalDao = provideLocalDB().cardLocalDao()

    @Provides @Singleton fun provideCardBalanceLocalDao(): CardBalanceLocalDao = provideLocalDB().cardBalanceLocalDao()

    @Provides @Singleton fun provideTransactionRepository(dataSource: TransactionRepository.Network): TransactionRepository = dataSource

    @Provides @Singleton fun provideTransactionLocalDao(): TransactionLocalDao = provideLocalDB().transactionLocalDao()

    @Provides @Singleton fun provideStatsRepository(dataSource: StatsRepository.Network): StatsRepository = dataSource

    @Provides @Singleton fun provideVoipRepository(dataSource: VoipRepository.Network): VoipRepository = dataSource

    @Provides @Singleton fun provideUserPreferencesRepository(): UserPreferencesRepository { return UserPreferencesRepository(context = provideApplicationContext(), userSessionRepository = provideUserSessionRepository()) }
}

