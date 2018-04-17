package com.shift.link.sdk.ui.vos;

import android.net.Uri;

/**
 * Simple representation of a document.
 * @author Wijnand
 */
public class DocumentVo {

    private final Uri mUri;
    private final String mType;

    /**
     * Creates a new {@link DocumentVo} instance.
     * @param uri URI.
     * @param type MIME type.
     */
    public DocumentVo(Uri uri, String type) {
        mUri = uri;
        mType = type;
    }

    /**
     * @return URI.
     */
    public Uri getUri() {
        return mUri;
    }

    /**
     * @return MIME type.
     */
    public String getType() {
        return mType;
    }
}
