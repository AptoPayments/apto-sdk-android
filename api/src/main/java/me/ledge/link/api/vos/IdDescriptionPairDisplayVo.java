package me.ledge.link.api.vos;

import java.util.AbstractMap;
import java.util.Map;

/**
 * TODO: Class documentation.
 * TODO: Better name?
 * @author Wijnand
 */
public class IdDescriptionPairDisplayVo extends AbstractMap.SimpleEntry<Integer, String> {

    public IdDescriptionPairDisplayVo(Integer theKey, String theValue) {
        super(theKey, theValue);
    }

    public IdDescriptionPairDisplayVo(Map.Entry<? extends Integer, ? extends String> copyFrom) {
        super(copyFrom);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getValue();
    }
}
