package com.aptopayments.core.data

import com.aptopayments.core.data.card.*
import com.aptopayments.core.data.cardproduct.CardProduct
import com.aptopayments.core.data.config.*
import com.aptopayments.core.data.geo.Country
import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.data.oauth.OAuthAttemptStatus
import com.aptopayments.core.data.oauth.OAuthUserDataUpdate
import com.aptopayments.core.data.oauth.OAuthUserDataUpdateResult
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.data.workflowaction.BalanceType
import org.mockito.Mockito
import java.net.URL

class TestDataProvider {

    companion object {
        fun provideOAuthAttempt() = OAuthAttempt(
                id = "",
                url = URL("http://www.google.es"),
                status = OAuthAttemptStatus.PENDING,
                userData = DataPointList(),
                tokenId = "",
                error = null,
                errorMessage = null
        )

        fun provideContextConfiguration() = ContextConfiguration(
                teamConfiguration = TeamConfiguration(
                        name = "",
                        logoUrl = ""),
                projectConfiguration = ProjectConfiguration(
                        name = "",
                        summary = "",
                        branding = ProjectBranding(
                                uiBackgroundPrimaryColor = 0,
                                uiBackgroundSecondaryColor = 0,
                                iconPrimaryColor = 0,
                                iconSecondaryColor = 0,
                                iconTertiaryColor = 0,
                                textPrimaryColor = 0,
                                textSecondaryColor = 0,
                                textTertiaryColor = 0,
                                textTopBarPrimaryColor = 0,
                                textTopBarSecondaryColor = 0,
                                textLinkColor = 0,
                                textLinkUnderlined = true,
                                textButtonColor = 0,
                                buttonCornerRadius = 0f,
                                uiPrimaryColor = 0,
                                uiSecondaryColor = 0,
                                uiTertiaryColor = 0,
                                uiErrorColor = 0,
                                uiSuccessColor = 0,
                                uiNavigationPrimaryColor = 0,
                                uiNavigationSecondaryColor = 0,
                                textMessageColor = 0,
                                badgeBackgroundPositiveColor = 0,
                                badgeBackgroundNegativeColor = 0,
                                showToastTitle = true,
                                transactionDetailsCollapsable = true,
                                disclaimerBackgroundColor = 0,
                                uiStatusBarStyle = UIStatusBarStyle.LIGHT,
                                logoUrl = "",
                                uiTheme = UITheme.THEME_1
                        ),
                        labels = hashMapOf(),
                        allowedCountries = arrayListOf(Country("US")),
                        supportEmailAddress = "",
                        trackerAccessToken = "",
                        isTrackerActive = false,
                        authCredential = AuthCredential.PHONE)
        )

        fun provideContextConfigurationEmail() = ContextConfiguration(
                teamConfiguration = TeamConfiguration(
                        name = "",
                        logoUrl = ""),
                projectConfiguration = ProjectConfiguration(
                        name = "",
                        summary = "",
                        branding = ProjectBranding(
                                uiBackgroundPrimaryColor = 0,
                                uiBackgroundSecondaryColor = 0,
                                iconPrimaryColor = 0,
                                iconSecondaryColor = 0,
                                iconTertiaryColor = 0,
                                textPrimaryColor = 0,
                                textSecondaryColor = 0,
                                textTertiaryColor = 0,
                                textTopBarPrimaryColor = 0,
                                textTopBarSecondaryColor = 0,
                                textLinkColor = 0,
                                textLinkUnderlined = true,
                                textButtonColor = 0,
                                buttonCornerRadius = 0f,
                                uiPrimaryColor = 0,
                                uiSecondaryColor = 0,
                                uiTertiaryColor = 0,
                                uiErrorColor = 0,
                                uiSuccessColor = 0,
                                uiNavigationPrimaryColor = 0,
                                uiNavigationSecondaryColor = 0,
                                textMessageColor = 0,
                                badgeBackgroundPositiveColor = 0,
                                badgeBackgroundNegativeColor = 0,
                                showToastTitle = true,
                                transactionDetailsCollapsable = true,
                                disclaimerBackgroundColor = 0,
                                uiStatusBarStyle = UIStatusBarStyle.LIGHT,
                                logoUrl = "",
                                uiTheme = UITheme.THEME_1
                        ),
                        labels = hashMapOf(),
                        allowedCountries = arrayListOf(Country("US")),
                        supportEmailAddress = "",
                        trackerAccessToken = "",
                        isTrackerActive = false,
                        authCredential = AuthCredential.EMAIL)
        )

        fun provideCardProduct() = CardProduct(
                cardholderAgreement = null,
                privacyPolicy = null,
                termsAndConditions = null,
                faq = null,
                waitlistBackgroundImage = null,
                waitlistBackgroundColor = null,
                waitlistAsset = null
        )

        fun provideAllowedBalanceType() = AllowedBalanceType(
                balanceType= BalanceType.COINBASE,
                baseUri = URL("http://www.aptopayments.com"))

        fun provideOAuthUserData() = OAuthUserDataUpdate(
                result = OAuthUserDataUpdateResult.VALID,
                userData = DataPointList()
        )

        fun provideCardApplicationId() = "bestCardApplicationIdEver"

        fun provideVerification() = Verification("", "")

        fun <T> anyObject(): T = Mockito.any<T>()

        fun provideCard(
                accountID: String = "",
                cardProductID: String = "",
                cardNetwork: Card.CardNetwork = Card.CardNetwork.UNKNOWN,
                lastFourDigits: String = "",
                cardBrand: String = "",
                cardIssuer: String = "",
                state: Card.CardState = Card.CardState.UNKNOWN,
                isWaitlisted: Boolean? = false,
                cardStyle: CardStyle? = null,
                kycStatus: KycStatus? = KycStatus.UNKNOWN,
                kycReason: List<String>? = null,
                orderedStatus: Card.OrderedStatus = Card.OrderedStatus.UNKNOWN,
                spendableAmount: Money? = null,
                nativeSpendableAmount: Money? = null,
                cardHolder: String = "",
                features: Features? = null
        ) = Card(
                accountID = accountID,
                cardProductID = cardProductID,
                cardNetwork = cardNetwork,
                lastFourDigits = lastFourDigits,
                cardBrand = cardBrand,
                cardIssuer = cardIssuer,
                state = state,
                isWaitlisted = isWaitlisted,
                cardStyle = cardStyle,
                kycStatus = kycStatus,
                kycReason = kycReason,
                orderedStatus = orderedStatus,
                spendableAmount = spendableAmount,
                nativeSpendableAmount = nativeSpendableAmount,
                cardHolder = cardHolder,
                features = features
        )

        fun provideUser(userData: DataPointList?) = User(userId = "user_id", token = "token", userData = userData)
    }
}
