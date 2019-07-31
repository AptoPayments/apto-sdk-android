package com.aptopayments.core.exception

import android.content.Context
import com.aptopayments.core.exception.Failure.FeatureFailure
import com.aptopayments.core.extension.localized

private const val UNKNOWN_SESSION = 3030
private const val SESSION_EXPIRED = 3031
private const val INVALID_SESSION = 3032
private const val EMPTY_SESSION = 3033
private const val LOGIN_ERROR_INVALID_CREDENTIALS = 90028
private const val LOGIN_ERROR_UNVERIFIED_DATAPOINTS = 90032
private const val SHIFT_CARD_ACTIVATE_ERROR = 90173
private const val SHIFT_CARD_ENABLE_ERROR = 90174
private const val SHIFT_CARD_DISABLE_ERROR = 90175
private const val PRIMARY_FUNDING_SOURCE_NOT_FOUND = 90197
private const val SHIFT_ACTIVATE_PHYSICAL_CARD_ERROR = 90206
private const val WRONG_PHYSICAL_CARD_ACTIVATION_CODE = 90207
private const val TOO_MANY_PHYSICAL_CARD_ACTIVATION_ATTEMPTS = 90208
private const val PHYSICAL_CARD_ALREADY_ACTIVATED = 90209
private const val PHYSICAL_CARD_ACTIVATION_NOT_SUPPORTED = 90210
private const val INVALID_PHYSICAL_CARD_ACTIVATION_CODE = 90211
private const val REVOKED_TOKEN = 90251
private const val INPUT_PHONE_REQUIRED = 200013
private const val INPUT_PHONE_INVALID = 200014
private const val INPUT_PHONE_NOT_ALLOWED = 200015
private const val SIGNUP_NOT_ALLOWED = 200016
private const val FIRST_NAME_REQUIRED = 200017
private const val FIRST_NAME_INVALID = 200018
private const val LAST_NAME_REQUIRED = 200019
private const val LAST_NAME_INVALID = 200020
private const val EMAIL_INVALID = 200023
private const val EMAIL_NOT_ALLOWED = 200024
private const val DOB_REQUIRED = 200025
private const val DOB_TOO_YOUNG = 200026
private const val ID_DOCUMENT_INVALID = 200027
private const val ADDRESS_INVALID = 200028
private const val POSTAL_CODE_INVALID = 200029
private const val LOCALITY_INVALID = 200030
private const val REGION_INVALID = 200031
private const val COUNTRY_INVALID = 200032
private const val DOB_INVALID = 200035
private const val CARD_ALREADY_ISSUED = 200036
private const val BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED = 200040
private const val BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT = 200041
private const val CAN_NOT_SEND_SMS = 9213
private const val INVALID_PHONE_NUMBER = 9214
private const val UNREACHABLE_PHONE_NUMBER = 9215
private const val INVALID_CALLED_PHONE_NUMBER = 9216
private const val INSUFFICIENT_FUNDS = 90196

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object MaintenanceMode : Failure()
    object DeprecatedSDK : Failure()
    object UserSessionExpired : Failure()
    class ServerError(val errorCode: Int?) : Failure() {
        val isErrorBalanceValidationsEmailSendsDisabled: Boolean = errorCode == BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED
        val isErrorBalanceValidationsInsufficientApplicationLimit: Boolean = errorCode == BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT
        val isErrorInsufficientFunds: Boolean = errorCode == INSUFFICIENT_FUNDS

        fun errorMessage(context: Context): String {
            return when (this.errorCode) {
                UNKNOWN_SESSION, INVALID_SESSION -> "error.transport.invalid_session".localized(context)
                SESSION_EXPIRED -> "error.transport.session_expired".localized(context)
                EMPTY_SESSION -> "error.transport.empty_session".localized(context)
                LOGIN_ERROR_INVALID_CREDENTIALS -> "error.transport.login_error_invalid_credentials".localized(context)
                LOGIN_ERROR_UNVERIFIED_DATAPOINTS -> "error.transport.login_error_unverified_datapoints".localized(context)
                SHIFT_CARD_ACTIVATE_ERROR -> "error.transport.shift_card_activate_error".localized(context)
                SHIFT_CARD_ENABLE_ERROR -> "error.transport.shift_card_enable_error".localized(context)
                SHIFT_CARD_DISABLE_ERROR -> "error.transport.shift_card_disable_error".localized(context)
                PRIMARY_FUNDING_SOURCE_NOT_FOUND  -> "error.transport.primary_funding_source_not_found".localized(context)
                SHIFT_ACTIVATE_PHYSICAL_CARD_ERROR, PHYSICAL_CARD_ACTIVATION_NOT_SUPPORTED  -> "error.transport.physical_card_activation_not_supported".localized(context)
                WRONG_PHYSICAL_CARD_ACTIVATION_CODE, INVALID_PHYSICAL_CARD_ACTIVATION_CODE  -> "error.transport.wrong_physical_card_activation_code".localized(context)
                REVOKED_TOKEN -> "issue_card.issue_card.error.token_revoked".localized(context)
                TOO_MANY_PHYSICAL_CARD_ACTIVATION_ATTEMPTS  -> "error.transport.too_many_physical_card_activation_attempts".localized(context)
                PHYSICAL_CARD_ALREADY_ACTIVATED  -> "error.transport.physical_card_already_activated".localized(context)
                INPUT_PHONE_REQUIRED -> "issue_card.issue_card.error.required_phone".localized(context)
                INPUT_PHONE_INVALID -> "issue_card.issue_card.error.invalid_phone".localized(context)
                INPUT_PHONE_NOT_ALLOWED -> "issue_card.issue_card.error.not_allowed_phone".localized(context)
                SIGNUP_NOT_ALLOWED -> "issue_card.issue_card.error.signup_not_allowed".localized(context)
                FIRST_NAME_REQUIRED -> "issue_card.issue_card.error.required_first_name".localized(context)
                FIRST_NAME_INVALID -> "issue_card.issue_card.error.invalid_first_name".localized(context)
                LAST_NAME_REQUIRED -> "issue_card.issue_card.error.required_last_name".localized(context)
                LAST_NAME_INVALID -> "issue_card.issue_card.error.invalid_last_name".localized(context)
                EMAIL_INVALID -> "issue_card.issue_card.error.invalid_email".localized(context)
                EMAIL_NOT_ALLOWED -> "issue_card.issue_card.error.not_allowed_email".localized(context)
                DOB_REQUIRED -> "issue_card.issue_card.error.required_dob".localized(context)
                DOB_TOO_YOUNG -> "issue_card.issue_card.error.dob_too_young".localized(context)
                ID_DOCUMENT_INVALID -> "issue_card.issue_card.error.invalid_id_document".localized(context)
                ADDRESS_INVALID -> "issue_card.issue_card.error.invalid_address".localized(context)
                POSTAL_CODE_INVALID -> "issue_card.issue_card.error.invalid_postal_code".localized(context)
                LOCALITY_INVALID -> "issue_card.issue_card.error.invalid_locality".localized(context)
                REGION_INVALID -> "issue_card.issue_card.error.invalid_region".localized(context)
                COUNTRY_INVALID -> "issue_card.issue_card.error.invalid_country".localized(context)
                CARD_ALREADY_ISSUED -> "issue_card.issue_card.error.card_already_issued".localized(context)
                DOB_INVALID -> "issue_card.issue_card.error.invalid_dob".localized(context)
                BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED -> "select_balance_store.login.error_email_sends_disabled.message".localized(context)
                BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT -> "select_balance_store.login.error_insufficient_application_limit.message".localized(context)
                CAN_NOT_SEND_SMS -> "auth.input_phone.error.can_not_send_sms".localized(context)
                INVALID_PHONE_NUMBER -> "auth.input_phone.error.invalid_phone_number".localized(context)
                UNREACHABLE_PHONE_NUMBER -> "auth.input_phone.error.unreachable_phone_number".localized(context)
                INVALID_CALLED_PHONE_NUMBER -> "auth.input_phone.error.invalid_called_phone_number".localized(context)
                else -> "error.transport.undefined".localized(context)
            }
        }

        fun isOauthTokenRevokedError() = errorCode == REVOKED_TOKEN
    }
    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}
