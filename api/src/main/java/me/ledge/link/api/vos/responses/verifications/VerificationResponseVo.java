package me.ledge.link.api.vos.responses.verifications;

/**
 *  Verification API response
 * @author Adrian
 */
public class VerificationResponseVo {

    public String type;
    public String verification_id;
    public String status;
    public SimpleVerificationResponseVo secondary_credential;
}
