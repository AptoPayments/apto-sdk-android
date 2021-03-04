package com.aptopayments.mobile.data.user.agreements

/**
 * This object is used to communicate th user reaction [AgreementAction] to a certain agreement
 */
data class ReviewAgreementsInput(val keys: List<String>, val action: AgreementAction)
