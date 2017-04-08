package me.ledge.link.imageloaders.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * In-memory LRU Bitmap cache.
 * @see <a href="https://developer.android.com/training/volley/request.html#lru-cache">Volley request docs</a>
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {

    /**
     * Creates a new {@link LruBitmapCache} instance.
     * @param maxSize Max cache size.
     */
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    /**
     * Creates a new {@link LruBitmapCache} instance.
     * @param context The {@link Context} to use to calculate the max cache size.
     */
    public LruBitmapCache(Context context) {
        this(getCacheSize(context));
    }

    /** {@inheritDoc} */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    /** {@inheritDoc} */
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    /** {@inheritDoc} */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    /**
     * @return A cache size equal to approximately three screens worth of images.
     */
    public static int getCacheSize(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }
}
