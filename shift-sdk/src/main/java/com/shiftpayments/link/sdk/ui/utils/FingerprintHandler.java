package com.shiftpayments.link.sdk.ui.utils;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by adrian on 02/03/2018.
 */

public class FingerprintHandler {

    // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
    // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lockscreen!

    private CancellationSignal cancellationSignal;
    private Context mContext;
    private static final String KEY_NAME = "shiftKey";
    private Cipher mCipher;
    private KeyStore mKeyStore;
    private FingerprintManager mFingerprintManager;


    public FingerprintHandler(Context context) {
        mContext = context;
        mFingerprintManager = (FingerprintManager) mContext.getSystemService(FINGERPRINT_SERVICE);
    }

    public void startAuth(FingerprintManager.AuthenticationCallback authenticationCallback) throws FingerprintException {
        if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        generateKey();
        if (initCipher()) {
            cancellationSignal = new CancellationSignal();
            //If the cipher is initialized successfully, then create a CryptoObject instance
            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(mCipher);
            mFingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, authenticationCallback, null);
        }
    }

    public void stopListening() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    public boolean isFingerprintAuthPossible() {
        if(!mFingerprintManager.isHardwareDetected()) {
            ApiErrorUtil.showErrorMessage("Your device doesn't support fingerprint authentication", mContext);
            return false;
        }
        if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            ApiErrorUtil.showErrorMessage("Please enable the fingerprint permission", mContext);
            return false;
        }
        if(!mFingerprintManager.hasEnrolledFingerprints()) {
            ApiErrorUtil.showErrorMessage("No fingerprint configured. Please register at least one fingerprint in your device's Settings", mContext);
            return false;
        }
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(KEYGUARD_SERVICE);
        if(!keyguardManager.isKeyguardSecure()) {
            ApiErrorUtil.showErrorMessage("Please enable lockscreen security in your device's Settings", mContext);
            return false;
        }
        return true;
    }

    //Create a new method that we’ll use to initialize our cipher//
    private boolean initCipher() throws FingerprintException {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            mCipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            //Return false if cipher initialization failed
            return false;
        } catch (KeyStoreException | UnrecoverableKeyException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private void generateKey() throws FingerprintException {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");

            //Initialize an empty KeyStore//
            mKeyStore.load(null);

            //Generate the key//
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            throw new FingerprintException(exc);
        }
    }

    public class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }
}
