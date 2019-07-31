package com.aptopayments.core.di

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.aptopayments.core.platform.NetworkHandlerWrapper
import com.aptopayments.core.platform.PushTokenRepositoryWrapper
import com.aptopayments.core.platform.UseCasesWrapper

import dagger.Component
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreApplicationModule::class, NetworkComponent::class])
@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal interface CoreApplicationComponent {

    fun inject(application: Application)

    fun inject(networkHandlerWrapper: NetworkHandlerWrapper)

    fun inject(pushTokenRepositoryWrapper: PushTokenRepositoryWrapper)

    fun inject(useCasesWrapper: UseCasesWrapper)
}
