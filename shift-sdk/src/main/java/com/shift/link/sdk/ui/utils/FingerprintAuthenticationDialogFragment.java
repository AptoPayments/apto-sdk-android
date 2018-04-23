package com.shift.link.sdk.ui.utils;


import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shift.link.sdk.ui.R;


/**
 * Created by adrian on 05/03/2018.
 */

public class FingerprintAuthenticationDialogFragment extends DialogFragment {
    private FingerprintHandler mFingerprintHandler;
    private FingerprintDelegate mFingerprintDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.fingerprint_title));
        return inflater.inflate(R.layout.fingerprint_dialog_container, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintHandler.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mFingerprintHandler.startAuth(new FingerprintManager.AuthenticationCallback() {
                    @Override
                    //onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters
                    public void onAuthenticationError(int errMsgId, CharSequence errString) {
                        mFingerprintHandler.stopListening();
                        if(mFingerprintDelegate != null) {
                            mFingerprintDelegate.onAuthenticationFailed("Authentication error\n" + errString);
                        }
                        dismiss();
                    }

                    @Override
                    //onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device
                    public void onAuthenticationFailed() {
                        mFingerprintHandler.stopListening();
                        if(mFingerprintDelegate != null) {
                            mFingerprintDelegate.onAuthenticationFailed("Authentication failed");
                        }
                        dismiss();
                    }

                    @Override
                    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error
                    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                        mFingerprintHandler.stopListening();
                        if(mFingerprintDelegate != null) {
                            mFingerprintDelegate.onAuthenticationFailed("Authentication help\n" + helpString);
                        }
                        dismiss();
                    }

                    @Override
                    //onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        mFingerprintHandler.stopListening();
                        // Let the activity know that authentication was successful.
                        if(mFingerprintDelegate != null) {
                            mFingerprintDelegate.onUserAuthenticated();
                        }
                        dismiss();

                    }
                });
            }
        } catch (FingerprintHandler.FingerprintException e) {
            if(mFingerprintDelegate != null) {
                mFingerprintDelegate.onAuthenticationFailed("Error: " + e.getMessage());
            }
        }
    }

    public void setFingerprintHandler(FingerprintHandler fingerprintHandler) {
        mFingerprintHandler = fingerprintHandler;
    }

    public void setFingerprintDelegate(FingerprintDelegate fingerprintDelegate) {
        mFingerprintDelegate = fingerprintDelegate;
    }
}

