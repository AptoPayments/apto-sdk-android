package com.shift.link.sdk.api.vos.responses.config;

/**
 * Disclaimers data.
 * @author Adrian
 */
public class ContentVo {

    public ContentVo(String type, String format, String value) {
        this.type = type;
        this.format = format;
        this.value = value;
    }

    public String type;

    public String format;

    public String value;

    public enum formatValues {
        plain_text,
        markdown,
        external_url
    }
}