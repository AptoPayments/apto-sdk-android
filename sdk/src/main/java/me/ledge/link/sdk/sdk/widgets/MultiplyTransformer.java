package me.ledge.link.sdk.sdk.widgets;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Simple {@link DiscreteSeekBar#NumericTransformer} that multiplies by a set value.
 * @author Wijnand
 */
public class MultiplyTransformer extends DiscreteSeekBar.NumericTransformer {

    private final int mMultiplier;

    /**
     * Creates a new {@link MultiplyTransformer} instance.
     * @param multiplier Amount to multiply by.
     */
    public MultiplyTransformer(int multiplier) {
        mMultiplier = multiplier;
    }

    /** {@inheritDoc} */
    @Override
    public int transform(int value) {
        return value * mMultiplier;
    }
}
