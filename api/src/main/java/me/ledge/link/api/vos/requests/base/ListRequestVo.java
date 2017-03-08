package me.ledge.link.api.vos.requests.base;

/**
 * Generic list request.
 * TODO: Move to API common project.
 * @author wijnand
 */
public class ListRequestVo extends UnauthorizedRequestVo {

    private static final int DEFAULT_ROWS = 10;

    public int page = 0;
    public int rows = DEFAULT_ROWS;

}
