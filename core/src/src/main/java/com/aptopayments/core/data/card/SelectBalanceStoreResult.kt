package com.aptopayments.core.data.card

import android.content.Context
import com.aptopayments.core.analytics.Event
import com.aptopayments.core.extension.localized

import java.io.Serializable

private const val COUNTRY_UNSUPPORTED = 90191
private const val REGION_UNSUPPORTED = 90192
private const val ADDRESS_UNVERIFIED = 90193
private const val CURRENCY_UNSUPPORTED = 90194
private const val CANNOT_CAPTURE_FUNDS = 90195
private const val INSUFFICIENT_FUNDS = 90196
private const val BALANCE_NOT_FOUND = 90214
private const val ACCESS_TOKEN_INVALID = 90215
private const val SCOPES_REQUIRED = 90216
private const val LEGAL_NAME_MISSING = 90222
private const val DATE_OF_BIRTH_MISSING = 90223
private const val DATE_OF_BIRTH_ERROR = 90224
private const val ADDRESS_MISSING = 90225
private const val EMAIL_MISSING = 90226
private const val EMAIL_ERROR = 90227
private const val BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED = 200040
private const val BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT = 200041
private const val IDENTITY_NOT_VERIFIED = 200046

data class SelectBalanceStoreResult (
        val result: Type,
        val errorCode: Int?
) : Serializable {

    enum class Type { VALID, INVALID }

    fun errorTitle(context: Context): String {
        return "select_balance_store.login.error.title".localized(context)
    }

    fun errorMessage(context: Context): String {
        return errorMessage(errorCode, context)
    }

    fun errorEvent(): Event {
        return when (errorCode) {
            COUNTRY_UNSUPPORTED -> Event.SelectBalanceStoreOauthConfirmCountryUnsupported
            REGION_UNSUPPORTED -> Event.SelectBalanceStoreOauthConfirmRegionUnsupported
            ADDRESS_UNVERIFIED -> Event.SelectBalanceStoreOauthConfirmAddressUnverified
            CURRENCY_UNSUPPORTED -> Event.SelectBalanceStoreOauthConfirmCurrencyUnsupported
            CANNOT_CAPTURE_FUNDS -> Event.SelectBalanceStoreOauthConfirmCannotCaptureFunds
            INSUFFICIENT_FUNDS -> Event.SelectBalanceStoreOauthConfirmInsufficientFunds
            BALANCE_NOT_FOUND -> Event.SelectBalanceStoreOauthConfirmBalanceNotFound
            ACCESS_TOKEN_INVALID -> Event.SelectBalanceStoreOauthConfirmAccessTokenInvalid
            SCOPES_REQUIRED -> Event.SelectBalanceStoreOauthConfirmScopesRequired
            LEGAL_NAME_MISSING -> Event.SelectBalanceStoreOauthConfirmLegalNameMissing
            DATE_OF_BIRTH_MISSING -> Event.SelectBalanceStoreOauthConfirmDobMissing
            DATE_OF_BIRTH_ERROR -> Event.SelectBalanceStoreOauthConfirmDobInvalid
            ADDRESS_MISSING -> Event.SelectBalanceStoreOauthConfirmAddressMissing
            EMAIL_MISSING -> Event.SelectBalanceStoreOauthConfirmEmailMissing
            EMAIL_ERROR -> Event.SelectBalanceStoreOauthConfirmEmailError
            BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED -> Event.SelectBalanceStoreOauthConfirmEmailSendsDisabled
            BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT -> Event.SelectBalanceStoreOauthConfirmInsufficientApplicationLimit
            IDENTITY_NOT_VERIFIED -> Event.SelectBalanceStoreOauthConfirmIdentityNotVerified
            else -> Event.SelectBalanceStoreOauthConfirmUnknownError
        }
    }

    companion object {
        fun errorMessage(errorCode: Int?, context: Context): String {
            return when (errorCode) {
                COUNTRY_UNSUPPORTED -> "select_balance_store.login.error_wrong_country.message".localized(context)
                REGION_UNSUPPORTED -> "select_balance_store.login.error_wrong_region.message".localized(context)
                ADDRESS_UNVERIFIED -> "select_balance_store.login.error_unverified_address.message".localized(context)
                CURRENCY_UNSUPPORTED -> "select_balance_store.login.error_unsupported_currency.message".localized(context)
                CANNOT_CAPTURE_FUNDS -> "select_balance_store.login.error_cant_capture_funds.message".localized(context)
                INSUFFICIENT_FUNDS -> "select_balance_store.login.error_insufficient_funds.message".localized(context)
                BALANCE_NOT_FOUND -> "select_balance_store.login.error_balance_not_found.message".localized(context)
                ACCESS_TOKEN_INVALID -> "select_balance_store.login.error_access_token_invalid.message".localized(context)
                SCOPES_REQUIRED -> "select_balance_store.login.error_scopes_required.message".localized(context)
                LEGAL_NAME_MISSING -> "select_balance_store.login.error_missing_legal_name.message".localized(context)
                DATE_OF_BIRTH_MISSING -> "select_balance_store.login.error_missing_birthdate.message".localized(context)
                DATE_OF_BIRTH_ERROR -> "select_balance_store.login.error_wrong_birthdate.message".localized(context)
                ADDRESS_MISSING -> "select_balance_store.login.error_missing_address.message".localized(context)
                EMAIL_MISSING -> "select_balance_store.login.error_missing_email.message".localized(context)
                EMAIL_ERROR -> "select_balance_store.login.error_wrong_email.message".localized(context)
                BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED -> "select_balance_store.login.error_email_sends_disabled.message".localized(context)
                BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT -> "select_balance_store.login.error_insufficient_application_limit.message".localized(context)
                IDENTITY_NOT_VERIFIED -> "select_balance_store.login.error_identity_not_verified.message".localized(context)
                else -> "select_balance_store.login.error_unknown.message".localized(context).replace("ERROR_CODE", errorCode.toString())
            }
        }
    }
}

