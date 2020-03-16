package com.aptopayments.core.platform

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.repository.UserSessionRepository
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
import com.aptopayments.core.repository.statements.usecases.GetMonthlyStatementPeriodUseCase
import com.aptopayments.core.repository.statements.usecases.GetMonthlyStatementUseCase
import com.aptopayments.core.repository.stats.usecases.ClearMonthlySpendingCacheUseCase
import com.aptopayments.core.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.core.repository.transaction.usecases.GetTransactionsUseCase
import com.aptopayments.core.repository.user.usecases.*
import com.aptopayments.core.repository.verification.usecase.FinishVerificationUseCase
import com.aptopayments.core.repository.verification.usecase.RestartVerificationUseCase
import com.aptopayments.core.repository.verification.usecase.StartEmailVerificationUseCase
import com.aptopayments.core.repository.verification.usecase.StartPhoneVerificationUseCase
import com.aptopayments.core.repository.voip.usecases.SetupVoipCallUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class UseCasesWrapper : KoinComponent {
    val getContextConfigurationUseCase: GetContextConfigurationUseCase by inject()
    val getCardProductUseCase: GetCardProductUseCase by inject()
    val getCardProductsUseCase: GetCardProductsUseCase by inject()
    val createUserUseCase: CreateUserUseCase by inject()
    val loginUserUseCase: LoginUserUseCase by inject()
    val updateUserDataUseCase: UpdateUserDataUseCase by inject()
    val startOAuthAuthenticationUseCase: StartOAuthAuthenticationUseCase by inject()
    val getOAuthAttemptStatusUseCase: GetOAuthAttemptStatusUseCase by inject()
    val saveOAuthUserDataUseCase: SaveOAuthUserDataUseCase by inject()
    val retrieveOAuthUserDataUseCase: RetrieveOAuthUserDataUseCase by inject()
    val startPhoneVerificationUseCase: StartPhoneVerificationUseCase by inject()
    val startEmailVerificationUseCase: StartEmailVerificationUseCase by inject()
    val finishVerificationUseCase: FinishVerificationUseCase by inject()
    val restartVerificationUseCase: RestartVerificationUseCase by inject()
    val startCardApplicationUseCase: StartCardApplicationUseCase by inject()
    val getCardApplicationUseCase: GetCardApplicationUseCase by inject()
    val setBalanceStoreUseCase: SetBalanceStoreUseCase by inject()
    val acceptDisclaimerUseCase: AcceptDisclaimerUseCase by inject()
    val cancelCardApplicationUseCase: CancelCardApplicationUseCase by inject()
    val issueCardUseCase: IssueCardUseCase by inject()
    val issueCardCardProductUseCase: com.aptopayments.core.repository.card.usecases.IssueCardUseCase by inject()
    val getCardsUseCase: GetCardsUseCase by inject()
    val getCardUseCase: GetCardUseCase by inject()
    val getCardDetailsUseCase: GetCardDetailsUseCase by inject()
    val activatePhysicalCardUseCase: ActivatePhysicalCardUseCase by inject()
    val unlockCardUseCase: UnlockCardUseCase by inject()
    val lockCardUseCase: LockCardUseCase by inject()
    val setPinUseCase: SetPinUseCase by inject()
    val getTransactionsUseCase: GetTransactionsUseCase by inject()
    val getMonthlySpendingUseCase: GetMonthlySpendingUseCase by inject()
    val getMonthlyStatementUseCase: GetMonthlyStatementUseCase by inject()
    val getMonthlyStatementPeriodUseCase: GetMonthlyStatementPeriodUseCase by inject()
    val clearMonthlySpendingCacheUseCase: ClearMonthlySpendingCacheUseCase by inject()
    val getFundingSourcesUseCase: GetFundingSourcesUseCase by inject()
    val getCardBalanceUseCase: GetCardBalanceUseCase by inject()
    val setFundingSourcesUseCase: SetCardFundingSourceUseCase by inject()
    val addCardBalanceUseCase: AddCardBalanceUseCase by inject()
    val getNotificationPreferencesUseCase: GetNotificationPreferencesUseCase by inject()
    val updateNotificationPreferencesUseCase: UpdateNotificationPreferencesUseCase by inject()
    val setupVoipCallUseCase: SetupVoipCallUseCase by inject()
    val userSessionRepository: UserSessionRepository by inject()
    val getProvisioningDataUseCase: GetProvisioningDataUseCase by inject()
}
