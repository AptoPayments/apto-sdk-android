package me.ledge.link.sdk.ui.presenters.verification;

import android.app.Activity;

import me.ledge.link.api.vos.DataPointVo;

/**
 * Delegation interface for phone verification.
 *
 * @author Adrian
 */
public interface PhoneVerificationDelegate {

    void phoneVerificationSucceeded(DataPointVo.PhoneNumber phoneNumber,
                                           PhoneVerificationPresenter phoneVerificationPresenter);

    void newPhoneVerificationSucceeded(DataPointVo.PhoneNumber phoneNumber,
                                       PhoneVerificationPresenter phoneVerificationPresenter,
                                       Activity current);
}
