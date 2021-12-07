package com.aptopayments.mobile.exception.server

internal object ServerErrorCodes {
    const val UNKNOWN_SESSION = 3030
    const val SESSION_EXPIRED = 3031
    const val INVALID_SESSION = 3032
    const val EMPTY_SESSION = 3033
    const val LOGIN_ERROR_INVALID_CREDENTIALS = 90028
    const val LOGIN_ERROR_UNVERIFIED_DATAPOINTS = 90032
    const val SHIFT_CARD_ACTIVATE_ERROR = 90173
    const val SHIFT_CARD_ENABLE_ERROR = 90174
    const val SHIFT_CARD_DISABLE_ERROR = 90175
    const val PRIMARY_FUNDING_SOURCE_NOT_FOUND = 90197
    const val SHIFT_ACTIVATE_PHYSICAL_CARD_ERROR = 90206
    const val WRONG_PHYSICAL_CARD_ACTIVATION_CODE = 90207
    const val TOO_MANY_PHYSICAL_CARD_ACTIVATION_ATTEMPTS = 90208
    const val PHYSICAL_CARD_ALREADY_ACTIVATED = 90209
    const val PHYSICAL_CARD_ACTIVATION_NOT_SUPPORTED = 90210
    const val INVALID_PHYSICAL_CARD_ACTIVATION_CODE = 90211
    const val PHYSICAL_CARD_ALREADY_ORDERED = 90230
    const val PHYSICAL_CARD_NOT_SUPPORTED = 90231
    const val REVOKED_TOKEN = 90251
    const val INPUT_PHONE_REQUIRED = 200013
    const val INPUT_PHONE_INVALID = 200014
    const val INPUT_PHONE_NOT_ALLOWED = 200015
    const val SIGNUP_NOT_ALLOWED = 200016
    const val FIRST_NAME_REQUIRED = 200017
    const val FIRST_NAME_INVALID = 200018
    const val LAST_NAME_REQUIRED = 200019
    const val LAST_NAME_INVALID = 200020
    const val EMAIL_INVALID = 200023
    const val EMAIL_NOT_ALLOWED = 200024
    const val DOB_REQUIRED = 200025
    const val DOB_TOO_YOUNG = 200026
    const val ID_DOCUMENT_INVALID = 200027
    const val ADDRESS_INVALID = 200028
    const val POSTAL_CODE_INVALID = 200029
    const val LOCALITY_INVALID = 200030
    const val REGION_INVALID = 200031
    const val COUNTRY_INVALID = 200032
    const val DOB_INVALID = 200035
    const val CARD_ALREADY_ISSUED = 200036
    const val BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED = 200040
    const val BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT = 200041
    const val CAN_NOT_SEND_SMS = 9213
    const val INVALID_PHONE_NUMBER = 9214
    const val UNREACHABLE_PHONE_NUMBER = 9215
    const val INVALID_CALLED_PHONE_NUMBER = 9216
    const val INSUFFICIENT_FUNDS = 90196
    const val STATEMENT_URL_NOT_GENERATED = 200043
    const val STATEMENT_NOT_UPLOADED = 200044
    const val STATEMENT_URL_NOT_GENERATED2 = 200045
    const val STATEMENT_GENERATING_ERROR = 200051
    const val INVALID_PAYMENT_SOURCE_CARD_TYPE = 200058
    const val INVALID_PAYMENT_SOURCE_CARD_NUMBER = 200059
    const val INVALID_PAYMENT_SOURCE_CARD_CVV = 200060
    const val INVALID_PAYMENT_SOURCE_CARD_EXPIRATION = 200061
    const val INVALID_PAYMENT_SOURCE_CARD_POSTAL_CODE = 200062
    const val INVALID_PAYMENT_SOURCE_CARD_ADDRESS = 200063
    const val INVALID_PAYMENT_SOURCE_CARD_ENTITY = 200064
    const val INVALID_PAYMENT_SOURCE_AMOUNT = 200065
    const val INVALID_PAYMENT_SOURCE_CURRENCY = 200066
    const val PAYMENT_SOURCE_ADD_LIMIT = 200067
    const val INVALID_PAYMENT_SOURCE_CARD_NETWORK = 200068
    const val INVALID_PAYMENT_SOURCE_DUPLICATE = 200074
    const val INVALID_RECIPIENT_EMAIL = 200023
    const val INVALID_RECIPIENT_PHONE = 200014
    const val RECIPIENT_NOT_FOUND = 200116
    const val LOAD_FUNDS_DAILY_LIMIT_EXCEEDED = 200069
    const val P2P_SELF_SEND = 200115
}
