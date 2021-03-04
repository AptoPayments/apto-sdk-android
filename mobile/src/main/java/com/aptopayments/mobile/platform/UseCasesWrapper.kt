package com.aptopayments.mobile.platform

import com.aptopayments.mobile.repository.card.usecases.*
import com.aptopayments.mobile.repository.cardapplication.usecases.*
import com.aptopayments.mobile.repository.cardapplication.usecases.IssueCardUseCase
import com.aptopayments.mobile.repository.config.usecases.GetCardProductUseCase
import com.aptopayments.mobile.repository.config.usecases.GetCardProductsUseCase
import com.aptopayments.mobile.repository.config.usecases.GetContextConfigurationUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.AssignAchAccountToBalanceUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.GetAchAccountDetailsUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.GetFundingSourcesUseCase
import com.aptopayments.mobile.repository.oauth.usecases.GetOAuthAttemptStatusUseCase
import com.aptopayments.mobile.repository.oauth.usecases.RetrieveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.SaveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.StartOAuthAuthenticationUseCase
import com.aptopayments.mobile.repository.payment.usecases.PushFundsUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.AddPaymentSourceUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.DeletePaymentSourceUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.GetPaymentSourcesUseCase
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
import com.aptopayments.mobile.repository.verification.usecase.StartPrimaryVerificationUseCase
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

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
    val startPrimaryVerificationUseCase: StartPrimaryVerificationUseCase by inject()
    val finishVerificationUseCase: FinishVerificationUseCase by inject()
    val restartVerificationUseCase: RestartVerificationUseCase by inject()
    val startCardApplicationUseCase: StartCardApplicationUseCase by inject()
    val getCardApplicationUseCase: GetCardApplicationUseCase by inject()
    val setBalanceStoreUseCase: SetBalanceStoreUseCase by inject()
    val acceptDisclaimerUseCase: AcceptDisclaimerUseCase by inject()
    val cancelCardApplicationUseCase: CancelCardApplicationUseCase by inject()
    val issueCardUseCase: IssueCardUseCase by inject()
    val issueCardCardProductUseCase: com.aptopayments.mobile.repository.card.usecases.IssueCardUseCase by inject()
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
    val getProvisioningDataUseCase: GetProvisioningDataUseCase by inject()
    val addPaymentSourceUseCase: AddPaymentSourceUseCase by inject()
    val getPaymentSourcesUseCase: GetPaymentSourcesUseCase by inject()
    val deletePaymentSourceUseCase: DeletePaymentSourceUseCase by inject()
    val pushFundsUseCase: PushFundsUseCase by inject()
    val setCardPasscodeUseCase: SetCardPasscodeUseCase by inject()
    val reviewAgreementsUseCase: ReviewAgreementsUseCase by inject()
    val assignAchAccountToBalanceUseCase: AssignAchAccountToBalanceUseCase by inject()
    val getAchAccountDetailsUseCase: GetAchAccountDetailsUseCase by inject()
}
