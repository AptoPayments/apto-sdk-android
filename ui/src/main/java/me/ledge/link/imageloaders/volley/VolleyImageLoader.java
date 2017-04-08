package me.ledge.link.imageloaders.volley;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.ledge.link.sdk.ui.images.GenericImageLoader;

/**
 * Concrete implementation of the {@link GenericImageLoader} interface using
 * <a href="https://developer.android.com/training/volley/index.html">Volley</a>.
 * @author wijnand
 */
public class VolleyImageLoader implements GenericImageLoader {

    private final ImageLoader mLoader;
    private List<ImageLoader.ImageContainer> mContainers;

    /**
     * Creates a new {@link VolleyImageLoader} instance.
     * @param context The {@link Context} to use when fetching the {@link ImageLoader}.
     */
    public VolleyImageLoader(Context context) {
        this(VolleySingleton.getInstance(context).getImageLoader());
    }

    /**
     * Creates a new {@link VolleyImageLoader} instance.
     * @param loader The specific {@link ImageLoader} to use.
     */
    public VolleyImageLoader(ImageLoader loader) {
        mLoader = loader;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mContainers = new ArrayList<ImageLoader.ImageContainer>();
    }

    /**
     * @param url The URL of the image that is being loaded.
     * @param target The {@link ImageView} that will display the loaded Bitmap.
     * @param containerList List of currently available {@link ImageLoader.ImageContainer}s.
     * @return A new {@link ImageLoader.ImageListener}.
     */
    private ImageLoader.ImageListener getImageListener(final String url, final ImageView target,
            final List<ImageLoader.ImageContainer> containerList) {

        return new ImageLoader.ImageListener() {

            /** {@inheritDoc} */
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                containerList.remove(response);

                if (response.getBitmap() != null) {
                    target.setImageBitmap(response.getBitmap());
                }
            }

            /** {@inheritDoc} */
            @Override
            public void onErrorResponse(VolleyError error) {
                ImageLoader.ImageContainer container = findContainer(containerList, url);

                if (container != null) {
                    containerList.remove(container);
                }
            }
        };
    }

    /**
     * @param containerList The list of {@link ImageLoader.ImageContainer}s to search.
     * @param url The URL to match.
     * @return The container that matches the url.
     */
    private ImageLoader.ImageContainer findContainer(List<ImageLoader.ImageContainer> containerList, String url) {
        ImageLoader.ImageContainer result = null;

        for (ImageLoader.ImageContainer container : containerList) {
            if (container.getRequestUrl().equals(url) ) {
                result = container;
            }
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public void load(String url, ImageView target) {
        ImageLoader.ImageContainer container = mLoader.get(url, getImageListener(url, target, mContainers), 0, 0,
                ImageView.ScaleType.CENTER_INSIDE);

        mContainers.add(container);
    }

    /** {@inheritDoc} */
    @Override
    public void cancel(String url, ImageView target) {
        ImageLoader.ImageContainer container = findContainer(mContainers, url);

        if (container != null) {
            container.cancelRequest();
            mContainers.remove(container);
        }
    }
}
