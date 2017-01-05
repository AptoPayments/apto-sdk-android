package me.ledge.link.sdk.ui.presenters.verification;

import me.ledge.link.api.vos.DataPointVo;

/**
 * Delegation interface for email verification.
 *
 * @author Adrian
 */
public interface EmailVerificationDelegate {

    void emailVerificationSucceeded(DataPointVo.Email email,
                                    EmailVerificationPresenter emailVerificationPresenter);

}
