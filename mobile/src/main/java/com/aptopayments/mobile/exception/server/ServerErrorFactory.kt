package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.exception.server.ServerErrorCodes.ADDRESS_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT
import com.aptopayments.mobile.exception.server.ServerErrorCodes.CAN_NOT_SEND_SMS
import com.aptopayments.mobile.exception.server.ServerErrorCodes.CARD_ALREADY_ISSUED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.COUNTRY_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.DOB_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.DOB_REQUIRED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.DOB_TOO_YOUNG
import com.aptopayments.mobile.exception.server.ServerErrorCodes.EMAIL_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.EMAIL_NOT_ALLOWED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.EMPTY_SESSION
import com.aptopayments.mobile.exception.server.ServerErrorCodes.FIRST_NAME_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.FIRST_NAME_REQUIRED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.ID_DOCUMENT_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INPUT_PHONE_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INPUT_PHONE_NOT_ALLOWED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INPUT_PHONE_REQUIRED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INSUFFICIENT_FUNDS
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_CALLED_PHONE_NUMBER
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_AMOUNT
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_ADDRESS
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_CVV
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_ENTITY
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_EXPIRATION
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_NETWORK
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_NUMBER
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_POSTAL_CODE
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_TYPE
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PAYMENT_SOURCE_CURRENCY
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PHONE_NUMBER
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_PHYSICAL_CARD_ACTIVATION_CODE
import com.aptopayments.mobile.exception.server.ServerErrorCodes.INVALID_SESSION
import com.aptopayments.mobile.exception.server.ServerErrorCodes.LAST_NAME_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.LAST_NAME_REQUIRED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.LOCALITY_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.LOGIN_ERROR_INVALID_CREDENTIALS
import com.aptopayments.mobile.exception.server.ServerErrorCodes.LOGIN_ERROR_UNVERIFIED_DATAPOINTS
import com.aptopayments.mobile.exception.server.ServerErrorCodes.PAYMENT_SOURCE_ADD_LIMIT
import com.aptopayments.mobile.exception.server.ServerErrorCodes.PHYSICAL_CARD_ACTIVATION_NOT_SUPPORTED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.PHYSICAL_CARD_ALREADY_ACTIVATED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.PHYSICAL_CARD_ALREADY_ORDERED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.PHYSICAL_CARD_NOT_SUPPORTED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.POSTAL_CODE_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.PRIMARY_FUNDING_SOURCE_NOT_FOUND
import com.aptopayments.mobile.exception.server.ServerErrorCodes.REGION_INVALID
import com.aptopayments.mobile.exception.server.ServerErrorCodes.REVOKED_TOKEN
import com.aptopayments.mobile.exception.server.ServerErrorCodes.SESSION_EXPIRED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.SHIFT_ACTIVATE_PHYSICAL_CARD_ERROR
import com.aptopayments.mobile.exception.server.ServerErrorCodes.SHIFT_CARD_ACTIVATE_ERROR
import com.aptopayments.mobile.exception.server.ServerErrorCodes.SHIFT_CARD_DISABLE_ERROR
import com.aptopayments.mobile.exception.server.ServerErrorCodes.SHIFT_CARD_ENABLE_ERROR
import com.aptopayments.mobile.exception.server.ServerErrorCodes.SIGNUP_NOT_ALLOWED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.STATEMENT_GENERATING_ERROR
import com.aptopayments.mobile.exception.server.ServerErrorCodes.STATEMENT_NOT_UPLOADED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.STATEMENT_URL_NOT_GENERATED
import com.aptopayments.mobile.exception.server.ServerErrorCodes.STATEMENT_URL_NOT_GENERATED2
import com.aptopayments.mobile.exception.server.ServerErrorCodes.TOO_MANY_PHYSICAL_CARD_ACTIVATION_ATTEMPTS
import com.aptopayments.mobile.exception.server.ServerErrorCodes.UNKNOWN_SESSION
import com.aptopayments.mobile.exception.server.ServerErrorCodes.UNREACHABLE_PHONE_NUMBER
import com.aptopayments.mobile.exception.server.ServerErrorCodes.WRONG_PHYSICAL_CARD_ACTIVATION_CODE

private const val UNKNOWN_ERROR = -1
private const val UNDEFINED_MESSAGE = "error.transport.undefined"

class ServerErrorFactory {

    private val genericMap = mapOf(
        UNKNOWN_SESSION to "error.transport.invalid_session",
        INVALID_SESSION to "error.transport.invalid_session",
        SESSION_EXPIRED to "error.transport.session_expired",
        EMPTY_SESSION to "error.transport.empty_session",
        LOGIN_ERROR_INVALID_CREDENTIALS to "error.transport.login_error_invalid_credentials",
        LOGIN_ERROR_UNVERIFIED_DATAPOINTS to "error.transport.login_error_unverified_datapoints",
        SHIFT_CARD_ACTIVATE_ERROR to "error.transport.shift_card_activate_error",
        SHIFT_CARD_ENABLE_ERROR to "error.transport.shift_card_enable_error",
        SHIFT_CARD_DISABLE_ERROR to "error.transport.shift_card_disable_error",
        PRIMARY_FUNDING_SOURCE_NOT_FOUND to "error.transport.primary_funding_source_not_found",
        SHIFT_ACTIVATE_PHYSICAL_CARD_ERROR to "error.transport.physical_card_activation_not_supported",
        PHYSICAL_CARD_ACTIVATION_NOT_SUPPORTED to "error.transport.physical_card_activation_not_supported",
        WRONG_PHYSICAL_CARD_ACTIVATION_CODE to "error.transport.wrong_physical_card_activation_code",
        INVALID_PHYSICAL_CARD_ACTIVATION_CODE to "error.transport.wrong_physical_card_activation_code",
        TOO_MANY_PHYSICAL_CARD_ACTIVATION_ATTEMPTS to "error.transport.too_many_physical_card_activation_attempts",
        PHYSICAL_CARD_ALREADY_ACTIVATED to "error.transport.physical_card_already_activated",
        INPUT_PHONE_REQUIRED to "issue_card.issue_card.error.required_phone",
        INPUT_PHONE_INVALID to "issue_card.issue_card.error.invalid_phone",
        INPUT_PHONE_NOT_ALLOWED to "issue_card.issue_card.error.not_allowed_phone",
        SIGNUP_NOT_ALLOWED to "issue_card.issue_card.error.signup_not_allowed",
        FIRST_NAME_REQUIRED to "issue_card.issue_card.error.required_first_name",
        FIRST_NAME_INVALID to "issue_card.issue_card.error.invalid_first_name",
        LAST_NAME_REQUIRED to "issue_card.issue_card.error.required_last_name",
        LAST_NAME_INVALID to "issue_card.issue_card.error.invalid_last_name",
        EMAIL_INVALID to "issue_card.issue_card.error.invalid_email",
        EMAIL_NOT_ALLOWED to "issue_card.issue_card.error.not_allowed_email",
        DOB_REQUIRED to "issue_card.issue_card.error.required_dob",
        DOB_TOO_YOUNG to "issue_card.issue_card.error.dob_too_young",
        ID_DOCUMENT_INVALID to "issue_card.issue_card.error.invalid_id_document",
        ADDRESS_INVALID to "issue_card.issue_card.error.invalid_address",
        POSTAL_CODE_INVALID to "issue_card.issue_card.error.invalid_postal_code",
        LOCALITY_INVALID to "issue_card.issue_card.error.invalid_locality",
        REGION_INVALID to "issue_card.issue_card.error.invalid_region",
        COUNTRY_INVALID to "issue_card.issue_card.error.invalid_country",
        CARD_ALREADY_ISSUED to "issue_card.issue_card.error.card_already_issued",
        DOB_INVALID to "issue_card.issue_card.error.invalid_dob",
        CAN_NOT_SEND_SMS to "auth.input_phone.error.can_not_send_sms",
        INVALID_PHONE_NUMBER to "auth.input_phone.error.invalid_phone_number",
        UNREACHABLE_PHONE_NUMBER to "auth.input_phone.error.unreachable_phone_number",
        INVALID_CALLED_PHONE_NUMBER to "auth.input_phone.error.invalid_called_phone_number",
        STATEMENT_URL_NOT_GENERATED to "monthly_statements.list.error_generating_report.message",
        STATEMENT_NOT_UPLOADED to "monthly_statements.list.error_generating_report.message",
        STATEMENT_URL_NOT_GENERATED2 to "monthly_statements.list.error_generating_report.message",
        STATEMENT_GENERATING_ERROR to "monthly_statements.list.error_generating_report.message",
        INVALID_PAYMENT_SOURCE_CARD_NETWORK to "load_funds_add_card_error_message",
        INVALID_PAYMENT_SOURCE_CARD_TYPE to "load_funds_add_card_error_message",
        INVALID_PAYMENT_SOURCE_CARD_ENTITY to "load_funds_add_card_error_message",
        INVALID_PAYMENT_SOURCE_CARD_NUMBER to "load_funds_add_card_error_number",
        INVALID_PAYMENT_SOURCE_CARD_CVV to "load_funds_add_card_error_cvv",
        INVALID_PAYMENT_SOURCE_CARD_EXPIRATION to "load_funds_add_card_error_expiration",
        INVALID_PAYMENT_SOURCE_CARD_POSTAL_CODE to "load_funds_add_card_error_postal_code",
        INVALID_PAYMENT_SOURCE_CARD_ADDRESS to "load_funds_add_card_error_address",
        INVALID_PAYMENT_SOURCE_AMOUNT to "load_funds_add_money_error_limit",
        INVALID_PAYMENT_SOURCE_CURRENCY to "load_funds_add_money_error_message",
        PAYMENT_SOURCE_ADD_LIMIT to "load_funds_add_card_error_limit",
        PHYSICAL_CARD_ALREADY_ORDERED to "error.physical_card.card_already_ordered",
        PHYSICAL_CARD_NOT_SUPPORTED to "error.physical_card.order_not_supported",
    )

    fun create(code: Int? = UNKNOWN_ERROR, message: String? = null): Failure.ServerError {

        return when {
            genericMap.containsKey(code) -> createGenericServerError(code, message, genericMap[code]!!)
            code == BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED -> ErrorBalanceValidationsEmailSendsDisabled(message)
            code == BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT ->
                ErrorBalanceValidationsInsufficientApplicationLimit(message)
            code == REVOKED_TOKEN -> ErrorOauthTokenRevoked(message)
            code == INSUFFICIENT_FUNDS -> ErrorInsufficientFunds(message)
            else -> createGenericServerError(code, message, UNDEFINED_MESSAGE)
        }
    }

    private fun createGenericServerError(code: Int? = UNKNOWN_ERROR, message: String? = null, errorKey: String): Failure.ServerError {
        return Failure.ServerError(code, message, errorKey)
    }
}
