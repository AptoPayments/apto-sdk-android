package me.ledge.link.api.vos.responses;

/**
 * Api error data.
 * TODO: Move to API common project?
 * @author Wijnand
 */
public class ApiErrorVo {

    /**
     * Request path.
     */
    public String request_path;

    /**
     * HTTP status code.
     */
    public int statusCode;

    /**
     * Server error code.
     */
    public int serverCode;

    /**
     * Error message from the server.
     */
    public String serverMessage;

    /**
     * @return Human readable representation of this class.
     */
    @Override
    public String toString() {
        String result = "%d / %d: %s";
        return String.format(result, statusCode, serverCode, serverMessage);
    }

    public boolean isSessionExpired = false;

}
