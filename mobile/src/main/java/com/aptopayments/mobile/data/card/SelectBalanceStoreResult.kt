package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.card.SelectBalanceStoreError.*
import com.aptopayments.mobile.data.card.SelectBalanceStoreResult.Type.VALID
import com.aptopayments.mobile.extension.localized
import java.io.Serializable

data class SelectBalanceStoreResult(
    val result: Type,
    val errorCode: Int?,
    private val errorMessageKeys: List<String>? = null
) : Serializable {

    val error = SelectBalanceStoreError.parseError(errorCode)

    enum class Type { VALID, INVALID }

    fun errorMessage(): String {
        customErrorMessage?.let { return it.localized() }
        return when (error) {
            COUNTRY_UNSUPPORTED -> "select_balance_store.login.error_wrong_country.message".localized()
            REGION_UNSUPPORTED -> "select_balance_store.login.error_wrong_region.message".localized()
            ADDRESS_UNVERIFIED -> "select_balance_store.login.error_unverified_address.message".localized()
            CURRENCY_UNSUPPORTED -> "select_balance_store.login.error_unsupported_currency.message".localized()
            CANNOT_CAPTURE_FUNDS -> "select_balance_store.login.error_cant_capture_funds.message".localized()
            INSUFFICIENT_FUNDS -> "select_balance_store.login.error_insufficient_funds.message".localized()
            BALANCE_NOT_FOUND -> "select_balance_store.login.error_balance_not_found.message".localized()
            ACCESS_TOKEN_INVALID -> "select_balance_store.login.error_access_token_invalid.message".localized()
            SCOPES_REQUIRED -> "select_balance_store.login.error_scopes_required.message".localized()
            LEGAL_NAME_MISSING -> "select_balance_store.login.error_missing_legal_name.message".localized()
            DATE_OF_BIRTH_MISSING -> "select_balance_store.login.error_missing_birthdate.message".localized()
            DATE_OF_BIRTH_ERROR -> "select_balance_store.login.error_wrong_birthdate.message".localized()
            ADDRESS_MISSING -> "select_balance_store.login.error_missing_address.message".localized()
            EMAIL_MISSING -> "select_balance_store.login.error_missing_email.message".localized()
            EMAIL_ERROR -> "select_balance_store.login.error_wrong_email.message".localized()
            BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED ->
                "select_balance_store.login.error_email_sends_disabled.message".localized()
            BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT ->
                "select_balance_store.login.error_insufficient_application_limit.message".localized()
            IDENTITY_NOT_VERIFIED -> "select_balance_store.login.error_identity_not_verified.message".localized()
            else ->
                "select_balance_store.login.error_unknown.message".localized()
                    .replace("ERROR_CODE", errorCode?.toString() ?: "")
        }
    }

    private val customErrorMessage: String?
        get() {
            if (result == VALID) return null
            return when (error) {
                COUNTRY_UNSUPPORTED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_wrong_country.message") }
                REGION_UNSUPPORTED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_wrong_region.message") }
                ADDRESS_UNVERIFIED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_unverified_address.message") }
                CURRENCY_UNSUPPORTED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_unsupported_currency.message") }
                CANNOT_CAPTURE_FUNDS ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_cant_capture_funds.message") }
                INSUFFICIENT_FUNDS ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_insufficient_funds.message") }
                BALANCE_NOT_FOUND ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_balance_not_found.message") }
                ACCESS_TOKEN_INVALID ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_access_token_invalid.message") }
                SCOPES_REQUIRED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_scopes_required.message") }
                LEGAL_NAME_MISSING ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_missing_legal_name.message") }
                DATE_OF_BIRTH_MISSING ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_missing_birthdate.message") }
                DATE_OF_BIRTH_ERROR ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_wrong_birthdate.message") }
                ADDRESS_MISSING ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_missing_address.message") }
                EMAIL_MISSING ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_missing_email.message") }
                EMAIL_ERROR ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_wrong_email.message") }
                BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_email_sends_disabled.message") }
                BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_insufficient_application_limit.message") }
                IDENTITY_NOT_VERIFIED ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_identity_not_verified.message") }
                else ->
                    errorMessageKeys?.firstOrNull { it.endsWith("login.error_unknown.message") }
            }
        }
}
