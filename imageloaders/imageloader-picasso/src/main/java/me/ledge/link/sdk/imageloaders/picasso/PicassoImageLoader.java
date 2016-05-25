package me.ledge.link.sdk.imageloaders.picasso;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import me.ledge.link.sdk.ui.images.GenericImageLoader;

/**
 * Concrete implementation of the {@link GenericImageLoader} interface using
 * <a href="https://square.github.io/picasso/">Picasso</a>.
 * @author Wijnand
 */
public class PicassoImageLoader implements GenericImageLoader {

    private final Picasso mPicasso;

    /**
     * Creates a new {@link PicassoImageLoader} instance.
     * @param context The {@link Context} to build the {@link Picasso} instance from.
     */
    public PicassoImageLoader(Context context) {
        this(new Picasso.Builder(context)
                .indicatorsEnabled(false)
                .loggingEnabled(false)
                .build()
        );
    }

    /**
     * Creates a new {@link PicassoImageLoader} instance.
     * @param picasso The specific {@link Picasso} instance to use.
     */
    public PicassoImageLoader(Picasso picasso) {
        mPicasso = picasso;
    }

    /** {@inheritDoc} */
    @Override
    public void load(String url, ImageView target) {
        mPicasso.load(url).into(target);
    }

    /** {@inheritDoc} */
    @Override
    public void cancel(String url, ImageView target) {
        mPicasso.cancelRequest(target);
    }
}
