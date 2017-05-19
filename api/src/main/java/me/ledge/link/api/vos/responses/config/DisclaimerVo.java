package me.ledge.link.api.vos.responses.config;

/**
 * Disclaimers data.
 * @author Adrian
 */
public class DisclaimerVo {

    public String type;

    public String format;

    public String value;

    public enum formatValues {
        plain_text,
        markdown,
        external_url
    }
}