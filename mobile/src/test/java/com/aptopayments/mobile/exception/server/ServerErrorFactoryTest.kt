package com.aptopayments.mobile.exception.server

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ServerErrorFactoryTest {

    private val sut = ServerErrorFactory()

    private val completeErrorKeyMap = mapOf(
        ServerErrorCodes.UNKNOWN_SESSION to "error.transport.invalid_session",
        ServerErrorCodes.INVALID_SESSION to "error.transport.invalid_session",
        ServerErrorCodes.SESSION_EXPIRED to "error.transport.session_expired",
        ServerErrorCodes.EMPTY_SESSION to "error.transport.empty_session",
        ServerErrorCodes.LOGIN_ERROR_INVALID_CREDENTIALS to "error.transport.login_error_invalid_credentials",
        ServerErrorCodes.LOGIN_ERROR_UNVERIFIED_DATAPOINTS to "error.transport.login_error_unverified_datapoints",
        ServerErrorCodes.SHIFT_CARD_ACTIVATE_ERROR to "error.transport.shift_card_activate_error",
        ServerErrorCodes.SHIFT_CARD_ENABLE_ERROR to "error.transport.shift_card_enable_error",
        ServerErrorCodes.SHIFT_CARD_DISABLE_ERROR to "error.transport.shift_card_disable_error",
        ServerErrorCodes.PRIMARY_FUNDING_SOURCE_NOT_FOUND to "error.transport.primary_funding_source_not_found",
        ServerErrorCodes.SHIFT_ACTIVATE_PHYSICAL_CARD_ERROR to "error.transport.physical_card_activation_not_supported",
        ServerErrorCodes.PHYSICAL_CARD_ACTIVATION_NOT_SUPPORTED to "error.transport.physical_card_activation_not_supported",
        ServerErrorCodes.WRONG_PHYSICAL_CARD_ACTIVATION_CODE to "error.transport.wrong_physical_card_activation_code",
        ServerErrorCodes.INVALID_PHYSICAL_CARD_ACTIVATION_CODE to "error.transport.wrong_physical_card_activation_code",
        ServerErrorCodes.TOO_MANY_PHYSICAL_CARD_ACTIVATION_ATTEMPTS to "error.transport.too_many_physical_card_activation_attempts",
        ServerErrorCodes.PHYSICAL_CARD_ALREADY_ACTIVATED to "error.transport.physical_card_already_activated",
        ServerErrorCodes.INPUT_PHONE_REQUIRED to "issue_card.issue_card.error.required_phone",
        ServerErrorCodes.INPUT_PHONE_INVALID to "issue_card.issue_card.error.invalid_phone",
        ServerErrorCodes.INPUT_PHONE_NOT_ALLOWED to "issue_card.issue_card.error.not_allowed_phone",
        ServerErrorCodes.SIGNUP_NOT_ALLOWED to "issue_card.issue_card.error.signup_not_allowed",
        ServerErrorCodes.FIRST_NAME_REQUIRED to "issue_card.issue_card.error.required_first_name",
        ServerErrorCodes.FIRST_NAME_INVALID to "issue_card.issue_card.error.invalid_first_name",
        ServerErrorCodes.LAST_NAME_REQUIRED to "issue_card.issue_card.error.required_last_name",
        ServerErrorCodes.LAST_NAME_INVALID to "issue_card.issue_card.error.invalid_last_name",
        ServerErrorCodes.EMAIL_INVALID to "issue_card.issue_card.error.invalid_email",
        ServerErrorCodes.EMAIL_NOT_ALLOWED to "issue_card.issue_card.error.not_allowed_email",
        ServerErrorCodes.DOB_REQUIRED to "issue_card.issue_card.error.required_dob",
        ServerErrorCodes.DOB_TOO_YOUNG to "issue_card.issue_card.error.dob_too_young",
        ServerErrorCodes.ID_DOCUMENT_INVALID to "issue_card.issue_card.error.invalid_id_document",
        ServerErrorCodes.ADDRESS_INVALID to "issue_card.issue_card.error.invalid_address",
        ServerErrorCodes.POSTAL_CODE_INVALID to "issue_card.issue_card.error.invalid_postal_code",
        ServerErrorCodes.LOCALITY_INVALID to "issue_card.issue_card.error.invalid_locality",
        ServerErrorCodes.REGION_INVALID to "issue_card.issue_card.error.invalid_region",
        ServerErrorCodes.COUNTRY_INVALID to "issue_card.issue_card.error.invalid_country",
        ServerErrorCodes.CARD_ALREADY_ISSUED to "issue_card.issue_card.error.card_already_issued",
        ServerErrorCodes.DOB_INVALID to "issue_card.issue_card.error.invalid_dob",
        ServerErrorCodes.CAN_NOT_SEND_SMS to "auth.input_phone.error.can_not_send_sms",
        ServerErrorCodes.INVALID_PHONE_NUMBER to "auth.input_phone.error.invalid_phone_number",
        ServerErrorCodes.UNREACHABLE_PHONE_NUMBER to "auth.input_phone.error.unreachable_phone_number",
        ServerErrorCodes.INVALID_CALLED_PHONE_NUMBER to "auth.input_phone.error.invalid_called_phone_number",
        ServerErrorCodes.STATEMENT_URL_NOT_GENERATED to "monthly_statements.list.error_generating_report.message",
        ServerErrorCodes.STATEMENT_NOT_UPLOADED to "monthly_statements.list.error_generating_report.message",
        ServerErrorCodes.STATEMENT_URL_NOT_GENERATED2 to "monthly_statements.list.error_generating_report.message",
        ServerErrorCodes.STATEMENT_GENERATING_ERROR to "monthly_statements.list.error_generating_report.message",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_NETWORK to "load_funds_add_card_error_message",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_TYPE to "load_funds_add_card_error_message",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_ENTITY to "load_funds_add_card_error_message",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_NUMBER to "load_funds_add_card_error_number",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_CVV to "load_funds_add_card_error_cvv",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_EXPIRATION to "load_funds_add_card_error_expiration",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_POSTAL_CODE to "load_funds_add_card_error_postal_code",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CARD_ADDRESS to "load_funds_add_card_error_address",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_AMOUNT to "load_funds_add_money_error_limit",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_CURRENCY to "load_funds_add_money_error_message",
        ServerErrorCodes.PAYMENT_SOURCE_ADD_LIMIT to "load_funds_add_card_error_limit",
        ServerErrorCodes.PHYSICAL_CARD_ALREADY_ORDERED to "error.physical_card.card_already_ordered",
        ServerErrorCodes.PHYSICAL_CARD_NOT_SUPPORTED to "error.physical_card.order_not_supported",
        ServerErrorCodes.BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED to "select_balance_store.login.error_email_sends_disabled.message",
        ServerErrorCodes.BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT to "select_balance_store.login.error_insufficient_application_limit.message",
        ServerErrorCodes.INSUFFICIENT_FUNDS to "error.transport.undefined",
        ServerErrorCodes.REVOKED_TOKEN to "issue_card.issue_card.error.token_revoked",
        ServerErrorCodes.INVALID_PAYMENT_SOURCE_DUPLICATE to "load_funds_add_card_error_duplicate"
    )

    @Test
    fun `whenever an error is created then correct copy set in ErrorKey`() {
        completeErrorKeyMap.entries.forEach {
            val error = sut.create(it.key)
            assertEquals(it.value, error.errorKey)
        }
    }

    @Test
    fun `given BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED code when create then ErrorBalanceValidationsEmailSendsDisabled`() {
        val error = sut.create(ServerErrorCodes.BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED)

        assertTrue(error is ErrorBalanceValidationsEmailSendsDisabled)
    }

    @Test
    fun `given BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT code when create then ErrorBalanceValidationsInsufficientApplicationLimit`() {
        val error = sut.create(ServerErrorCodes.BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT)

        assertTrue(error is ErrorBalanceValidationsInsufficientApplicationLimit)
    }

    @Test
    fun `given REVOKED_TOKEN code when create then ErrorOauthTokenRevoked`() {
        val error = sut.create(ServerErrorCodes.REVOKED_TOKEN)

        assertTrue(error is ErrorOauthTokenRevoked)
    }

    @Test
    fun `given INSUFFICIENT_FUNDS code when create then ErrorInsufficientFunds`() {
        val error = sut.create(ServerErrorCodes.INSUFFICIENT_FUNDS)

        assertTrue(error is ErrorInsufficientFunds)
    }
}
