package me.ledge.link.api.vos.datapoints;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CredentialVo that = (CredentialVo) o;

        if (mCredentialType != that.mCredentialType) return false;
        return credential != null ? credential.equals(that.credential) : that.credential == null;

    }

    @Override
    public int hashCode() {
        int result = mCredentialType != null ? mCredentialType.hashCode() : 0;
        result = 31 * result + (credential != null ? credential.hashCode() : 0);
        return result;
    }
}