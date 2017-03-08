package me.ledge.link.api.vos.responses.base;

/**
 * Generic list API response object.
 * @author wijnand
 */
public class ListResponseVo<DataType> {

    public String type;
    public int page;
    public int rows;
    public boolean has_more;
    public int total_count;

    public DataType data;

}
