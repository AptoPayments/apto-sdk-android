package me.ledge.link.api.vos.responses;

/**
 * Api error representing a Session Expired (401)
 * @author Adrian
 */
public class SessionExpiredErrorVo {
    public ApiErrorVo apiError;

    public SessionExpiredErrorVo(ApiErrorVo apiError) {
        this.apiError = apiError;
    }

    @Override
    public String toString() {
        return apiError.toString();
    }
}
