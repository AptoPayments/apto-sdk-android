package com.aptopayments.core.platform

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.di.CoreApplicationComponent
import com.aptopayments.core.repository.card.usecases.*
import com.aptopayments.core.repository.cardapplication.usecases.*
import com.aptopayments.core.repository.config.usecases.GetCardProductUseCase
import com.aptopayments.core.repository.cardapplication.usecases.IssueCardUseCase
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
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class UseCasesWrapper @Inject constructor(appComponent: CoreApplicationComponent) {

    @Inject lateinit var getContextConfigurationUseCase: GetContextConfigurationUseCase
    @Inject lateinit var getCardProductUseCase: GetCardProductUseCase
    @Inject lateinit var getCardProductsUseCase: GetCardProductsUseCase
    @Inject lateinit var createUserUseCase: CreateUserUseCase
    @Inject lateinit var loginUserUseCase: LoginUserUseCase
    @Inject lateinit var updateUserDataUseCase: UpdateUserDataUseCase
    @Inject lateinit var startOAuthAuthenticationUseCase: StartOAuthAuthenticationUseCase
    @Inject lateinit var getOAuthAttemptStatusUseCase: GetOAuthAttemptStatusUseCase
    @Inject lateinit var saveOAuthUserDataUseCase: SaveOAuthUserDataUseCase
    @Inject lateinit var retrieveOAuthUserDataUseCase: RetrieveOAuthUserDataUseCase
    @Inject lateinit var startPhoneVerificationUseCase: StartPhoneVerificationUseCase
    @Inject lateinit var startEmailVerificationUseCase: StartEmailVerificationUseCase
    @Inject lateinit var finishVerificationUseCase: FinishVerificationUseCase
    @Inject lateinit var restartVerificationUseCase: RestartVerificationUseCase
    @Inject lateinit var startCardApplicationUseCase: StartCardApplicationUseCase
    @Inject lateinit var getCardApplicationUseCase: GetCardApplicationUseCase
    @Inject lateinit var setBalanceStoreUseCase: SetBalanceStoreUseCase
    @Inject lateinit var acceptDisclaimerUseCase: AcceptDisclaimerUseCase
    @Inject lateinit var cancelCardApplicationUseCase: CancelCardApplicationUseCase
    @Inject lateinit var issueCardUseCase: IssueCardUseCase
    @Inject lateinit var issueCardCardProductUseCase: com.aptopayments.core.repository.card.usecases.IssueCardUseCase
    @Inject lateinit var getCardsUseCase: GetCardsUseCase
    @Inject lateinit var getCardUseCase: GetCardUseCase
    @Inject lateinit var getCardDetailsUseCase: GetCardDetailsUseCase
    @Inject lateinit var activatePhysicalCardUseCase: ActivatePhysicalCardUseCase
    @Inject lateinit var unlockCardUseCase: UnlockCardUseCase
    @Inject lateinit var lockCardUseCase: LockCardUseCase
    @Inject lateinit var setPinUseCase: SetPinUseCase
    @Inject lateinit var getTransactionsUseCase: GetTransactionsUseCase
    @Inject lateinit var getMonthlySpendingUseCase: GetMonthlySpendingUseCase
    @Inject lateinit var clearMonthlySpendingCacheUseCase: ClearMonthlySpendingCacheUseCase
    @Inject lateinit var getFundingSourcesUseCase: GetFundingSourcesUseCase
    @Inject lateinit var getCardBalanceUseCase: GetCardBalanceUseCase
    @Inject lateinit var setFundingSourcesUseCase: SetCardFundingSourceUseCase
    @Inject lateinit var addCardBalanceUseCase: AddCardBalanceUseCase
    @Inject lateinit var getNotificationPreferencesUseCase: GetNotificationPreferencesUseCase
    @Inject lateinit var updateNotificationPreferencesUseCase: UpdateNotificationPreferencesUseCase
    @Inject lateinit var setupVoipCallUseCase: SetupVoipCallUseCase

    init {
        appComponent.inject(this)
    }
}
