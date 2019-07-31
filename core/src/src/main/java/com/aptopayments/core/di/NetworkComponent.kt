package com.aptopayments.core.di

import com.aptopayments.core.network.ApiCatalog
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkComponent {

    @Provides
    @Singleton
    fun provideApiCatalog(): ApiCatalog { return ApiCatalog() }
}
