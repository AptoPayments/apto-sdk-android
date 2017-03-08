package me.ledge.link.api.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Credential data
 * @author Adrian
 */

public class CredentialVo {
    public enum CredentialType{
        phone(0),
        email(1);

        private final int mType;

        CredentialType(final int type) {
            mType = type;
        }

        public int getType() { return mType; }
    }

    public int getCredentialType() {
        return mCredentialType.getType();
    }

    @SerializedName("credential_type")
    public CredentialType mCredentialType;
    public String credential;

    /**
     * Creates a new {@link CredentialVo} instance.
     */
    public CredentialVo(CredentialType type, String value) {
        mCredentialType = type;
        credential = value;
    }

}